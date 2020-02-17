package api.contentpack;

import api.contentpack.data.Data;
import api.contentpack.data.IData;
import api.contentpack.data.IPackData;
import api.contentpack.json.PackInfoObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackManager {

    private final Path contentPackPath;
    private final AtomicReference<InputStream> stream;
    private final AtomicReference<InputStreamReader> reader;
    private final List<ContentPack> packs;
    private final PriorityQueue<Data> packDataQueue;
    private final Logger logger;
    private final int dataVersion;
    private Gson gson;
    private ZipFile zipFile = null;

    /**
     * Define a {@link PackManager#contentPackPath} where {@link PackManager#loadPacks()} walk into it
     * <br>You need to figure out the {@link net.minecraft.client.Minecraft#gameDir} was not the same in Client and Server<br>
     * Use Proxies instead !
     *
     * @param contentPackPathIn
     */
    public PackManager(int dataVersionIn, Logger loggerIn, Path contentPackPathIn) {
        this.dataVersion = dataVersionIn;
        this.logger = loggerIn;
        this.stream = new AtomicReference<>();
        this.reader = new AtomicReference<>();
        this.packs = new ArrayList<>();
        this.packDataQueue = new PriorityQueue<>();
        this.contentPackPath = contentPackPathIn;
        if (!Files.exists(contentPackPath)) {
            try {
                Files.createDirectories(contentPackPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Main method to load packs from {@link PackManager#contentPackPath}
     * read zip content and inject {@link PackManager#packDataQueue} into game
     */
    public void loadPacks() {
        if (this.contentPackPath != null) {
            try (Stream<Path> walk = Files.walk(this.contentPackPath)) {
                walk.map(Path::toFile)
                        .filter(f -> f.getName().endsWith(".zip"))
                        .collect(Collectors.toList())
                        .forEach(files -> {
                            try {
                                zipFile = new ZipFile(files);
                                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                                while (entries.hasMoreElements()) {

                                    ContentPack contentPack = createContentPack(files, entries);

                                    if (contentPack != null) {
                                        if (contentPack.getPackInfo().getNuwaDataVersion() == this.dataVersion) {
                                            //Parse Hardcoded Data
                                            this.parseHardcodedData(contentPack);
                                            //Parse Pack Data
                                            this.parseData(contentPack);
                                            //Add ContentPack to list
                                            packs.add(contentPack);
                                        } else {
                                            this.logger.error("Unable to load \"{}\" Content Pack, Data Version mismatch with \"Nuwa\". Data Version: {}\"", contentPack.getPackInfo().getPackName(), this.dataVersion);
                                        }
                                    }

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                this.close(walk);
                            }
                        });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void close(Stream<Path> walkIn) {
        try {
            if (zipFile != null) {
                zipFile.close();
            }
            if (stream.get() != null) {
                stream.get().close();
            }
            if (reader.get() != null) {
                reader.get().close();
            }
            walkIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ContentPack createContentPack(File files, Enumeration<? extends ZipEntry> entries) throws IOException {
        if (entries.nextElement().getName().equals("content.pack")) {
            ZipEntry contentPackEntry = zipFile.getEntry("content.pack");
            ZipEntry packIconEntry = zipFile.getEntry("pack.png");

            if (contentPackEntry != null) {
                stream.set(zipFile.getInputStream(contentPackEntry));
                reader.set(new InputStreamReader(stream.get()));
                PackInfoObject packInfoObject = gson.fromJson(reader.get(), PackInfoObject.class);

                if (packIconEntry != null) {
                    stream.set(zipFile.getInputStream(packIconEntry));
                    return new ContentPack(stream.get(), files, packInfoObject, files.length());
                } else {
                    return new ContentPack(files, packInfoObject, files.length());
                }
            }
        }
        return null;
    }

    private void parseHardcodedData(ContentPack contentPackIn) {
        this.packDataQueue.forEach((dataEntry) -> {
            try {
                IData data = dataEntry.getDataClass().newInstance();
                data.parseData(this, contentPackIn);
                this.fillRegistries(data);
            } catch (InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void parseData(ContentPack contentPackIn) {
        this.packDataQueue.forEach((dataEntry) -> {
            try {
                IData data = dataEntry.getDataClass().newInstance();
                if (data instanceof IPackData) {
                    IPackData packData = (IPackData) data;

                    zipFile.stream().filter(o -> o.getName().startsWith(packData.getEntryFolder()) && o.getName().endsWith(".json")).forEach(o -> {
                        try {
                            stream.set(zipFile.getInputStream(o));
                            reader.set(new InputStreamReader(stream.get()));
                            packData.parseData(this, contentPackIn, zipFile, reader.get());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                    this.fillRegistries(packData);
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Register objects in their respectives registries
     */
    private void fillRegistries(IData dataIn) {
        if (dataIn.getObjectsList() != null && !dataIn.getObjectsList().isEmpty()) {
            dataIn.getObjectsList().stream()
                    .filter(Objects::nonNull)
                    .forEach(object -> {
                        this.packDataQueue.stream()
                                .filter(data -> data.getForgeRegistry() != null)
                                .filter(data -> data.getForgeRegistry().getRegistrySuperType().equals(object.getRegistryType()))
                                .forEach(data -> {
                                    data.getForgeRegistry().register(object);
                                });
                    });
        }
    }

    /**
     * Throw a message if an {@link net.minecraft.item.ItemGroup} doesn't exist in "itemGroup" object
     */
    public void throwItemGroupWarn(ContentPack contentPackIn, ZipFile zipFile, String entryName, ResourceLocation parsedItemGroup) {
        int erroredLine = 0;
        LineNumberReader lnr = null;
        try {
            lnr = new LineNumberReader(new InputStreamReader(zipFile.getInputStream(new ZipEntry(entryName))));
            String line;
            while ((line = lnr.readLine()) != null) {
                if (line.contains(parsedItemGroup.toString())) {
                    erroredLine = lnr.getLineNumber();
                }
            }
        } catch (IOException e) {
        }
        this.logger.warn("Item Group {{}} at line {} in {}/{} does not exist.", parsedItemGroup.toString(), erroredLine, contentPackIn.getPackInfo().getPackName(), entryName);
        try {
            if (lnr != null) {
                lnr.close();
            }
        } catch (IOException e) {
        }
    }

    /**
     * Register {@link IPackData} entry <b><u>ALWAYS</u></b> before {@link PackManager#loadPacks()} process
     *
     * @param dataIn
     */
    public void registerData(Class<? extends IData> dataIn) {
        this.packDataQueue.add(new Data(dataIn));
    }

    /**
     * Register {@link IPackData} entry <b><u>ALWAYS</u></b> before {@link PackManager#loadPacks()} process
     * Attach an {@link IForgeRegistry} if using {@link IData#getObjectsList()}
     *
     * @param dataIn
     * @param registryIn
     */
    public void registerData(Class<? extends IData> dataIn, IForgeRegistry registryIn) {
        this.packDataQueue.add(new Data(dataIn, registryIn));
    }

    /**
     * Remove {@link IPackData} entry <b><u>ALWAYS</u></b> before {@link PackManager#loadPacks()} process
     *
     * @param dataIn
     */
    public void removePackDataEntry(Class<? extends IData> dataIn) {
        this.packDataQueue.removeIf(data -> data.getDataClass().equals(dataIn));
    }

    public Gson getGson() {
        //Ensure Gson is never null
        return gson != null ? gson : new GsonBuilder().create();
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public Logger getLogger() {
        return logger;
    }

    public List<ContentPack> getPacks() {
        return packs;
    }

    public PriorityQueue<Data> getPackDataQueue() {
        return packDataQueue;
    }
}

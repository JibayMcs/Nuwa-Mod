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

/**
 * Main class to manage content packs,<br>
 * read it from contentpack folder as folder or zip file,
 * parse data, put data objects in Forge Registries
 *
 * @author ZeAmateis
 */
public class PackManager {

    private final Path contentPackPath;
    private final AtomicReference<InputStream> stream;
    private final AtomicReference<InputStreamReader> reader;
    private final List<ContentPack> packs;
    private final PriorityQueue<Data> packDataQueue;
    private final Logger logger;
    private final int dataVersion;
    private Gson gson;

    /**
     * Define a {@link PackManager#contentPackPath} where {@link PackManager#loadPacks()} walk into it
     * * <br>You need to figure out the {@link net.minecraft.client.Minecraft#gameDir} was not the same in Client and Server<br>
     * * Use Proxies instead !
     *
     * @param dataVersionIn     Data version of the mod implementing API
     * @param loggerIn          A logger instance
     * @param contentPackPathIn folder path where content packs are
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
            loadZippedPacks();
            loadUnzippedPacks();
        }
    }

    /**
     * Walks down the contentpacks/ folder hierarchy to find pack present as folders
     */
    private void loadUnzippedPacks() {
        // load unzipped packs
        try (Stream<Path> walk = Files.walk(this.contentPackPath)) {
            walk.map(Path::toFile)
                    .filter(f -> f.isDirectory())
                    .collect(Collectors.toList())
                    .forEach(pack -> {
                        Path basePath = pack.toPath();
                        try {
                            parseAndAdd(createUnzippedContentPack(basePath));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Create {@link ContentPack} based on a folder
     *
     * @param basePath base folder path
     * @return
     * @throws IOException
     */
    private ContentPack createUnzippedContentPack(Path basePath) throws FileNotFoundException {
        Path actualPath = basePath.resolve("content.pack");
        File actualFile = actualPath.toFile();

        if(!actualFile.exists())
            return null;

        Path packIconEntryPath = basePath.resolve("pack.png");
        File packIconEntry = packIconEntryPath.toFile();

        stream.set(new FileInputStream(actualFile));
        reader.set(new InputStreamReader(stream.get()));
        PackInfoObject packInfoObject = gson.fromJson(reader.get(), PackInfoObject.class);

        if (packIconEntry != null) {
            stream.set(new FileInputStream(packIconEntry));
            return new ContentPack(stream.get(), basePath, packInfoObject);
        } else {
            return new ContentPack(basePath, packInfoObject);
        }
    }

    /**
     * Parses the data inside a content pack and adds it to the list of loaded packs
     * @param contentPack
     */
    private void parseAndAdd(ContentPack contentPack) {
        if (contentPack != null) {
            if (contentPack.getPackInfo().getNuwaDataVersion() == this.dataVersion) {
                //Parse Hardcoded Data
                this.parseHardcodedData(contentPack);
                //Parse Pack Data
                this.parseData(contentPack);
                //Add ContentPack to list
                packs.add(contentPack);
                this.logger.info("Loaded \"{}\" Content Pack", contentPack.getPackInfo().getPackName());
            } else {
                this.logger.error("Unable to load \"{}\" Content Pack, Data Version mismatch with \"Nuwa\". Data Version: {}\"", contentPack.getPackInfo().getPackName(), this.dataVersion);
            }
        }
    }

    /**
     * Walks down the contentpacks/ folder hierarchy to find pack present as zip files
     */
    private void loadZippedPacks() {
        // load zipped packs
        try (Stream<Path> walk = Files.walk(this.contentPackPath)) {
            walk.map(Path::toFile)
                    .filter(f -> f.getName().endsWith(".zip"))
                    .collect(Collectors.toList())
                    .forEach(files -> {
                        try(ZipFile zipFile = new ZipFile(files)) {
                            Enumeration<? extends ZipEntry> entries = zipFile.entries();

                            while (entries.hasMoreElements()) {
                                parseAndAdd(createZippedContentPack(zipFile, files, entries));
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

    /**
     * Close properly various opened streams
     *
     * @param walkIn
     */
    private void close(Stream<Path> walkIn) {
        try {
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

    /**
     * Create {@link ContentPack} based on content.pack zip entry informations
     *
     * @param files
     * @param entries
     * @return
     * @throws IOException
     */
    private ContentPack createZippedContentPack(ZipFile zipFile, File files, Enumeration<? extends ZipEntry> entries) throws IOException {
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

    /**
     * Parse hardcoded data who don't need entries from zip and fill registries of the content pack
     *
     * @param contentPackIn
     */
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

    /**
     * Parse data and fill registries of the content pack
     *
     * @param contentPackIn
     */
    private void parseData(ContentPack contentPackIn) {
        this.packDataQueue.forEach((dataEntry) -> {
            try {
                IData data = dataEntry.getDataClass().newInstance();
                if (data instanceof IPackData) {
                    IPackData packData = (IPackData) data;

                    contentPackIn.getFiles().stream().filter(o -> o.getName().startsWith(packData.getEntryFolder()) && o.getName().endsWith(".json")).forEach(o -> {
                        try {
                            stream.set(o.getInputStream());
                            reader.set(new InputStreamReader(stream.get()));
                            packData.parseData(this, contentPackIn, reader.get());
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
     *
     * @param dataIn
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
     *  @param contentPackIn   the errored content pack
     * @param entryName       the errored json file
     * @param parsedItemGroup the errored item group name
     */
    public void throwItemGroupWarn(ContentPack contentPackIn, String entryName, ResourceLocation parsedItemGroup) {
        int erroredLine = 0;
        LineNumberReader lnr = null;
        try {
            lnr = new LineNumberReader(new InputStreamReader(contentPackIn.getSubFile(entryName).getInputStream()));
            String line;
            while ((line = lnr.readLine()) != null) {
                if (line.contains(parsedItemGroup.toString())) {
                    erroredLine = lnr.getLineNumber();
                }
            }
        } catch (IOException ignored) {
        }
        this.logger.warn("Item Group {{}} at line {} in {}/{} does not exist.", parsedItemGroup.toString(), erroredLine, contentPackIn.getPackInfo().getPackName(), entryName);
        try {
            if (lnr != null) {
                lnr.close();
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Register {@link IPackData} entry <b><u>ALWAYS</u></b> before {@link PackManager#loadPacks()} process
     *
     * @param dataIn the data class to be registered in queue
     */
    public void registerData(Class<? extends IData> dataIn) {
        this.packDataQueue.add(new Data(dataIn));
    }

    /**
     * Register {@link IPackData} entry <b><u>ALWAYS</u></b> before {@link PackManager#loadPacks()} process
     * Attach an {@link IForgeRegistry} if using {@link IData#getObjectsList()}
     *
     * @param dataIn     the data class to be registered in queue
     * @param registryIn the {@link IForgeRegistry} attached to the {@link IData} class
     */
    public void registerData(Class<? extends IData> dataIn, IForgeRegistry registryIn) {
        this.packDataQueue.add(new Data(dataIn, registryIn));
    }

    /**
     * Remove {@link IPackData} entry <b><u>ALWAYS</u></b> before {@link PackManager#loadPacks()} process
     *
     * @param dataIn the data class to be removed from queue
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

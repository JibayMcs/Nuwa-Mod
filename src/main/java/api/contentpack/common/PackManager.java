package api.contentpack.common;

import api.contentpack.common.json.PackInfoObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackManager {

    public static final Gson GSON = new GsonBuilder().create();
    private final Path contentPackPath;
    private final List<ContentPack> packs;
    private final Map<ResourceLocation, Class<? extends IPackData>> packDataMap;


    /**
     * Define a {@link PackManager#contentPackPath} where {@link PackManager#loadPacks()} walk into it
     * <br>You need to figure out the {@link net.minecraft.client.Minecraft#gameDir} was not the same in Client and Server<br>
     * Use Proxies instead !
     *
     * @param contentPackPathIn
     */
    public PackManager(Path contentPackPathIn) {
        packs = new ArrayList<>();
        packDataMap = new HashMap<>();
        contentPackPath = contentPackPathIn;
        if (!Files.exists(contentPackPath)) {
            try {
                Files.createDirectories(contentPackPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void throwItemGroupWarn(ContentPack contentPackIn, ZipFile zipFile, String entryName, ResourceLocation parsedItemGroup) {
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
        NuwaMod.getLogger().warn("Item Group {{}} at line {} in {}/{} does not exist.", parsedItemGroup.toString(), erroredLine, contentPackIn.getFile().getName(), entryName);
        try {
            lnr.close();
        } catch (IOException e) {
        }
    }

    public List<ContentPack> getPacks() {
        return packs;
    }

    /**
     * Main method to load packs from {@link PackManager#contentPackPath}
     * read zip content and inject {@link PackManager#packDataMap} into game
     */
    public void loadPacks() {
        if (this.contentPackPath != null) {
            try (Stream<Path> walk = Files.walk(this.contentPackPath)) {
                walk.map(Path::toFile)
                        .filter(f -> f.getName().endsWith(".zip"))
                        .collect(Collectors.toList())
                        .forEach(files -> {
                            ZipFile zipFile = null;
                            InputStream stream = null;
                            InputStreamReader reader = null;
                            try {
                                zipFile = new ZipFile(files);
                                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                                while (entries.hasMoreElements()) {
                                    if (entries.nextElement().getName().equals("content.pack")) {
                                        ZipEntry contentPackEntry = zipFile.getEntry("content.pack");
                                        ZipEntry packIconEntry = zipFile.getEntry("pack.png");

                                        ContentPack contentPack;

                                        if (contentPackEntry != null) {
                                            stream = zipFile.getInputStream(contentPackEntry);
                                            reader = new InputStreamReader(stream);
                                            PackInfoObject packInfoObject = GSON.fromJson(reader, PackInfoObject.class);

                                            if (packIconEntry != null) {
                                                stream = zipFile.getInputStream(packIconEntry);
                                                contentPack = new ContentPack(stream, files, packInfoObject, files.length());
                                            } else {
                                                contentPack = new ContentPack(files, packInfoObject, files.length());
                                            }


                                            if (!this.packDataMap.isEmpty()) {
                                                for (Map.Entry<ResourceLocation, Class<? extends IPackData>> packDataEntry : packDataMap.entrySet()) {
                                                    IPackData packData = packDataEntry.getValue().newInstance();
                                                    if (!packData.getEntryName().isEmpty()) {
                                                        ZipEntry zipEntry = zipFile.getEntry(packData.getEntryName());
                                                        if (zipEntry != null) {
                                                            stream = zipFile.getInputStream(zipEntry);
                                                            reader = new InputStreamReader(stream);
                                                            packData.parseData(contentPack, zipFile, reader);
                                                            if (packData.getObjectsList() != null && !packData.getObjectsList().isEmpty()) {
                                                                contentPack.getObjectsList().addAll(packData.getObjectsList());
                                                                contentPack.getObjectsList().forEach(iForgeRegistryEntry -> {
                                                                    if (packData.getObjectsRegistry().getRegistrySuperType().equals(iForgeRegistryEntry.getRegistryType())) {
                                                                        packData.getObjectsRegistry().register(iForgeRegistryEntry);
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            packs.add(contentPack);
                                        }
                                    }
                                }
                            } catch (IOException | IllegalAccessException | InstantiationException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (zipFile != null) {
                                        zipFile.close();
                                    }
                                    if (stream != null) {
                                        stream.close();
                                    }
                                    if (reader != null) {
                                        reader.close();
                                    }
                                    walk.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * Register {@link IPackData} entry <b><u>ALWAYS</u></b> before {@link PackManager#loadPacks()} process
     *
     * @param entryName
     * @param packDataIn
     */
    public void registerDataEntry(ResourceLocation entryName, Class<? extends IPackData> packDataIn) {
        this.packDataMap.put(entryName, packDataIn);
    }

    /**
     * Remove {@link IPackData} entry <b><u>ALWAYS</u></b> before {@link PackManager#loadPacks()} process
     *
     * @param entryNameIn
     */
    public void removePackDataEntry(ResourceLocation entryNameIn) {
        this.packDataMap.remove(entryNameIn);
    }

}

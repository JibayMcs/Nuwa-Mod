package api.contentpack.common;

import api.contentpack.common.data.BlocksData;
import api.contentpack.common.data.ItemGroupData;
import api.contentpack.common.data.ItemsData;
import api.contentpack.common.json.PackInfoObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.util.ResourceLocation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackManager {


    public static final Gson GSON = new GsonBuilder().create();

    private final List<ContentPack> packs;


    public PackManager(Path contentPackPathIn) {
        packs = new ArrayList<>();
        Path path = contentPackPathIn;
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
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

    public void fetchPacks(File contentPackDirIn) {
        try (Stream<Path> walk = Files.walk(contentPackDirIn.toPath())) {
            walk.map(Path::toFile)
                    .filter(f -> f.getName().endsWith(".zip")).collect(Collectors.toList()).forEach(files -> {

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

                            BlocksData blocksData;
                            ItemsData itemsData;
                            ItemGroupData itemGroupData;
                            ZipEntry blocksEntry, itemsEntry, itemGroupEntry;


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

                                blocksData = new BlocksData(contentPack, zipFile);
                                blocksEntry = zipFile.getEntry(blocksData.getEntryName());

                                itemsData = new ItemsData(contentPack, zipFile);
                                itemsEntry = zipFile.getEntry(itemsData.getEntryName());

                                itemGroupData = new ItemGroupData();
                                itemGroupEntry = zipFile.getEntry(itemGroupData.getEntryName());

                                if (itemGroupEntry != null) {
                                    stream = zipFile.getInputStream(itemGroupEntry);
                                    reader = new InputStreamReader(stream);
                                    itemGroupData.parseData(reader);
                                }

                                if (blocksEntry != null) {
                                    stream = zipFile.getInputStream(blocksEntry);
                                    reader = new InputStreamReader(stream);
                                    blocksData.parseData(reader);
                                    contentPack.getBlockList().addAll(blocksData.getObjectsList());
                                }

                                if (itemsEntry != null) {
                                    stream = zipFile.getInputStream(itemsEntry);
                                    reader = new InputStreamReader(stream);
                                    itemsData.parseData(reader);
                                    contentPack.getItemList().addAll(itemsData.getObjectsList());
                                }
                                packs.add(contentPack);
                            }
                        }
                    }
                } catch (IOException e) {
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

        packs.forEach(contentPack -> {
            NuwaMod.getLogger().debug(contentPack.getBlockList().size());
        });
    }

}

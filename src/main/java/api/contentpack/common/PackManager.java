package api.contentpack.common;

import api.contentpack.common.json.PackInfoObject;
import api.contentpack.common.json.datas.WhitelistObject;
import api.contentpack.common.minecraft.blocks.base.IJsonBlock;
import api.contentpack.common.minecraft.items.base.JsonBlockItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.zeamateis.nuwa.Constant;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackManager {

    private final Gson gson;
    private final Path contentPackPath;
    private final List<ContentPack> packs;
    private final Map<ResourceLocation, Class<? extends IPackData>> packDataMap;

    private WhitelistObject whitelistObject;

    /**
     * Define a {@link PackManager#contentPackPath} where {@link PackManager#loadPacks()} walk into it
     * <br>You need to figure out the {@link net.minecraft.client.Minecraft#gameDir} was not the same in Client and Server<br>
     * Use Proxies instead !
     *
     * @param contentPackPathIn
     */
    public PackManager(Path contentPackPathIn) {
        gson = new GsonBuilder().setPrettyPrinting().create();
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

    public Map<ResourceLocation, Class<? extends IPackData>> getPackDataMap() {
        return packDataMap;
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
                            AtomicReference<InputStream> stream = new AtomicReference<InputStream>();
                            AtomicReference<InputStreamReader> reader = new AtomicReference<InputStreamReader>();
                            try {
                                zipFile = new ZipFile(files);
                                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                                while (entries.hasMoreElements()) {
                                    if (entries.nextElement().getName().equals("content.pack")) {
                                        ZipEntry contentPackEntry = zipFile.getEntry("content.pack");
                                        ZipEntry packIconEntry = zipFile.getEntry("pack.png");

                                        ContentPack contentPack;

                                        if (contentPackEntry != null) {
                                            stream.set(zipFile.getInputStream(contentPackEntry));
                                            reader.set(new InputStreamReader(stream.get()));
                                            PackInfoObject packInfoObject = gson.fromJson(reader.get(), PackInfoObject.class);

                                            if (packIconEntry != null) {
                                                stream.set(zipFile.getInputStream(packIconEntry));
                                                contentPack = new ContentPack(stream.get(), files, packInfoObject, files.length());
                                            } else {
                                                contentPack = new ContentPack(files, packInfoObject, files.length());
                                            }

                                            if (packInfoObject.getNuwaDataVersion() == Constant.DATA_VERSION) {
                                                ZipFile finalZipFile = zipFile;

                                                //Whitelist
                                                zipFile.stream().filter(o -> o.getName().equals("objects/whitelist.json")).forEach(o -> {
                                                    try {
                                                        stream.set(finalZipFile.getInputStream(o));
                                                        reader.set(new InputStreamReader(stream.get()));
                                                        whitelistObject = gson.fromJson(reader.get(), WhitelistObject.class);
                                                    } catch (IOException ex) {
                                                        ex.printStackTrace();
                                                    }
                                                });

                                                for (Map.Entry<ResourceLocation, Class<? extends IPackData>> packDataEntry : packDataMap.entrySet()) {
                                                    IPackData packData = packDataEntry.getValue().newInstance();

                                                    zipFile.stream().filter(o -> o.getName().startsWith(packData.getEntryFolder()) && o.getName().endsWith(".json")).forEach(o -> {
                                                        try {
                                                            stream.set(finalZipFile.getInputStream(o));
                                                            reader.set(new InputStreamReader(stream.get()));
                                                            packData.parseData(this, contentPack, finalZipFile, reader.get());
                                                        } catch (IOException ex) {
                                                            ex.printStackTrace();
                                                        }
                                                    });

                                                    if (packData.getObjectsList() != null && !packData.getObjectsList().isEmpty()) {
                                                        packData.getObjectsList().stream()
                                                                .filter(registryEntry -> registryEntry.getRegistryType().equals(registryEntry.getRegistryType()))
                                                                .forEach(iForgeRegistryEntry -> {
                                                                    packData.getObjectsRegistry().register(iForgeRegistryEntry);
                                                                });

                                                        //Register BlockItem
                                                        packData.getObjectsList().stream()
                                                                .filter(registryEntry -> registryEntry.getRegistryType().equals(Block.class))
                                                                .forEach(o -> {
                                                                    IJsonBlock jsonBlock = (IJsonBlock) o;
                                                                    JsonBlockItem jsonBlockItem = new JsonBlockItem(jsonBlock.getBlock(), new Item.Properties().group(jsonBlock.getItemGroup()), Objects.requireNonNull(jsonBlock.getRegistryName()));
                                                                    if (whitelistObject != null) {
                                                                        if (!whitelistObject.getBlocks().isEmpty()) {
                                                                            whitelistObject.getBlocks().stream()
                                                                                    .filter(s -> !s.equals(jsonBlock.getRegistryName().toString()))
                                                                                    .forEach(s -> ForgeRegistries.ITEMS.register(jsonBlockItem));
                                                                        } else {
                                                                            ForgeRegistries.ITEMS.register(jsonBlockItem);
                                                                        }
                                                                    } else {
                                                                        ForgeRegistries.ITEMS.register(jsonBlockItem);
                                                                    }
                                                                });
                                                    }
                                                }

                                                packs.add(contentPack);
                                            } else {
                                                NuwaMod.getLogger().error("Unable to load \"{}\" Content Pack, Data Version mismatch with \"Nuwa\". Data Version: {}\"", packInfoObject.getPackName(), Constant.DATA_VERSION);
                                            }
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
                                    if (stream.get() != null) {
                                        stream.get().close();
                                    }
                                    if (reader.get() != null) {
                                        reader.get().close();
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
    public void registerData(ResourceLocation entryName, Class<? extends IPackData> packDataIn) {
        this.packDataMap.put(entryName, packDataIn);
    }


    /**
     * Register {@link IPackDataEvent} entry if you need to hook on {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent} or
     * {@link net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent}
     *
     * @param packDataEventIn
     */
    public void registerDataEvent(Class<? extends IPackDataEvent> packDataEventIn) {
        try {
            IPackDataEvent packDataEvent = packDataEventIn.newInstance();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(packDataEvent::onCommonSetup);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(packDataEvent::onClientSetup);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove {@link IPackData} entry <b><u>ALWAYS</u></b> before {@link PackManager#loadPacks()} process
     *
     * @param entryNameIn
     */
    public void removePackDataEntry(ResourceLocation entryNameIn) {
        this.packDataMap.remove(entryNameIn);
    }

    public WhitelistObject getWhitelist() {
        return whitelistObject;
    }

    public Gson getGson() {
        return gson;
    }
}

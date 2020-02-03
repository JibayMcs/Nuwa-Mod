package api.contentpack.common;

import api.contentpack.common.data.base.IData;
import api.contentpack.common.data.base.IPackData;
import api.contentpack.common.data.base.IPackDataEvent;
import api.contentpack.common.json.PackInfoObject;
import api.contentpack.common.json.adapter.IProcessAdapter;
import api.contentpack.common.json.adapter.ItemStackAdapter;
import api.contentpack.common.json.datas.WhitelistObject;
import api.contentpack.common.json.datas.blocks.events.processes.AttackProcess;
import api.contentpack.common.json.datas.blocks.events.processes.GiveItemProcess;
import api.contentpack.common.json.datas.blocks.events.processes.HealProcess;
import api.contentpack.common.json.datas.blocks.events.processes.TeleportProcess;
import api.contentpack.common.json.datas.blocks.events.processes.base.IProcess;
import api.contentpack.common.minecraft.blocks.base.IJsonBlock;
import api.contentpack.common.minecraft.items.base.JsonBlockItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.zeamateis.nuwa.Constant;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
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

    private final Gson gson;
    private final Path contentPackPath;
    private final AtomicReference<InputStream> stream;
    private final AtomicReference<InputStreamReader> reader;
    private final List<ContentPack> packs;
    private final Map<ResourceLocation, Class<? extends IData>> packDataMap;
    private final Logger logger;
    private ZipFile zipFile = null;
    private WhitelistObject whitelistObject;


    /**
     * Define a {@link PackManager#contentPackPath} where {@link PackManager#loadPacks()} walk into it
     * <br>You need to figure out the {@link net.minecraft.client.Minecraft#gameDir} was not the same in Client and Server<br>
     * Use Proxies instead !
     *
     * @param contentPackPathIn
     */
    public PackManager(Logger loggerIn, Path contentPackPathIn) {
        this.logger = loggerIn;
        this.gson = createGsonBuilder();
        this.stream = new AtomicReference<>();
        this.reader = new AtomicReference<>();
        this.packs = new ArrayList<>();
        this.packDataMap = new HashMap<>();
        this.contentPackPath = contentPackPathIn;
        if (!Files.exists(contentPackPath)) {
            try {
                Files.createDirectories(contentPackPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Gson createGsonBuilder() {
        return new GsonBuilder()
                .registerTypeAdapter(ItemStack.class, new ItemStackAdapter())
                .registerTypeAdapter(IProcess.class, new IProcessAdapter<AttackProcess>())
                .registerTypeAdapter(IProcess.class, new IProcessAdapter<HealProcess>())
                .registerTypeAdapter(IProcess.class, new IProcessAdapter<GiveItemProcess>())
                .registerTypeAdapter(IProcess.class, new IProcessAdapter<TeleportProcess>())
                .setPrettyPrinting()
                .create();
    }

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

    public List<ContentPack> getPacks() {
        return packs;
    }

    public Map<ResourceLocation, Class<? extends IData>> getPackDataMap() {
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
                            try {
                                zipFile = new ZipFile(files);
                                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                                while (entries.hasMoreElements()) {

                                    ContentPack contentPack = createContentPack(files, entries);

                                    if (contentPack != null) {
                                        if (contentPack.getPackInfo().getNuwaDataVersion() == Constant.DATA_VERSION) {
                                            //If exist
                                            this.parseWhitelist();
                                            //Parse Hardcoded and Pack Data
                                            this.parseData(contentPack);
                                            packs.add(contentPack);
                                        } else {
                                            this.logger.error("Unable to load \"{}\" Content Pack, Data Version mismatch with \"Nuwa\". Data Version: {}\"", contentPack.getPackInfo().getPackName(), Constant.DATA_VERSION);
                                        }
                                    }

                                }
                            } catch (IOException | IllegalAccessException | InstantiationException e) {
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

    private void parseWhitelist() {
        zipFile.stream().filter(o -> o.getName().equals("objects/whitelist.json")).forEach(o -> {
            try {
                stream.set(zipFile.getInputStream(o));
                reader.set(new InputStreamReader(stream.get()));
                whitelistObject = gson.fromJson(reader.get(), WhitelistObject.class);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
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

    private void parseData(ContentPack contentPackIn) throws IllegalAccessException, InstantiationException {
        for (Map.Entry<ResourceLocation, Class<? extends IData>> packDataEntry : packDataMap.entrySet()) {
            IData data = packDataEntry.getValue().newInstance();
            //Pack datas
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
            } else {
                //Hardcoded datas
                data.parseData(this);
                if (data.getObjectsList() != null && !data.getObjectsList().isEmpty()) {
                    data.getObjectsList().stream()
                            .filter(registryEntry -> registryEntry.getRegistryType().equals(registryEntry.getRegistryType()))
                            .forEach(iForgeRegistryEntry -> {
                                data.getObjectsRegistry().register(iForgeRegistryEntry);
                            });
                }

            }
        }
    }


    /**
     * Register {@link IPackData} entry <b><u>ALWAYS</u></b> before {@link PackManager#loadPacks()} process
     *
     * @param entryName
     * @param packDataIn
     */
    public void registerData(ResourceLocation entryName, Class<? extends IData> packDataIn) {
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

    public Logger getLogger() {
        return logger;
    }
}

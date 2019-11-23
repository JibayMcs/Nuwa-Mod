package fr.zeamateis.mjson;

import api.contentpack.client.itemGroup.VanillaItemGroups;
import api.contentpack.common.ContentPack;
import api.contentpack.common.PackManager;
import api.contentpack.common.minecraft.assets.ContentPackFinder;
import fr.zeamateis.mjson.common.network.C2SContentPackInfoPacket;
import fr.zeamateis.mjson.common.network.S2CContentPackInfoPacket;
import fr.zeamateis.mjson.proxy.ClientProxy;
import fr.zeamateis.mjson.proxy.CommonProxy;
import fr.zeamateis.mjson.proxy.ServerProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Map;
import java.util.function.Predicate;

@Mod(Constant.MODID)
public class MJsonMod implements ISelectiveResourceReloadListener {
    private static final String PROTOCOL_VERSION = String.valueOf(1);

    private static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Constant.MODID, "mjson_channel"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    private static final Logger LOGGER = LogManager.getLogger();
    private static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    private static PackManager packManager;

    public MJsonMod() {
        packManager = new PackManager(PROXY.getPackDir().toPath());

        DistExecutor.runWhenOn(Dist.CLIENT, () -> this::registerVanillaItemGroups);

        packManager.fetchPacks(Constant.MODELS_PACK_DIR);
        packManager.getPacks().forEach(contentPack -> {
            PROXY.objectsRegistry(contentPack);
            //PROXY.registerPackFinder(contentPack);
        });

        ((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(this);
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onSetupFinished);*/
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    static CommonProxy getProxy() {
        return PROXY;
    }

    public static PackManager getPackManager() {
        return packManager;
    }

    public static SimpleChannel getNetwork() {
        return CHANNEL;
    }

    /**
     * A version of onResourceManager that selectively chooses {@link IResourceType}s
     * to reload.
     * When using this, the given predicate should be called to ensure the relevant resources should
     * be reloaded at this time.
     *
     * @param resourceManager   the resource manager being reloaded
     * @param resourcePredicate predicate to test whether any given resource type should be reloaded
     */
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        for (ContentPack contentPack : getPackManager().getPacks()) {
            ContentPackFinder contentPackFinder = new ContentPackFinder(contentPack);
            Minecraft.getInstance().getResourcePackList().addPackFinder(contentPackFinder);
            new IPackFinder() {
                @Override
                public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, ResourcePackInfo.IFactory<T> packInfoFactory) {
                    File contentPackFile = contentPack.getFile();
                    if (contentPackFile != null && contentPackFile.isFile()) {
                        T t1 = ResourcePackInfo.createResourcePack(contentPack.getNamespace(), true, () -> new FilePack(contentPackFile) {
                            @Override
                            public boolean isHidden() {
                                return true;
                            }
                        }, packInfoFactory, ResourcePackInfo.Priority.TOP);
                        if (t1 != null) {
                            nameToPackMap.put(contentPack.getNamespace(), t1);
                            //TODO Fix non-closing pack.
                            //Minecraft.getInstance().getResourceManager().addResourcePack(t1.getResourcePack());
                            resourceManager.addResourcePack(t1.getResourcePack());
                            MJsonMod.getLogger().debug("Added {} content pack assets to resources packs list.", t1.getName());
                        }
                    }
                }
            };
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void registerVanillaItemGroups() {
        VanillaItemGroups.addVanillaItemGroup(new ResourceLocation("minecraft", "building_blocks"), ItemGroup.BUILDING_BLOCKS);
        VanillaItemGroups.addVanillaItemGroup(new ResourceLocation("minecraft", ItemGroup.DECORATIONS.getTabLabel()), ItemGroup.DECORATIONS);
        VanillaItemGroups.addVanillaItemGroup(new ResourceLocation("minecraft", ItemGroup.REDSTONE.getTabLabel()), ItemGroup.REDSTONE);
        VanillaItemGroups.addVanillaItemGroup(new ResourceLocation("minecraft", ItemGroup.TRANSPORTATION.getTabLabel()), ItemGroup.TRANSPORTATION);
        VanillaItemGroups.addVanillaItemGroup(new ResourceLocation("minecraft", ItemGroup.FOOD.getTabLabel()), ItemGroup.FOOD);
        VanillaItemGroups.addVanillaItemGroup(new ResourceLocation("minecraft", ItemGroup.TOOLS.getTabLabel()), ItemGroup.TOOLS);
        VanillaItemGroups.addVanillaItemGroup(new ResourceLocation("minecraft", ItemGroup.COMBAT.getTabLabel()), ItemGroup.COMBAT);
        VanillaItemGroups.addVanillaItemGroup(new ResourceLocation("minecraft", ItemGroup.BREWING.getTabLabel()), ItemGroup.BREWING);
        VanillaItemGroups.addVanillaItemGroup(new ResourceLocation("minecraft", ItemGroup.MISC.getTabLabel()), ItemGroup.MISC);
        VanillaItemGroups.getItemGroupsMap().forEach((resourceLocation, itemGroup) -> MJsonMod.getLogger().debug(resourceLocation.toString()));
    }

    private void onClientSetup(FMLClientSetupEvent event) {

    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        CHANNEL.messageBuilder(S2CContentPackInfoPacket.class, 0)
                .encoder(S2CContentPackInfoPacket::encode)
                .decoder(S2CContentPackInfoPacket::decode)
                .consumer(S2CContentPackInfoPacket::handle)
                .add();

        CHANNEL.messageBuilder(C2SContentPackInfoPacket.class, 1)
                .encoder(C2SContentPackInfoPacket::encode)
                .decoder(C2SContentPackInfoPacket::decode)
                .consumer(C2SContentPackInfoPacket::handle)
                .add();
    }

}
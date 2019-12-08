package fr.zeamateis.nuwa;

import api.contentpack.client.itemGroup.ItemGroups;
import api.contentpack.client.minecraft.assets.ContentPackFinder;
import api.contentpack.common.ContentPack;
import api.contentpack.common.PackManager;
import api.contentpack.common.data.*;
import fr.zeamateis.nuwa.common.network.C2SContentPackInfoPacket;
import fr.zeamateis.nuwa.common.network.S2CContentPackInfoPacket;
import fr.zeamateis.nuwa.proxy.ClientProxy;
import fr.zeamateis.nuwa.proxy.CommonProxy;
import fr.zeamateis.nuwa.proxy.ServerProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Predicate;

//TODO Fix content packs not closed after loading.
@Mod(Constant.MODID)
public class NuwaMod implements ISelectiveResourceReloadListener {
    private static final String PROTOCOL_VERSION = String.valueOf(1);

    private static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Constant.MODID, "nuwa_channel"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    private static final Logger LOGGER = LogManager.getLogger();
    private static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    private static PackManager packManager;

    public NuwaMod() {
        packManager = new PackManager(PROXY.getPackDir().toPath());

        DistExecutor.runWhenOn(Dist.CLIENT, () -> this::registerVanillaItemGroups);

        packManager.registerData(new ResourceLocation(Constant.MODID, "item_group_data"), ItemGroupData.class);
        packManager.registerData(new ResourceLocation(Constant.MODID, "block_data"), BlocksData.class);
        packManager.registerData(new ResourceLocation(Constant.MODID, "item_data"), ItemsData.class);
        packManager.registerData(new ResourceLocation(Constant.MODID, "ores_generation_data"), OresGenerationData.class);
        packManager.registerData(new ResourceLocation(Constant.MODID, "trees_generation_data"), TreesGenerationData.class);

        packManager.loadPacks();

        MinecraftForge.EVENT_BUS.addListener(this::onServerAboutToStart);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> this::registerAssetsManager);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
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

    @OnlyIn(Dist.CLIENT)
    private void registerAssetsManager() {
        for (ContentPack contentPack : packManager.getPacks()) {
            Minecraft.getInstance().getResourcePackList().addPackFinder(new ContentPackFinder(contentPack));
        }
        ((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(this);
    }

    private void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        for (ContentPack contentPack : getPackManager().getPacks()) {
            event.getServer().getResourcePacks().addPackFinder(new ContentPackFinder(contentPack));
        }
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
            Minecraft.getInstance().getResourcePackList().addPackFinder(new ContentPackFinder(contentPack));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void registerVanillaItemGroups() {
        ItemGroups.putItemGroup(new ResourceLocation("minecraft", "building_blocks"), ItemGroup.BUILDING_BLOCKS);
        ItemGroups.putItemGroup(new ResourceLocation("minecraft", ItemGroup.DECORATIONS.getTabLabel()), ItemGroup.DECORATIONS);
        ItemGroups.putItemGroup(new ResourceLocation("minecraft", ItemGroup.REDSTONE.getTabLabel()), ItemGroup.REDSTONE);
        ItemGroups.putItemGroup(new ResourceLocation("minecraft", ItemGroup.TRANSPORTATION.getTabLabel()), ItemGroup.TRANSPORTATION);
        ItemGroups.putItemGroup(new ResourceLocation("minecraft", ItemGroup.FOOD.getTabLabel()), ItemGroup.FOOD);
        ItemGroups.putItemGroup(new ResourceLocation("minecraft", ItemGroup.TOOLS.getTabLabel()), ItemGroup.TOOLS);
        ItemGroups.putItemGroup(new ResourceLocation("minecraft", ItemGroup.COMBAT.getTabLabel()), ItemGroup.COMBAT);
        ItemGroups.putItemGroup(new ResourceLocation("minecraft", ItemGroup.BREWING.getTabLabel()), ItemGroup.BREWING);
        ItemGroups.putItemGroup(new ResourceLocation("minecraft", ItemGroup.MISC.getTabLabel()), ItemGroup.MISC);
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
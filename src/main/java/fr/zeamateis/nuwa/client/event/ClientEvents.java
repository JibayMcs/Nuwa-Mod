package fr.zeamateis.nuwa.client.event;

import api.contentpack.common.minecraft.blocks.JsonInvisibleBlock;
import api.contentpack.common.minecraft.blocks.base.IBiomeColor;
import api.contentpack.common.minecraft.blocks.base.IJsonBlock;
import api.contentpack.common.minecraft.items.JsonBlockItem;
import com.mojang.blaze3d.platform.GlStateManager;
import fr.zeamateis.nuwa.Constant;
import fr.zeamateis.nuwa.NuwaMod;
import fr.zeamateis.nuwa.client.gui.contentPack.ContentPackButton;
import fr.zeamateis.nuwa.client.gui.contentPack.ContentPacksScreen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.GrassColors;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constant.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onInitGuiEvent(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof MainMenuScreen) {
            int width = event.getGui().width;
            int height = event.getGui().height / 4 + (48 * 2) + 12;
            Button contentPackButton = new ContentPackButton(false, width / 2 - 124, height - 12, 20, 20, 0, 0, 20, 64, 64, onPress -> {
                Minecraft.getInstance().displayGuiScreen(new ContentPacksScreen(event.getGui()));
            });
            event.addWidget(contentPackButton);
        }
    }

    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        ItemStack itemstack = player.getHeldItemMainhand();
        World world = Minecraft.getInstance().world;

        boolean flag = Minecraft.getInstance().playerController.getCurrentGameType() == GameType.CREATIVE && !itemstack.isEmpty();
        ActiveRenderInfo activeRenderInfo = Minecraft.getInstance().getRenderManager().info;

        if (flag && itemstack.getItem() instanceof JsonBlockItem) {
            JsonBlockItem jsonBlockItem = (JsonBlockItem) itemstack.getItem();
            if (jsonBlockItem.getBlock() instanceof JsonInvisibleBlock) {
                for (int x = -2; x < 3; x++) {
                    for (int y = -2; y < 3; y++) {
                        //Check in z-axis
                        for (int z = -2; z < 3; z++) {
                            //Only get the blocks that are 2 blocks out (For loop -can- be used here instead)
                            if (x != -1 || x != 0 || x != 1 || y != -1 || y != 0 || y != 1 || z != -1 || z != 0 || z != 1) {
                                //Create a new BlockPos based on our offsets
                                BlockPos blockpos = new BlockPos(player.posZ + x, player.posY + y, player.posZ + z);

                                if (world.getBlockState(blockpos).getBlock() instanceof JsonInvisibleBlock) {

                                    GlStateManager.pushMatrix();
                                    GlStateManager.scalef(1.0F, 1.0F, 1.0F);

                                    double d0 = activeRenderInfo.getProjectedView().x;
                                    double d1 = activeRenderInfo.getProjectedView().y;
                                    double d2 = activeRenderInfo.getProjectedView().z;

                                    GlStateManager.translated(
                                            (double) blockpos.getX() - d0 + 0.5,
                                            (double) blockpos.getY() - d1 + 0.5,
                                            (double) blockpos.getZ() - d2 + 0.5);
                                    GlStateManager.rotatef(-player.rotationYaw, 0, 1, 0);
                                    GlStateManager.rotatef(player.rotationPitch, 1, 0, 0);
                                    Minecraft.getInstance().getItemRenderer().renderItem(itemstack, ItemCameraTransforms.TransformType.GROUND);
                                    GlStateManager.popMatrix();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Constant.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class NuwaColourManager {

        @SubscribeEvent
        public static void registerBlockColourHandlers(ColorHandlerEvent.Block event) {
            BlockColors blockcolors = event.getBlockColors();

            NuwaMod.getPackManager().getPacks().forEach(contentPack -> contentPack.getObjectsList().forEach(registry -> {
                if (registry instanceof IJsonBlock) {
                    IJsonBlock jsonBlock = (IJsonBlock) registry;
                    if (jsonBlock instanceof IBiomeColor) {
                        IBiomeColor biomeColorBlock = (IBiomeColor) jsonBlock;
                        blockcolors.register((state, worldIn, pos, tintIndex) -> worldIn != null && pos != null ? BiomeColors.getGrassColor(worldIn, pos) : GrassColors.get(0.5D, 1.0D), (Block) biomeColorBlock);
                    }
                }
            }));
        }

        @SubscribeEvent
        public static void registerItemColourHandlers(final ColorHandlerEvent.Item event) {
            final BlockColors blockColors = event.getBlockColors();
            final ItemColors itemColors = event.getItemColors();

            NuwaMod.getPackManager().getPacks().forEach(contentPack -> contentPack.getObjectsList().forEach(registry -> {
                if (registry instanceof IJsonBlock) {
                    IJsonBlock jsonBlock = (IJsonBlock) registry;
                    if (jsonBlock instanceof IBiomeColor) {
                        IBiomeColor biomeColorBlock = (IBiomeColor) jsonBlock;
                        itemColors.register((p_210235_1_, p_210235_2_) -> {
                            BlockState blockstate = ((BlockItem) p_210235_1_.getItem()).getBlock().getDefaultState();
                            return blockColors.getColor(blockstate, null, null, p_210235_2_);
                        }, (Block) biomeColorBlock);
                    }
                }
            }));
        }
    }

}

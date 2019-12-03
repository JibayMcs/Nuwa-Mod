package fr.zeamateis.nuwa.client.event;

import api.contentpack.common.minecraft.blocks.base.IBiomeColor;
import api.contentpack.common.minecraft.blocks.base.IJsonBlock;
import fr.zeamateis.nuwa.Constant;
import fr.zeamateis.nuwa.NuwaMod;
import fr.zeamateis.nuwa.client.gui.contentPack.ContentPackButton;
import fr.zeamateis.nuwa.client.gui.contentPack.ContentPacksScreen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
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

package fr.zeamateis.nuwa.proxy;

import api.contentpack.common.ContentPack;
import api.contentpack.common.minecraft.blocks.IJsonBlock;
import api.contentpack.common.minecraft.blocks.JsonGrassBlock;
import api.contentpack.common.minecraft.items.JsonBlockItem;
import fr.zeamateis.nuwa.Constant;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

public class ClientProxy extends CommonProxy {

    @Override
    public File getPackDir() {
        return new File(Minecraft.getInstance().gameDir, "/contentpacks/");
    }

    @Mod.EventBusSubscriber(modid = Constant.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModColourManager {


        @SubscribeEvent
        public static void registerBlockColourHandlers(ColorHandlerEvent.Block event) {
            BlockColors blockcolors = event.getBlockColors();
            blockcolors.register((state, worldIn, pos, tintIndex) ->
                            worldIn != null && pos != null ? BiomeColors.getGrassColor(worldIn, pos) : GrassColors.get(0.5D, 1.0D)
                    ,
                    NuwaMod.getPackManager().getPacks().stream().map(ContentPack::getObjectsList).filter(iForgeRegistryEntry -> iForgeRegistryEntry instanceof IJsonBlock).map(iForgeRegistryEntries -> (IJsonBlock) iForgeRegistryEntries).filter(iJsonBlock -> iJsonBlock instanceof JsonGrassBlock).toArray(JsonGrassBlock[]::new));
        }

        @SubscribeEvent
        public void registerItemColourHandlers(final ColorHandlerEvent.Item event) {
            final BlockColors blockColors = event.getBlockColors();
            final ItemColors itemColors = event.getItemColors();

            final IItemColor itemBlockColourHandler = (stack, tintIndex) -> {
                @SuppressWarnings("deprecation") final BlockState state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
                return blockColors.getColor(state, null, null, tintIndex);
            };
            NuwaMod.getPackManager().getPacks().forEach(contentPack -> {
                contentPack.getObjectsList().forEach(iForgeRegistryEntry -> {
                    if (iForgeRegistryEntry instanceof JsonBlockItem) {
                        JsonBlockItem jsonBlockItem = (JsonBlockItem) iForgeRegistryEntry;

                        itemColors.register(itemBlockColourHandler, jsonBlockItem);
                    }
                });
            });
        }
    }
}

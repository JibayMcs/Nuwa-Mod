package fr.zeamateis.nuwa.contentpack.common.minecraft.blocks;

import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.IBiomeColor;
import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.JsonBlock;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Json parsed {@link net.minecraft.block.Block} supporting {@link net.minecraft.world.biome.BiomeColors}
 *
 * @author ZeAmateis
 */
public class JsonBiomeColorBlock extends JsonBlock implements IBiomeColor {

    public JsonBiomeColorBlock(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties, registryNameIn);
    }

}

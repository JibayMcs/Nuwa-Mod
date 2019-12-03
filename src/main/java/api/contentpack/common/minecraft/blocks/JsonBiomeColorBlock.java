package api.contentpack.common.minecraft.blocks;

import api.contentpack.common.minecraft.blocks.base.IBiomeColor;
import api.contentpack.common.minecraft.blocks.base.JsonBlock;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class JsonBiomeColorBlock extends JsonBlock implements IBiomeColor {

    public JsonBiomeColorBlock(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties, registryNameIn);
    }

}

package fr.zeamateis.nuwa.contentpack.common.minecraft.entity.items;

import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class JsonPainting extends PaintingType {

    public JsonPainting(int width, int height, @Nonnull ResourceLocation registryNameIn) {
        super(width, height);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

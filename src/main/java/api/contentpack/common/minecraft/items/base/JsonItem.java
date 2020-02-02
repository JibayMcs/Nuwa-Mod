package api.contentpack.common.minecraft.items.base;

import api.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class JsonItem extends Item {
    public JsonItem(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

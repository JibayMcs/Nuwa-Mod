package api.contentpack.common.minecraft.items;

import api.contentpack.common.minecraft.RegistryUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class JsonItem extends Item {
    public JsonItem(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

package fr.zeamateis.nuwa.contentpack.common.minecraft.items.base;

import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class JsonItem extends Item implements IJsonItem {
    public JsonItem(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

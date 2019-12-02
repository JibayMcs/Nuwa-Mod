package api.contentpack.common.minecraft.items;

import api.contentpack.common.minecraft.RegistryUtil;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class JsonNamedItem extends BlockNamedItem implements IJsonItem {

    public JsonNamedItem(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("mff", "leek")), properties);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

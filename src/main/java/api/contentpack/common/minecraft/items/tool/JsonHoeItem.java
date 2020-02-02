package api.contentpack.common.minecraft.items.tool;

import api.contentpack.common.minecraft.RegistryUtil;
import api.contentpack.common.minecraft.items.base.IJsonItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

//TODO Check if it's necessary to replace the hoe_lookup map
public class JsonHoeItem extends HoeItem implements IJsonItem {

    public JsonHoeItem(IItemTier tier, float attackSpeedIn, Properties builder, @Nonnull ResourceLocation registryNameIn) {
        super(tier, attackSpeedIn, builder);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

package api.contentpack.common.minecraft.items.tool;

import api.contentpack.common.minecraft.RegistryUtil;
import api.contentpack.common.minecraft.items.base.IJsonItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class JsonAxeItem extends AxeItem implements IJsonItem {

    public JsonAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder, @Nonnull ResourceLocation registryNameIn) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

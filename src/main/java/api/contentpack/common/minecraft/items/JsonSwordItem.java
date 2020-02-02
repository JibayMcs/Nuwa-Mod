package api.contentpack.common.minecraft.items;

import api.contentpack.common.minecraft.RegistryUtil;
import api.contentpack.common.minecraft.items.base.IJsonItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class JsonSwordItem extends SwordItem implements IJsonItem {

    public JsonSwordItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder, @Nonnull ResourceLocation registryNameIn) {
        super(tier, (int) attackDamageIn, attackSpeedIn, builder);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

package api.contentpack.common.minecraft.items.tool;

import api.contentpack.common.minecraft.RegistryUtil;
import api.contentpack.common.minecraft.items.base.IJsonItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class JsonPixaxeItem extends PickaxeItem implements IJsonItem {

    public JsonPixaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, @Nonnull ResourceLocation registryNameIn) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

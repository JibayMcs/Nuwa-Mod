package fr.zeamateis.nuwa.contentpack.common.minecraft.items.tool;

import fr.zeamateis.nuwa.contentpack.common.minecraft.items.base.IJsonItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Json parsed {@link ShovelItem}
 *
 * @author ZeAmateis
 */
//TODO Check if it's necessary to replace the effective_on set
public class JsonShovelItem extends ShovelItem implements IJsonItem {

    public JsonShovelItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder, @Nonnull ResourceLocation registryNameIn) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

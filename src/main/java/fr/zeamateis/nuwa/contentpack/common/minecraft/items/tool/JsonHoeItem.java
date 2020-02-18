package fr.zeamateis.nuwa.contentpack.common.minecraft.items.tool;

import fr.zeamateis.nuwa.contentpack.common.minecraft.items.base.IJsonItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Json parsed {@link HoeItem}
 *
 * @author ZeAmateis
 */
//TODO Check if it's necessary to replace the hoe_lookup map
public class JsonHoeItem extends HoeItem implements IJsonItem {

    public JsonHoeItem(IItemTier tier, float attackSpeedIn, Properties builder, @Nonnull ResourceLocation registryNameIn) {
        super(tier, attackSpeedIn, builder);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

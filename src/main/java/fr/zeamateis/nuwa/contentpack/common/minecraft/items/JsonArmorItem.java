package fr.zeamateis.nuwa.contentpack.common.minecraft.items;

import fr.zeamateis.nuwa.contentpack.common.minecraft.items.base.IJsonItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Json parsed {@link ArmorItem}
 *
 * @author ZeAmateis
 */
//TODO Armor Ticks events/effect
public class JsonArmorItem extends ArmorItem implements IJsonItem {

    public JsonArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder, @Nonnull ResourceLocation registryNameIn) {
        super(materialIn, slot, builder);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }


}

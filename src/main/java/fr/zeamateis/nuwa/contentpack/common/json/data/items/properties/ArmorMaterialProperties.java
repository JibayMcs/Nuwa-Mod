package fr.zeamateis.nuwa.contentpack.common.json.data.items.properties;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

/**
 * Reprensentation of Json {@link fr.zeamateis.nuwa.contentpack.common.json.data.materials.ArmorMaterialObject} properties
 *
 * @author ZeAmateis
 */
public class ArmorMaterialProperties {

    private String armorMaterial;
    private EquipmentSlotType equipmentSlotType;

    public ArmorMaterialProperties(EquipmentSlotType equipmentSlotTypeIn, String armorMaterialIn) {
        this.equipmentSlotType = equipmentSlotTypeIn;
        this.armorMaterial = armorMaterialIn;
    }

    public EquipmentSlotType getEquipmentSlotType() {
        return this.equipmentSlotType;
    }

    public ResourceLocation getArmorMaterial() {
        return new ResourceLocation(armorMaterial);
    }
}

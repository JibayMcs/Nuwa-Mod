package api.contentpack.common.json.datas.items.properties;

import net.minecraft.inventory.EquipmentSlotType;

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

    public String getArmorMaterial() {
        return armorMaterial;
    }
}

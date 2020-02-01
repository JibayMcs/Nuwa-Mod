package api.contentpack.common.json.datas.items.properties;

import api.contentpack.common.json.datas.materials.ArmorMaterialObject;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

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

    public ArmorMaterialObject getArmorMaterial() {
        return NuwaMod.Registries.ARMOR_MATERIAL.getValue(new ResourceLocation(armorMaterial)).getArmorMaterialObject();
    }
}

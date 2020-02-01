package api.contentpack.common.json.datas.materials;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ArmorMaterialObject {

    private int[] maxDamageArray;
    private int maxDamageFactor;
    private int[] damageReductionAmountArray;
    private int enchantability;
    private String repairItem;
    private String materialName;
    private float toughness;

    private String registryName;

    public int[] getMaxDamageArray() {
        return maxDamageArray;
    }

    public void setMaxDamageArray(int[] maxDamageArray) {
        this.maxDamageArray = maxDamageArray;
    }

    public int getMaxDamageFactor() {
        return maxDamageFactor;
    }

    public void setMaxDamageFactor(int maxDamageFactor) {
        this.maxDamageFactor = maxDamageFactor;
    }

    public int[] getDamageReductionAmountArray() {
        return damageReductionAmountArray;
    }

    public void setDamageReductionAmountArray(int[] damageReductionAmountArray) {
        this.damageReductionAmountArray = damageReductionAmountArray;
    }

    public int getEnchantability() {
        return enchantability;
    }

    public void setEnchantability(int enchantability) {
        this.enchantability = enchantability;
    }

    public String getRepairItem() {
        return repairItem;
    }

    public void setRepairItem(String repairItem) {
        this.repairItem = repairItem;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public float getToughness() {
        return toughness;
    }

    public void setToughness(float toughness) {
        this.toughness = toughness;
    }

    public String getRegistryName() {
        return registryName;
    }

    public IArmorMaterial getMaterial() {
        return new IArmorMaterial() {
            @Override
            public int getDurability(EquipmentSlotType slotIn) {
                return maxDamageArray[slotIn.getIndex()] * maxDamageFactor;
            }

            @Override
            public int getDamageReductionAmount(EquipmentSlotType slotIn) {
                return damageReductionAmountArray[slotIn.getIndex()];
            }

            @Override
            public int getEnchantability() {
                return enchantability;
            }

            //TODO List All Sounds
            @Override
            public SoundEvent getSoundEvent() {
                return null;
            }

            @Override
            public Ingredient getRepairMaterial() {
                Item repairMaterial = ForgeRegistries.ITEMS.getValue(new ResourceLocation(repairItem));
                return repairMaterial != null ? Ingredient.fromItems(repairMaterial) : null;
            }

            @Override
            public String getName() {
                return registryName;
            }

            @Override
            public float getToughness() {
                return toughness;
            }
        };
    }
}
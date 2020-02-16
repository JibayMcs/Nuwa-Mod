package fr.zeamateis.nuwa.contentpack.common.json.data.materials;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ToolMaterialObject implements IItemTier {

    private float attackDamage, efficiency;
    private int durability, harvestLevel, enchantability;
    private String repairItem;
    private String registryName;

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public float getEfficiency() {
        return this.efficiency;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getMaxUses() {
        return this.durability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        Item repairMaterial = ForgeRegistries.ITEMS.getValue(new ResourceLocation(repairItem));
        return repairMaterial != null ? Ingredient.fromItems(repairMaterial) : null;
    }

    public String getRegistryName() {
        return registryName;
    }

}
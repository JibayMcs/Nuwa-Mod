package fr.zeamateis.nuwa.contentpack.common.json.data.items.properties;

import net.minecraft.util.ResourceLocation;

public class ToolMaterialProperties {

    private String toolMaterial;
    private float attackDamage, attackSpeed;

    public float getAttackDamage() {
        return attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public ResourceLocation getToolMaterial() {
        return new ResourceLocation(toolMaterial);
    }

}

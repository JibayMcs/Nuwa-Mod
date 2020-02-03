package api.contentpack.common.json.datas.items.properties;

import api.contentpack.common.json.datas.materials.ToolMaterialObject;
import fr.zeamateis.nuwa.init.NuwaRegistries;
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

    public ToolMaterialObject getToolMaterial() {
        return NuwaRegistries.TOOL_MATERIAL.getValue(new ResourceLocation(toolMaterial)).getToolMaterialObject();
    }

}
package tests;

import api.contentpack.common.json.datas.items.armor.ArmorMaterialProperties;
import api.contentpack.common.json.datas.items.tool.ToolMaterialProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.inventory.EquipmentSlotType;

public class JsonTests {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ArmorMaterialProperties materialObject = new ArmorMaterialProperties(
                EquipmentSlotType.HEAD,
                "twitch:test_material"
        );

        ToolMaterialProperties toolPropertiesObject = new ToolMaterialProperties();


        String materialJson = gson.toJson(materialObject);
        System.out.println(materialJson);

        ArmorMaterialProperties parsedArmorMaterial = gson.fromJson(materialJson, ArmorMaterialProperties.class);
        System.out.println(parsedArmorMaterial.getArmorMaterial().getMaterial().getName());

        String toolJson = gson.toJson(toolPropertiesObject);
        System.out.println(toolJson);

        ToolMaterialProperties parsedToolMaterial = gson.fromJson(toolJson, ToolMaterialProperties.class);
        System.out.println(parsedToolMaterial.getToolMaterial().getAttackDamage());
    }
}

package api.contentpack.common.json.datas.items;

import api.contentpack.common.json.datas.items.armor.ArmorMaterialProperties;
import api.contentpack.common.json.datas.items.properties.ItemsPropertiesObject;
import api.contentpack.common.json.datas.items.tool.ToolMaterialProperties;
import api.contentpack.common.json.datas.items.type.ItemType;

public class ItemsObject {

    private ItemsPropertiesObject properties;
    private String registryName;
    private String itemGroup;
    private ItemType itemType;

    private ArmorMaterialProperties armorProperties;
    private ToolMaterialProperties toolProperties;

    public ItemsPropertiesObject getProperties() {
        return properties;
    }

    public String getRegistryName() {
        return registryName;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public ArmorMaterialProperties getArmorProperties() {
        return armorProperties;
    }

    public ToolMaterialProperties getToolProperties() {
        return toolProperties;
    }
}

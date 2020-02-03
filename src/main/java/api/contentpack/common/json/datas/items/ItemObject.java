package api.contentpack.common.json.datas.items;

import api.contentpack.common.json.datas.items.properties.ArmorMaterialProperties;
import api.contentpack.common.json.datas.items.properties.ItemsPropertiesObject;
import api.contentpack.common.json.datas.items.properties.SoundProperties;
import api.contentpack.common.json.datas.items.properties.ToolMaterialProperties;

public class ItemObject {

    private ItemsPropertiesObject properties;
    private String registryName;
    private String itemGroup;
    private String itemType;

    private String cropsBlock;

    private ArmorMaterialProperties armorProperties;
    private ToolMaterialProperties toolProperties;
    private SoundProperties soundProperties;

    public ItemsPropertiesObject getProperties() {
        return properties;
    }

    public String getRegistryName() {
        return registryName;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public String getItemType() {
        return itemType;
    }

    public String getCropsBlock() {
        return cropsBlock;
    }

    public ArmorMaterialProperties getArmorProperties() {
        return armorProperties;
    }

    public ToolMaterialProperties getToolProperties() {
        return toolProperties;
    }

    public SoundProperties getSoundProperties() {
        return soundProperties;
    }
}

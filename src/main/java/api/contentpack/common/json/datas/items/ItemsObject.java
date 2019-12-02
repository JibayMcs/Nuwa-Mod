package api.contentpack.common.json.datas.items;

import api.contentpack.common.json.datas.items.properties.ItemsPropertiesObject;
import api.contentpack.common.json.datas.items.type.ItemType;

public class ItemsObject {

    private ItemsPropertiesObject properties;
    private String registryName;
    private String itemGroup;
    private ItemType itemType;

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
}

package api.contentpack.common.json.datas.items;

import api.contentpack.common.json.datas.items.properties.ItemsPropertiesObject;

public class ItemsObject {

    private ItemsPropertiesObject properties;
    private String registryName;
    private String itemGroup;

    public ItemsPropertiesObject getProperties() {
        return properties;
    }

    public String getRegistryName() {
        return registryName;
    }

    public String getItemGroup() {
        return itemGroup;
    }
}

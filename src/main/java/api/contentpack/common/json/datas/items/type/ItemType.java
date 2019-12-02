package api.contentpack.common.json.datas.items.type;

import api.contentpack.common.minecraft.items.JsonItem;
import api.contentpack.common.minecraft.items.JsonNamedItem;
import net.minecraft.item.Item;

public enum ItemType {
    DEFAULT(JsonItem.class),
    SEEDS(JsonNamedItem.class);

    private Class<? extends Item> itemType;

    ItemType(Class<? extends Item> itemType) {
        this.itemType = itemType;
    }

    public Class<? extends Item> getItemType() {
        return itemType;
    }
}

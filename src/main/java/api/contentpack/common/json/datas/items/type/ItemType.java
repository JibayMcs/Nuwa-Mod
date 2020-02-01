package api.contentpack.common.json.datas.items.type;

import api.contentpack.common.minecraft.items.JsonArmorItem;
import api.contentpack.common.minecraft.items.JsonSwordItem;
import api.contentpack.common.minecraft.items.base.JsonBlockItem;
import api.contentpack.common.minecraft.items.base.JsonItem;
import api.contentpack.common.minecraft.items.tool.JsonAxeItem;
import api.contentpack.common.minecraft.items.tool.JsonHoeItem;
import api.contentpack.common.minecraft.items.tool.JsonPixaxeItem;
import api.contentpack.common.minecraft.items.tool.JsonShovelItem;
import net.minecraft.item.Item;

public enum ItemType {
    DEFAULT(JsonItem.class),
    ARMOR(JsonArmorItem.class),
    AXE(JsonAxeItem.class),
    HOE(JsonHoeItem.class),
    PICKAXE(JsonPixaxeItem.class),
    SEEDS(JsonBlockItem.class),
    SHOVEL(JsonShovelItem.class),
    SWORD(JsonSwordItem.class);

    private Class<? extends Item> itemType;

    ItemType(Class<? extends Item> itemType) {
        this.itemType = itemType;
    }

    public Class<? extends Item> getItemType() {
        return itemType;
    }
}

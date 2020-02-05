package fr.zeamateis.nuwa.contentpack.common.json.data.items.type;

import fr.zeamateis.nuwa.contentpack.common.minecraft.items.JsonArmorItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.JsonMusicDiskItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.JsonSwordItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.base.JsonBlockItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.base.JsonItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.tool.JsonAxeItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.tool.JsonHoeItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.tool.JsonPixaxeItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.tool.JsonShovelItem;
import net.minecraft.item.Item;

public enum ItemType {
    NULL(),
    DEFAULT(JsonItem.class),
    ARMOR(JsonArmorItem.class),
    AXE(JsonAxeItem.class),
    HOE(JsonHoeItem.class),
    PICKAXE(JsonPixaxeItem.class),
    SEEDS(JsonBlockItem.class),
    SHOVEL(JsonShovelItem.class),
    SWORD(JsonSwordItem.class),
    MUSIC_DISC(JsonMusicDiskItem.class);

    private Class<? extends Item> itemType;

    ItemType() {
    }

    ItemType(Class<? extends Item> itemType) {
        this.itemType = itemType;
    }

    public static ItemType itemTypeOf(String valueIn) {
        try {
            return ItemType.valueOf(valueIn);
        } catch (IllegalArgumentException ex) {
            return NULL;
        }
    }

    public Class<? extends Item> getItemType() {
        return itemType;
    }
}

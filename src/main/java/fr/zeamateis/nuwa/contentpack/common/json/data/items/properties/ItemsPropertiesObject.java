package fr.zeamateis.nuwa.contentpack.common.json.data.items.properties;

import com.google.gson.annotations.SerializedName;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Reprensentation of Json {@link Item.Properties} properties
 *
 * @author ZeAmateis
 */
public class ItemsPropertiesObject {

    /**
     * Default Properties for a basic item if json file has no datas
     */
    private String toolType;
    private int toolTypeLevel = -1;
    private String containerItem;
    @SerializedName("defaultMaxDurability")
    private int defaultMaxDamage = -1;
    @SerializedName("maxDurability")
    private int maxDamage = -1;
    private FoodProperties food;
    private int maxStackSize = 64;
    private boolean noRepair;
    private Rarity rarity = Rarity.COMMON;

    /**
     * Getting parsed {@link Item.Properties} json object
     *
     * @return The parsed item properties
     */
    public Item.Properties getParsedProperties() {
        Item.Properties properties = new Item.Properties();

        if (toolType != null && toolTypeLevel != -1)
            properties.addToolType(ToolType.get(getToolType()), getToolTypeLevel());
        if (containerItem != null)
            properties.containerItem(ForgeRegistries.ITEMS.getValue(new ResourceLocation(getContainerItem())));
        if (defaultMaxDamage != -1) properties.defaultMaxDamage(getDefaultMaxDamage());
        if (food != null) properties.food(food.getFood());
        if (maxDamage != -1) properties.maxDamage(getMaxDamage());
        if (getMaxDamage() <= 0) {
            properties.maxStackSize(getMaxStackSize());
        }
        if (noRepair()) {
            properties.setNoRepair();
        }

        //TODO Implements Later.
        //properties.setTEISR();

        properties.rarity(getRarity());
        return properties;
    }

    private String getToolType() {
        return toolType;
    }

    private int getToolTypeLevel() {
        return toolTypeLevel;
    }

    private String getContainerItem() {
        return containerItem;
    }

    private int getDefaultMaxDamage() {
        return defaultMaxDamage;
    }

    private int getMaxDamage() {
        return maxDamage;
    }

    private Food getFood() {
        return food.getFood();
    }

    private int getMaxStackSize() {
        return Math.min(maxStackSize, 64);
    }

    private boolean noRepair() {
        return noRepair;
    }

    private Rarity getRarity() {
        return rarity;
    }
}
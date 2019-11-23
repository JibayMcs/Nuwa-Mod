package api.contentpack.common.json.datas.items.properties;

import com.google.gson.annotations.SerializedName;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsPropertiesObject {

    /**
     * Default Properties for a basic item if json file has no datas
     */
    private String toolType = "";
    private int toolTypeLevel;
    private String containerItem = "";
    @SerializedName("defaultMaxDurability")
    private int defaultMaxDamage;
    @SerializedName("maxDurability")
    private int maxDamage;
    private String food = "";
    private int maxStackSize = 64;
    private boolean noRepair = false;
    private Rarity rarity;

    public Item.Properties getParsedProperties() {
        Item.Properties properties = new Item.Properties();

        properties.addToolType(ToolType.get(getToolType()), getToolTypeLevel());
        properties.containerItem(ForgeRegistries.ITEMS.getValue(new ResourceLocation(getContainerItem())));
        properties.defaultMaxDamage(getDefaultMaxDamage());
        properties.food(ForgeRegistries.ITEMS.getValue(new ResourceLocation(getFood())).getFood());
        properties.maxDamage(getMaxDamage());
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

    private String getFood() {
        return food;
    }

    private int getMaxStackSize() {
        return maxStackSize;
    }

    private boolean noRepair() {
        return noRepair;
    }

    private Rarity getRarity() {
        return rarity;
    }
}
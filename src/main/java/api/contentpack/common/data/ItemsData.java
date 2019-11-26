package api.contentpack.common.data;

import api.contentpack.client.itemGroup.ItemGroups;
import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.items.ItemsObject;
import api.contentpack.common.minecraft.items.JsonItem;
import com.google.common.reflect.TypeToken;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipFile;

public class ItemsData implements IPackData {

    private final LinkedList<Item> itemList;

    public ItemsData() {
        itemList = new LinkedList<>();
    }

    @Override
    public String getEntryName() {
        return "objects/items.json";
    }

    @Override
    public void parseData(ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        Type itemsType = new TypeToken<List<ItemsObject>>() {
        }.getType();
        List<ItemsObject> itemsList = PackManager.GSON.fromJson(readerIn, itemsType);

        if (itemsType != null && itemsList != null) {
            itemsList.forEach(itemsObject -> {
                ResourceLocation itemRegistryName = new ResourceLocation(contentPackIn.getNamespace(), itemsObject.getRegistryName());

                Item.Properties properties = itemsObject.getProperties() != null ? itemsObject.getProperties().getParsedProperties() : new Item.Properties();

                JsonItem parsedItem = new JsonItem(properties, Objects.requireNonNull(itemRegistryName));

                if (itemsObject.getItemGroup() != null) {
                    ResourceLocation parsedItemGroup = new ResourceLocation(itemsObject.getItemGroup());
                    if (ItemGroups.contains(parsedItemGroup)) {
                        properties.group(ItemGroups.get(parsedItemGroup));
                    } else {
                        PackManager.throwItemGroupWarn(contentPackIn, zipFileIn, getEntryName(), parsedItemGroup);
                    }
                } else {
                    properties.group(ItemGroups.get(new ResourceLocation("minecraft:misc")));
                }

                itemList.add(parsedItem);
            });
        }
    }

    @Override
    public LinkedList<Item> getObjectsList() {
        return itemList;
    }
}

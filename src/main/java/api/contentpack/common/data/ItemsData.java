package api.contentpack.common.data;

import api.contentpack.client.itemGroup.ItemGroups;
import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.items.ItemsObject;
import api.contentpack.common.json.datas.items.type.ItemType;
import api.contentpack.common.minecraft.items.IJsonItem;
import com.google.common.reflect.TypeToken;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipFile;

public class ItemsData implements IPackData {

    private final LinkedList<IJsonItem> itemList;

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

                IJsonItem parsedItem;

                ItemType itemType = itemsObject.getItemType() != null ? itemsObject.getItemType() : ItemType.DEFAULT;

                Item.Properties properties = itemsObject.getProperties() != null ? itemsObject.getProperties().getParsedProperties() : new Item.Properties();

                try {
                    parsedItem = (IJsonItem) itemType.getItemType()
                            .getDeclaredConstructor(Item.Properties.class, ResourceLocation.class)
                            .newInstance(properties, itemRegistryName);

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

                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public LinkedList<IJsonItem> getObjectsList() {
        return itemList;
    }
}

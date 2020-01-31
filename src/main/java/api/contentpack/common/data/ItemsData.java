package api.contentpack.common.data;

import api.contentpack.client.itemGroup.ItemGroups;
import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.items.ItemsObject;
import api.contentpack.common.json.datas.items.type.ItemType;
import api.contentpack.common.minecraft.items.base.IJsonItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class ItemsData implements IPackData {

    private final LinkedList<IJsonItem> itemList;

    public ItemsData() {
        itemList = new LinkedList<>();
    }

    @Override
    public String getEntryFolder() {
        return "objects/items/";
    }

    @Override
    public void parseData(ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {

        ItemsObject itemsObject = PackManager.GSON.fromJson(readerIn, ItemsObject.class);

        ResourceLocation itemRegistryName = new ResourceLocation(contentPackIn.getNamespace(), itemsObject.getRegistryName());

        IJsonItem parsedItem;

        ItemType itemType = itemsObject.getItemType() != null ? itemsObject.getItemType() : ItemType.DEFAULT;

        Item.Properties properties = itemsObject.getProperties() != null ? itemsObject.getProperties().getParsedProperties() : new Item.Properties();

        try {
            if (itemType.equals(ItemType.SEEDS)) {
                parsedItem = (IJsonItem) itemType.getItemType()
                        .getDeclaredConstructor(Block.class, Item.Properties.class, ResourceLocation.class)
                        .newInstance(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("mff", "leek")), properties, itemRegistryName);
                //contentPackIn.getObjectsList().forEach(iForgeRegistryEntry -> System.out.println(iForgeRegistryEntry.getRegistryName()));
                System.out.println(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("mff", "leek")).getRegistryName());
            } else {
                parsedItem = (IJsonItem) itemType.getItemType()
                        .getDeclaredConstructor(Item.Properties.class, ResourceLocation.class)
                        .newInstance(properties, itemRegistryName);
            }

            if (itemsObject.getItemGroup() != null) {
                ResourceLocation parsedItemGroup = new ResourceLocation(itemsObject.getItemGroup());
                if (ItemGroups.contains(parsedItemGroup)) {
                    properties.group(ItemGroups.get(parsedItemGroup));
                } else {
                    PackManager.throwItemGroupWarn(contentPackIn, zipFileIn, getEntryFolder(), parsedItemGroup);
                }
            } else {
                properties.group(ItemGroups.get(new ResourceLocation("minecraft:misc")));
            }

            itemList.add(parsedItem);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedList<IJsonItem> getObjectsList() {
        return itemList;
    }

    @Override
    public IForgeRegistry<Item> getObjectsRegistry() {
        return ForgeRegistries.ITEMS;
    }
}

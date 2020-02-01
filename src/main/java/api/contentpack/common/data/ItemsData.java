package api.contentpack.common.data;

import api.contentpack.client.itemGroup.ItemGroups;
import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.items.ItemsObject;
import api.contentpack.common.json.datas.items.properties.ArmorMaterialProperties;
import api.contentpack.common.json.datas.items.properties.ToolMaterialProperties;
import api.contentpack.common.json.datas.items.type.ItemType;
import api.contentpack.common.minecraft.items.base.IJsonItem;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipFile;

public class ItemsData implements IPackData {

    private final LinkedList<IJsonItem> itemList;

    public ItemsData() {
        this.itemList = new LinkedList<>();
    }

    @Override
    public String getEntryFolder() {
        return "objects/items/";
    }

    @Override
    public void parseData(ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {

        ItemsObject itemsObject = PackManager.GSON.fromJson(readerIn, ItemsObject.class);

        ResourceLocation itemRegistryName = new ResourceLocation(contentPackIn.getNamespace(), itemsObject.getRegistryName());

        AtomicReference<IJsonItem> parsedItem = new AtomicReference<>();

        ItemType itemType = itemsObject.getItemType() != null ? itemsObject.getItemType() : ItemType.DEFAULT;

        Item.Properties properties = itemsObject.getProperties() != null ? itemsObject.getProperties().getParsedProperties() : new Item.Properties();

        ArmorMaterialProperties armorMaterialProperties = itemsObject.getArmorProperties();
        ToolMaterialProperties toolMaterialProperties = itemsObject.getToolProperties();

        try {
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

            switch (itemType) {
                case SEEDS: {
                    parsedItem.set((IJsonItem) itemType.getItemType()
                            .getDeclaredConstructor(Block.class, Item.Properties.class, ResourceLocation.class)
                            .newInstance(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(itemsObject.getCropsBlock())), properties, itemRegistryName));
                }
                break;
                case ARMOR: {
                    if (armorMaterialProperties != null) {
                        IArmorMaterial armorMaterial = armorMaterialProperties.getArmorMaterial().getMaterial();
                        EquipmentSlotType equipmentSlotType = armorMaterialProperties.getEquipmentSlotType();
                        parsedItem.set((IJsonItem) itemType.getItemType()
                                .getDeclaredConstructor(IArmorMaterial.class, EquipmentSlotType.class, Item.Properties.class, ResourceLocation.class)
                                .newInstance(armorMaterial, equipmentSlotType, properties, itemRegistryName));
                    }
                }
                break;
                case AXE:
                case SWORD: {
                    if (toolMaterialProperties != null) {
                        parsedItem.set((IJsonItem) itemType.getItemType()
                                .getDeclaredConstructor(IItemTier.class, float.class, float.class, Item.Properties.class, ResourceLocation.class)
                                .newInstance(toolMaterialProperties.getToolMaterial(), toolMaterialProperties.getAttackDamage(), toolMaterialProperties.getAttackSpeed(), properties, itemRegistryName));
                    }
                }
                break;
                case HOE:
                case PICKAXE:
                case SHOVEL: {
                    if (toolMaterialProperties != null) {
                        parsedItem.set((IJsonItem) itemType.getItemType()
                                .getDeclaredConstructor(IItemTier.class, float.class, Item.Properties.class, ResourceLocation.class)
                                .newInstance(toolMaterialProperties.getToolMaterial(), toolMaterialProperties.getAttackSpeed(), properties, itemRegistryName));
                    }
                }
                break;
                case DEFAULT:
                    parsedItem.set((IJsonItem) itemType.getItemType()
                            .getDeclaredConstructor(Item.Properties.class, ResourceLocation.class)
                            .newInstance(properties, itemRegistryName));
                    break;

            }
            itemList.add(parsedItem.get());
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

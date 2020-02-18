package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import com.google.common.collect.Sets;
import fr.zeamateis.nuwa.contentpack.common.json.data.items.ItemObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.items.properties.ArmorMaterialProperties;
import fr.zeamateis.nuwa.contentpack.common.json.data.items.properties.ToolMaterialProperties;
import fr.zeamateis.nuwa.contentpack.common.json.data.items.type.ItemType;
import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.JsonOreBlock;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.base.IJsonItem;
import fr.zeamateis.nuwa.init.NuwaRegistries;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipFile;

/**
 * Data reading class for json representation of {@link Item} objects
 *
 * @author ZeAmateis
 */
public class ItemsData implements IPackData {

    private final LinkedList<IJsonItem> itemList;

    public ItemsData() {
        this.itemList = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return the full entry folder path
     */
    @Override
    public String getEntryFolder() {
        return "objects/items/";
    }

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack
     *
     * @param packManagerIn The {@link PackManager} instance
     * @param contentPackIn The {@link ContentPack} instance
     * @param readerIn      The {@link InputStreamReader} instance
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, InputStreamReader readerIn) {

        ItemObject itemsObject = packManagerIn.getGson().fromJson(readerIn, ItemObject.class);

        ResourceLocation itemRegistryName = new ResourceLocation(contentPackIn.getNamespace(), itemsObject.getRegistryName());

        AtomicReference<IJsonItem> parsedItem = new AtomicReference<>();

        ItemType itemType = itemsObject.getItemType() != null ? ItemType.itemTypeOf(itemsObject.getItemType()) : ItemType.DEFAULT;

        Item.Properties properties = itemsObject.getProperties() != null ? itemsObject.getProperties().getParsedProperties() : new Item.Properties();

        ArmorMaterialProperties armorMaterialProperties = itemsObject.getArmorProperties();
        ToolMaterialProperties toolMaterialProperties = itemsObject.getToolProperties();

        if (itemType != ItemType.NULL) {
            try {

                //ItemGroup
                if (itemsObject.getItemGroup() != null) {
                    ResourceLocation parsedItemGroup = new ResourceLocation(itemsObject.getItemGroup());

                    if (NuwaRegistries.ITEM_GROUP.getValue(parsedItemGroup) != null) {
                        properties.group(NuwaRegistries.ITEM_GROUP.getValue(parsedItemGroup).getItemGroup());
                    } else {
                        packManagerIn.throwItemGroupWarn(contentPackIn, getEntryFolder(), parsedItemGroup);
                    }
                } else {
                    properties.group(ItemGroup.MISC);
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
                            IArmorMaterial armorMaterial = NuwaRegistries.ARMOR_MATERIAL.getValue(armorMaterialProperties.getArmorMaterial()).getArmorMaterial();
                            EquipmentSlotType equipmentSlotType = armorMaterialProperties.getEquipmentSlotType();
                            try {
                                parsedItem.set((IJsonItem) itemType.getItemType()
                                        .getDeclaredConstructor(IArmorMaterial.class, EquipmentSlotType.class, Item.Properties.class, ResourceLocation.class)
                                        .newInstance(armorMaterial, equipmentSlotType, properties, itemRegistryName));
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                    case AXE:
                    case SHOVEL:
                    case SWORD: {
                        if (toolMaterialProperties != null) {

                            IItemTier toolMaterial = NuwaRegistries.TOOL_MATERIAL.getValue(toolMaterialProperties.getToolMaterial()).getToolMaterial();
                            try {
                                parsedItem.set((IJsonItem) itemType.getItemType()
                                        .getDeclaredConstructor(IItemTier.class, float.class, float.class, Item.Properties.class, ResourceLocation.class)
                                        .newInstance(toolMaterial, toolMaterialProperties.getAttackDamage(), toolMaterialProperties.getAttackSpeed(), properties, itemRegistryName));
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                    case PICKAXE: {
                        if (toolMaterialProperties != null) {
                            Set<Block> effectiveOn =
                                    Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX);

                            ForgeRegistries.BLOCKS.getValues().stream()
                                    .filter(block -> block instanceof JsonOreBlock)
                                    .forEach(effectiveOn::add);

                            IItemTier toolMaterial = NuwaRegistries.TOOL_MATERIAL.getValue(toolMaterialProperties.getToolMaterial()).getToolMaterial();
                            try {
                                parsedItem.set((IJsonItem) itemType.getItemType()
                                        .getDeclaredConstructor(IItemTier.class, Set.class, float.class, float.class, Item.Properties.class, ResourceLocation.class)
                                        .newInstance(toolMaterial, effectiveOn, toolMaterialProperties.getAttackDamage(), toolMaterialProperties.getAttackSpeed(), properties, itemRegistryName));
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    break;
                    case HOE: {
                        if (toolMaterialProperties != null) {
                            IItemTier toolMaterial = NuwaRegistries.TOOL_MATERIAL.getValue(toolMaterialProperties.getToolMaterial()).getToolMaterial();
                            try {
                                parsedItem.set((IJsonItem) itemType.getItemType()
                                        .getDeclaredConstructor(IItemTier.class, float.class, Item.Properties.class, ResourceLocation.class)
                                        .newInstance(toolMaterial, toolMaterialProperties.getAttackSpeed(), properties, itemRegistryName));
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    break;
                    case MUSIC_DISC: {
                        if (itemsObject.getSoundProperties() != null) {
                            int comparatorValue = itemsObject.getSoundProperties().getComparatorValue();
                            ResourceLocation soundEvent = new ResourceLocation(itemsObject.getSoundProperties().getSoundName());
                            parsedItem.set((IJsonItem) itemType.getItemType()
                                    .getDeclaredConstructor(int.class, SoundEvent.class, Item.Properties.class, ResourceLocation.class)
                                    .newInstance(comparatorValue, ForgeRegistries.SOUND_EVENTS.getValue(soundEvent), properties, itemRegistryName));
                        }
                    }
                    break;
                    case DEFAULT:
                        parsedItem.set((IJsonItem) itemType.getItemType()
                                .getDeclaredConstructor(Item.Properties.class, ResourceLocation.class)
                                .newInstance(properties, itemRegistryName));
                        break;
                }
                /*if (packManagerIn.getWhitelist() != null) {
                    if (!packManagerIn.getWhitelist().getItems().isEmpty()) {
                        packManagerIn.getWhitelist().getItems().stream()
                                .filter(s -> !s.equals(parsedItem.get().getRegistryName().toString()))
                                .forEach(s -> itemList.add(parsedItem.get()));
                    } else {
                        itemList.add(parsedItem.get());
                    }
                } else {*/
                itemList.add(parsedItem.get());
                // }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList} type of {@link IForgeRegistryEntry}
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    @Override
    public LinkedList<IJsonItem> getObjectsList() {
        return itemList;
    }

}

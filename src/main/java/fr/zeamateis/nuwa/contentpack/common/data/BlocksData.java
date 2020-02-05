package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.BlockObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties.BlockEventsObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties.OreProperties;
import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.type.BlockType;
import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.JsonCropsBlock;
import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.IJsonBlock;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.base.JsonBlockItem;
import fr.zeamateis.nuwa.init.NuwaRegistries;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipFile;

public class BlocksData implements IPackData {

    private final LinkedList<IJsonBlock> blocksList;

    public BlocksData() {
        blocksList = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return String
     */
    @Override
    public String getEntryFolder() {
        return "objects/blocks/";
    }

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param packManagerIn
     * @param contentPackIn
     * @param zipFileIn
     * @param readerIn
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        BlockObject blocksObject = packManagerIn.getGson().fromJson(readerIn, BlockObject.class);

        AtomicReference<IJsonBlock> parsedBlock = new AtomicReference<>();

        ResourceLocation blockRegistryName = new ResourceLocation(contentPackIn.getNamespace(), blocksObject.getRegistryName());

        BlockType blockType = blocksObject.getBlockType() != null ? BlockType.blockTypeOf(blocksObject.getBlockType()) : BlockType.DEFAULT;

        Block.Properties properties = blocksObject.getProperties() != null ?
                blocksObject.getProperties().getParsedProperties() : blockType.getDefaultProperties();

        OreProperties oreProperties = blocksObject.getOreProperties();

        BlockEventsObject blocksObjectEvents = blocksObject.getEvents();

        //Events

        if (blockType != BlockType.NULL) {
            try {
                switch (blockType) {
                    case CROPS: {
                        parsedBlock.set((IJsonBlock) blockType.getBlockType()
                                .getDeclaredConstructor(Item.class, Block.Properties.class, ResourceLocation.class)
                                .newInstance(ForgeRegistries.ITEMS.getValue(new ResourceLocation(blocksObject.getCropSeed())), properties, blockRegistryName));
                    }
                    break;
                    case ORE: {
                        if (oreProperties != null) {
                            parsedBlock.set((IJsonBlock) blockType.getBlockType()
                                    .getDeclaredConstructor(int.class, int.class, Block.Properties.class, ResourceLocation.class)
                                    .newInstance(oreProperties.getMinExpDrop(), oreProperties.getMaxExpDrop(), properties, blockRegistryName));
                        }
                    }
                    break;
                    case DEFAULT:
                        parsedBlock.set((IJsonBlock) blockType.getBlockType()
                                .getDeclaredConstructor(Block.Properties.class, ResourceLocation.class)
                                .newInstance(properties, blockRegistryName));
                        break;
                }


                if (blocksObjectEvents != null) {
                    parsedBlock.get().setBlockEventObject(blocksObjectEvents);
                }

                //Voxel Shapes
                parsedBlock.get().setShape(blocksObject.getVoxelShape() != null ? blocksObject.getVoxelShape().getShape() : VoxelShapes.fullCube());
                parsedBlock.get().setCollisionShape(blocksObject.getVoxelShape() != null ? blocksObject.getVoxelShape().getCollisionShape() : VoxelShapes.fullCube());

                //ItemGroup
                if (!(parsedBlock.get() instanceof JsonCropsBlock)) {
                    if (blocksObject.getItemGroup() != null) {
                        ResourceLocation parsedItemGroup = new ResourceLocation(blocksObject.getItemGroup());
                        if (NuwaRegistries.ITEM_GROUP.getValue(parsedItemGroup) != null) {
                            parsedBlock.get().setItemGroup(NuwaRegistries.ITEM_GROUP.getValue(parsedItemGroup).getItemGroup());
                        } else {
                            packManagerIn.throwItemGroupWarn(contentPackIn, zipFileIn, getEntryFolder(), parsedItemGroup);
                        }
                    } else {
                        parsedBlock.get().setItemGroup(ItemGroup.MISC);
                    }
                }

                //Whitelisting
               /* if (packManagerIn.getWhitelist() != null) {
                    if (!packManagerIn.getWhitelist().getBlocks().isEmpty()) {
                        packManagerIn.getWhitelist().getBlocks().stream()
                                .filter(s -> !s.equals(parsedBlock.get().getRegistryName().toString()))
                                .forEach(s -> blocksList.add(parsedBlock.get()));
                    } else {
                        blocksList.add(parsedBlock.get());
                    }
                } else {*/
                blocksList.add(parsedBlock.get());
                //}

                //Register BlockItem
                JsonBlockItem jsonBlockItem = new JsonBlockItem(
                        parsedBlock.get().getBlock(),
                        new Item.Properties().group(parsedBlock.get().getItemGroup()),
                        Objects.requireNonNull(parsedBlock.get().getRegistryName()));
                ForgeRegistries.ITEMS.register(jsonBlockItem);


            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<IJsonBlock> getObjectsList() {
        return this.blocksList;
    }

}

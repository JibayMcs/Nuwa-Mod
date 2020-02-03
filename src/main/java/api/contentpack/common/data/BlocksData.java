package api.contentpack.common.data;

import api.contentpack.client.itemGroup.ItemGroups;
import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.blocks.BlockObject;
import api.contentpack.common.json.datas.blocks.events.actions.AttackProcess;
import api.contentpack.common.json.datas.blocks.events.actions.HealProcess;
import api.contentpack.common.json.datas.blocks.events.actions.base.IProcess;
import api.contentpack.common.json.datas.blocks.properties.BlockEventProperties;
import api.contentpack.common.json.datas.blocks.properties.OreProperties;
import api.contentpack.common.json.datas.blocks.type.BlockType;
import api.contentpack.common.minecraft.blocks.JsonCropsBlock;
import api.contentpack.common.minecraft.blocks.base.IJsonBlock;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import tests.JsonTests;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipFile;

public class BlocksData implements IPackData {

    private final LinkedList<IJsonBlock> blocksList;

    public BlocksData() {
        blocksList = new LinkedList();
    }

    @Override
    public String getEntryFolder() {
        return "objects/blocks/";
    }

    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        BlockObject blocksObject = new GsonBuilder()
                .registerTypeAdapter(IProcess.class, new JsonTests.InterfaceAdapter<AttackProcess>())
                .registerTypeAdapter(IProcess.class, new JsonTests.InterfaceAdapter<HealProcess>())
                .create()
                .fromJson(readerIn, BlockObject.class);

        AtomicReference<IJsonBlock> parsedBlock = new AtomicReference<>();

        ResourceLocation blockRegistryName = new ResourceLocation(contentPackIn.getNamespace(), blocksObject.getRegistryName());

        BlockType blockType = blocksObject.getBlockType() != null ? BlockType.blockTypeOf(blockRegistryName, blocksObject.getBlockType()) : BlockType.DEFAULT;

        Block.Properties properties = blocksObject.getProperties() != null ?
                blocksObject.getProperties().getParsedProperties() : blockType.getDefaultProperties();

        OreProperties oreProperties = blocksObject.getOreProperties();

        BlockEventProperties blockEventProperties = blocksObject.getEventProperties();

        //Events

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

            if (blockEventProperties != null) {
                parsedBlock.get().setBlockEventProperties(blockEventProperties);
            }


            //Voxel Shapes
            parsedBlock.get().setShape(blocksObject.getVoxelShape() != null ? blocksObject.getVoxelShape().getShape() : VoxelShapes.fullCube());
            parsedBlock.get().setCollisionShape(blocksObject.getVoxelShape() != null ? blocksObject.getVoxelShape().getCollisionShape() : VoxelShapes.fullCube());

            //ItemGroup
            if (!(parsedBlock.get() instanceof JsonCropsBlock)) {
                if (blocksObject.getItemGroup() != null) {
                    ResourceLocation parsedItemGroup = new ResourceLocation(blocksObject.getItemGroup());
                    if (ItemGroups.contains(parsedItemGroup)) {
                        parsedBlock.get().setItemGroup(ItemGroups.get(parsedItemGroup));
                    } else {
                        PackManager.throwItemGroupWarn(contentPackIn, zipFileIn, getEntryFolder(), parsedItemGroup);
                    }
                } else {
                    parsedBlock.get().setItemGroup(ItemGroups.get(new ResourceLocation("minecraft:misc")));
                }
            }

            //Whitelisting
            if (packManagerIn.getWhitelist() != null) {
                if (!packManagerIn.getWhitelist().getBlocks().isEmpty()) {
                    packManagerIn.getWhitelist().getBlocks().stream()
                            .filter(s -> !s.equals(parsedBlock.get().getRegistryName().toString()))
                            .forEach(s -> blocksList.add(parsedBlock.get()));
                } else {
                    blocksList.add(parsedBlock.get());
                }
            } else {
                blocksList.add(parsedBlock.get());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedList<IJsonBlock> getObjectsList() {
        return blocksList;
    }

    @Override
    public IForgeRegistry<Block> getObjectsRegistry() {
        return ForgeRegistries.BLOCKS;
    }
}

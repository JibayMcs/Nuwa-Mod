package api.contentpack.common.data;

import api.contentpack.client.itemGroup.ItemGroups;
import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.blocks.BlocksObject;
import api.contentpack.common.json.datas.blocks.type.BlockType;
import api.contentpack.common.minecraft.blocks.JsonCropsBlock;
import api.contentpack.common.minecraft.blocks.base.IJsonBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
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
    public void parseData(ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        BlocksObject blocksObject = PackManager.GSON.fromJson(readerIn, BlocksObject.class);

        IJsonBlock parsedBlock;

        ResourceLocation blockRegistryName = new ResourceLocation(contentPackIn.getNamespace(), blocksObject.getRegistryName());

        BlockType blockType = blocksObject.getBlockType() != null ? BlockType.blockTypeOf(blockRegistryName, blocksObject.getBlockType()) : BlockType.DEFAULT;

        Block.Properties properties = blocksObject.getProperties() != null ?
                blocksObject.getProperties().getParsedProperties() : blockType.getDefaultProperties();

        try {
            if (blockType.equals(BlockType.CROPS)) {
                parsedBlock = (IJsonBlock) blockType.getBlockType()
                        .getDeclaredConstructor(Item.class, Block.Properties.class, ResourceLocation.class)
                        .newInstance(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mff", "leek_seeds")), properties, blockRegistryName);
                //System.out.println(parsedBlock.getRegistryName());
            } else {
                parsedBlock = (IJsonBlock) blockType.getBlockType()
                        .getDeclaredConstructor(Block.Properties.class, ResourceLocation.class)
                        .newInstance(properties, blockRegistryName);
            }

            parsedBlock.setShape(blocksObject.getVoxelShape() != null ? blocksObject.getVoxelShape().getShape() : VoxelShapes.fullCube());
            parsedBlock.setCollisionShape(blocksObject.getVoxelShape() != null ? blocksObject.getVoxelShape().getCollisionShape() : VoxelShapes.fullCube());

            if (!(parsedBlock instanceof JsonCropsBlock)) {
                if (blocksObject.getItemGroup() != null) {
                    ResourceLocation parsedItemGroup = new ResourceLocation(blocksObject.getItemGroup());
                    if (ItemGroups.contains(parsedItemGroup)) {
                        parsedBlock.setItemGroup(ItemGroups.get(parsedItemGroup));
                    } else {
                        PackManager.throwItemGroupWarn(contentPackIn, zipFileIn, getEntryFolder(), parsedItemGroup);
                    }
                } else {
                    parsedBlock.setItemGroup(ItemGroups.get(new ResourceLocation("minecraft:misc")));
                }
            }

            this.blocksList.add(parsedBlock);

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

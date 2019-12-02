package api.contentpack.common.data;

import api.contentpack.client.itemGroup.ItemGroups;
import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.blocks.BlocksObject;
import api.contentpack.common.json.datas.blocks.type.BlockType;
import api.contentpack.common.minecraft.blocks.IJsonBlock;
import api.contentpack.common.minecraft.items.JsonBlockItem;
import com.google.common.reflect.TypeToken;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipFile;

public class BlocksData implements IPackData {

    private final LinkedList<IJsonBlock> blocksList;

    public BlocksData() {
        blocksList = new LinkedList();
    }

    @Override
    public String getEntryName() {
        return "objects/blocks.json";
    }

    @Override
    public void parseData(ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {

        Type blocksType = new TypeToken<List<BlocksObject>>() {
        }.getType();
        List<BlocksObject> blocksList = PackManager.GSON.fromJson(readerIn, blocksType);

        if (blocksType != null && blocksList != null) {
            blocksList.forEach(blocksObject -> {
                IJsonBlock parsedBlock;

                ResourceLocation blockRegistryName = new ResourceLocation(contentPackIn.getNamespace(), blocksObject.getRegistryName());

                BlockType blockType = blocksObject.getBlockType() != null ? blocksObject.getBlockType() : BlockType.DEFAULT;

                Block.Properties properties = blocksObject.getProperties() != null ?
                        blocksObject.getProperties().getParsedProperties() : blockType.getDefaultProperties();

                try {
                    parsedBlock = (IJsonBlock) blockType.getBlockType()
                            .getDeclaredConstructor(Block.Properties.class, ResourceLocation.class)
                            .newInstance(properties, blockRegistryName);

                    parsedBlock.setShape(blocksObject.getVoxelShape() != null ? blocksObject.getVoxelShape().getShape() : VoxelShapes.fullCube());
                    parsedBlock.setCollisionShape(blocksObject.getVoxelShape() != null ? blocksObject.getVoxelShape().getCollisionShape() : VoxelShapes.fullCube());

                    if (blocksObject.getItemGroup() != null) {
                        ResourceLocation parsedItemGroup = new ResourceLocation(blocksObject.getItemGroup());
                        if (ItemGroups.contains(parsedItemGroup)) {
                            parsedBlock.setItemGroup(ItemGroups.get(parsedItemGroup));
                        } else {
                            PackManager.throwItemGroupWarn(contentPackIn, zipFileIn, getEntryName(), parsedItemGroup);
                        }
                    } else {
                        parsedBlock.setItemGroup(ItemGroups.get(new ResourceLocation("minecraft:misc")));
                    }

                    this.blocksList.add(parsedBlock);

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            });
        }

        //Registering itemblocks
        this.blocksList.forEach(block -> {
            IJsonBlock jsonBlock = block;
            JsonBlockItem jsonBlockItem = new JsonBlockItem(jsonBlock.getBlock(), new Item.Properties().group(jsonBlock.getItemGroup()), Objects.requireNonNull(jsonBlock.getRegistryName()));
            contentPackIn.getObjectsList().add(jsonBlockItem);
        });
    }

    @Override
    public LinkedList<IJsonBlock> getObjectsList() {
        return blocksList;
    }


}

package api.contentpack.common.data;

import api.contentpack.client.itemGroup.VanillaItemGroups;
import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.blocks.BlocksObject;
import api.contentpack.common.minecraft.blocks.JsonBlock;
import api.contentpack.common.minecraft.items.JsonBlockItem;
import com.google.common.reflect.TypeToken;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipFile;

public class BlocksData implements IPackData {

    private final ContentPack contentPack;
    private final ZipFile zipFile;
    private LinkedList<Block> blocksList = new LinkedList();

    public BlocksData(ContentPack contentPackIn, ZipFile zipFileIn) {
        this.contentPack = contentPackIn;
        this.zipFile = zipFileIn;
    }

    @Override
    public String getEntryName() {
        return "objects/blocks.json";
    }

    @Override
    public void parseData(InputStreamReader readerIn) {
        Type blocksType = new TypeToken<List<BlocksObject>>() {
        }.getType();
        List<BlocksObject> blocksList = PackManager.GSON.fromJson(readerIn, blocksType);

        if (blocksType != null && blocksList != null) {
            blocksList.forEach(blocksObject -> {
                ResourceLocation blockRegistryName = new ResourceLocation(this.contentPack.getNamespace(), blocksObject.getRegistryName());

                Block.Properties properties = blocksObject.getProperties() != null ?
                        blocksObject.getProperties().getParsedProperties() : Block.Properties.create(Material.ROCK);

                JsonBlock parsedBlock = new JsonBlock(properties, Objects.requireNonNull(blockRegistryName));


                parsedBlock.setShape(blocksObject.getVoxelShape() != null ? blocksObject.getVoxelShape().getShape() : VoxelShapes.fullCube());
                parsedBlock.setCollisionShape(blocksObject.getVoxelShape() != null ? blocksObject.getVoxelShape().getCollisionShape() : VoxelShapes.fullCube());

                if (blocksObject.getItemGroup() != null) {
                    ResourceLocation parsedItemGroup = new ResourceLocation(blocksObject.getItemGroup());
                    if (VanillaItemGroups.getItemGroupsMap().containsKey(parsedItemGroup)) {
                        parsedBlock.setItemGroup(VanillaItemGroups.getItemGroupsMap().get(parsedItemGroup));
                    } else {
                        PackManager.throwItemGroupWarn(this.contentPack, this.zipFile, getEntryName(), parsedItemGroup);
                    }
                } else {
                    parsedBlock.setItemGroup(VanillaItemGroups.getItemGroupsMap().get(new ResourceLocation("minecraft:misc")));
                }


                this.blocksList.add(parsedBlock);
            });
        }

        //Registering itemblocks
        this.blocksList.forEach(block -> {
            JsonBlockItem jsonBlockItem;
            if (block instanceof JsonBlock) {
                JsonBlock jsonBlock = (JsonBlock) block;
                jsonBlockItem = new JsonBlockItem(jsonBlock, new Item.Properties().group(jsonBlock.getItemGroup()), Objects.requireNonNull(jsonBlock.getRegistryName()));
                this.contentPack.getItemList().add(jsonBlockItem);
            }

        });
    }

    @Override
    public LinkedList<Block> getObjectsList() {
        return blocksList;
    }


}

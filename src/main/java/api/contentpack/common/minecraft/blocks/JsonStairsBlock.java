package api.contentpack.common.minecraft.blocks;

import api.contentpack.common.json.datas.blocks.properties.BlockEventsObject;
import api.contentpack.common.minecraft.blocks.base.IJsonBlock;
import api.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.shapes.VoxelShape;

import javax.annotation.Nonnull;

public class JsonStairsBlock extends StairsBlock implements IJsonBlock {

    private VoxelShape shape, collisionShape;
    private ItemGroup itemGroup;
    private BlockEventsObject eventProperties;

    public JsonStairsBlock(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(Blocks.STONE.getDefaultState(), properties);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }

    @Override
    public BlockEventsObject getBlockEventObject() {
        return this.eventProperties;
    }

    @Override
    public void setBlockEventObject(BlockEventsObject eventProperties) {
        this.eventProperties = eventProperties;
    }

    @Override
    public VoxelShape getShape() {
        return shape;
    }

    @Override
    public void setShape(VoxelShape shape) {
        this.shape = shape;
    }

    @Override
    public VoxelShape getCollisionShape() {
        return collisionShape;
    }

    @Override
    public void setCollisionShape(VoxelShape collisionShape) {
        this.collisionShape = collisionShape;
    }


    @Override
    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    @Override
    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

}

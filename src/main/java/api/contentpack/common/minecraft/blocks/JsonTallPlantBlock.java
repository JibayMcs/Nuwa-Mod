package api.contentpack.common.minecraft.blocks;

import api.contentpack.common.json.datas.blocks.properties.BlockEventProperties;
import api.contentpack.common.minecraft.blocks.base.IJsonBlock;
import api.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.shapes.VoxelShape;

import javax.annotation.Nonnull;

public class JsonTallPlantBlock extends TallFlowerBlock implements IJsonBlock {

    private VoxelShape shape, collisionShape;
    private ItemGroup itemGroup;
    private BlockEventProperties eventProperties;

    public JsonTallPlantBlock(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }

    @Override
    public BlockEventProperties getBlockEventProperties() {
        return this.eventProperties;
    }

    @Override
    public void setBlockEventProperties(BlockEventProperties eventProperties) {
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

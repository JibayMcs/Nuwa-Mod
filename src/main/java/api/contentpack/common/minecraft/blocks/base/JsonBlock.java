package api.contentpack.common.minecraft.blocks.base;


import api.contentpack.common.json.datas.blocks.properties.BlockEventsObject;
import api.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class JsonBlock extends Block implements IJsonBlock {

    private VoxelShape shape, collisionShape;
    private ItemGroup itemGroup;

    private BlockEventsObject eventProperties;

    public JsonBlock(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties);
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
    public VoxelShape getCollisionShape() {
        return collisionShape;
    }

    @Override
    public void setCollisionShape(VoxelShape collisionShape) {
        this.collisionShape = collisionShape;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shape;
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
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (getBlockEventObject() != null && getBlockEventObject().getEntityCollideBlockEvent() != null) {
            return Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
        } else {
            return this.blocksMovement ? this.collisionShape : VoxelShapes.empty();
        }
    }

    @Override
    public boolean isSolid(BlockState state) {
        return this.blocksMovement;
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return !this.blocksMovement;
    }


    @Override
    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    @Override
    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        this.onEntityCollisionEvent(state, worldIn, pos, entityIn);
    }

}

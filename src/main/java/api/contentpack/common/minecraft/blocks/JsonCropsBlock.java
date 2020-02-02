package api.contentpack.common.minecraft.blocks;

import api.contentpack.common.minecraft.blocks.base.IJsonBlock;
import api.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

public class JsonCropsBlock extends CropsBlock implements IJsonBlock, IGrowable {
    private static final IntegerProperty CROPS_AGE = BlockStateProperties.AGE_0_7;
    private final Item seedItem;
    private VoxelShape shape, collisionShape;
    private ItemGroup itemGroup;

    public JsonCropsBlock(Item jsonNamedItem, Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties);
        this.seedItem = jsonNamedItem;
        RegistryUtil.forceRegistryName(this, registryNameIn);
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


    @Override
    @OnlyIn(Dist.CLIENT)
    protected IItemProvider getSeedsItem() {
        return this.seedItem;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(this.getSeedsItem());
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.getBlock() instanceof FarmlandBlock;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return CROPS_AGE;
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return !this.isMaxAge(state);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        this.grow(worldIn, pos, state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CROPS_AGE);
    }

}

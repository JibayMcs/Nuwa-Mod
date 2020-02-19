package fr.zeamateis.nuwa.contentpack.common.minecraft.blocks;

import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties.FallingProperties;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.blocks.BlockEventObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.IJsonBlock;
import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Json parsed {@link FallingBlock}
 *
 * @author ZeAmateis
 */
public class JsonFallingBlock extends FallingBlock implements IJsonBlock {

    private final FallingProperties fallingProperties;

    //IJsonBlock default impl
    private VoxelShape shape, collisionShape;
    private ItemGroup itemGroup;
    private BlockEventObject eventProperties;

    public JsonFallingBlock(FallingProperties fallingProperties, Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties);
        this.fallingProperties = fallingProperties;
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }

    public static boolean canFallThrough(BlockState state) {
        Block block = state.getBlock();
        Material material = state.getMaterial();
        return state.isAir() || block == Blocks.FIRE || material.isLiquid() || material.isReplaceable();
    }

    @Override
    public BlockEventObject getBlockEventObject() {
        return this.eventProperties;
    }

    @Override
    public void setBlockEventObject(BlockEventObject eventProperties) {
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

    //Falling Properties

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote) {
            this.checkFallable(worldIn, pos);
        }

    }

    private void checkFallable(World worldIn, BlockPos pos) {
        if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            if (!worldIn.isRemote) {
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
                this.onStartFalling(fallingblockentity);
                Vec3d fallVec = Vec3d.ZERO;
                if (fallingProperties != null && fallingProperties.getFallingVector() != null)
                    fallVec = new Vec3d(fallingProperties.getFallingVector()[0], fallingProperties.getFallingVector()[1], fallingProperties.getFallingVector()[2]);
                fallingblockentity.setMotion(fallVec);
                fallingblockentity.setNoGravity(fallingProperties.hasNoGravity());
                fallingblockentity.setHurtEntities(fallingProperties.isHurtingEntities());
                fallingblockentity.shouldDropItem = fallingProperties.shouldDropItem();
                fallingblockentity.dontSetBlock = fallingProperties.setNoBlockOnLand();
                fallingblockentity.fallHurtAmount = fallingProperties.getFallDamageAmount();
                worldIn.addEntity(fallingblockentity);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state) {
        return this.fallingProperties.getDustColor();
    }
}

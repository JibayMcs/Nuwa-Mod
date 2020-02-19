package fr.zeamateis.nuwa.contentpack.common.minecraft.blocks;

import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.JsonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Json parsed {@link net.minecraft.block.SoulSandBlock} type
 *
 * @author ZeAmateis
 */
public class JsonSlowBlock extends JsonBlock {

    private final double[] motionValue;

    public JsonSlowBlock(@Nonnull double[] motionValue, Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties, registryNameIn);
        this.motionValue = motionValue;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setMotion(entityIn.getMotion().mul(motionValue[0], motionValue[1], motionValue[2]));
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

}

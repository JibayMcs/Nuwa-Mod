package fr.zeamateis.nuwa.contentpack.common.minecraft.blocks;

import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.JsonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class JsonSlowBlock extends JsonBlock {

    public JsonSlowBlock(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties, registryNameIn);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setMotionMultiplier(state, new Vec3d(0.25D, (double) 0.05F, 0.25D));
    }
}

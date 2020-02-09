package fr.zeamateis.nuwa.contentpack.common.minecraft.blocks;

import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.IBiomeColor;
import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.JsonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class JsonBiomeColorBlock extends JsonBlock implements IBiomeColor {

    public JsonBiomeColorBlock(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties, registryNameIn);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        this.onEntityCollisionEvent(state, worldIn, pos, entityIn);
    }
}

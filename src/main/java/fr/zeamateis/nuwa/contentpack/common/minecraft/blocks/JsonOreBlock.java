package fr.zeamateis.nuwa.contentpack.common.minecraft.blocks;

import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.JsonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class JsonOreBlock extends JsonBlock {

    private final int minExpDrop, maxExpDrop;

    public JsonOreBlock(int minExpDrop, int maxExpDrop, Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties, registryNameIn);
        this.minExpDrop = minExpDrop;
        this.maxExpDrop = maxExpDrop;
    }


    private int getDrops(Random random) {
        return MathHelper.nextInt(random, this.minExpDrop, this.maxExpDrop);
    }

    /**
     * Perform side-effects from block dropping, such as creating silverfish
     */
    @Override
    public void spawnAdditionalDrops(BlockState state, World worldIn, BlockPos pos, ItemStack stack) {
        super.spawnAdditionalDrops(state, worldIn, pos, stack);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getDrops(RANDOM) : 0;
    }
}

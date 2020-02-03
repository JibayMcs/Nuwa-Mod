package api.contentpack.common.json.datas.blocks.events.actions.base;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IProcess {

    void processAction(World worldIn, BlockPos blockPosIn, Entity entityIn);

    String getRegistryName();
}

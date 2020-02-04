package api.contentpack.common.json.datas.events.processes.base;


import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface IEntityProcess extends IProcess {

    void process(World worldIn, Entity entityIn);
}

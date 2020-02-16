package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IProcess;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SetOnFireProcess implements IProcess {

    private int duration = -1;

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        if (!worldIn.isRemote()) {
            if (duration != -1)
                entityIn.setFire(duration);
        }
    }

    @Override
    public String getRegistryName() {
        return "nuwa:set_on_fire_process";
    }
}

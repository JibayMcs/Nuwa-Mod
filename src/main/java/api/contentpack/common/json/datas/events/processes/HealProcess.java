package api.contentpack.common.json.datas.events.processes;

import api.contentpack.common.json.datas.events.processes.base.IEntityProcess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class HealProcess implements IEntityProcess {

    private float healAmount;

    public float getHealAmount() {
        return healAmount;
    }

    @Override
    public void process(World worldIn, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            ((LivingEntity) entityIn).heal(getHealAmount());
        }
    }

    @Override
    public String getRegistryName() {
        return "nuwa:heal_process";
    }
}

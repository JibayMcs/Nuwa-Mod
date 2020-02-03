package api.contentpack.common.json.datas.blocks.events.actions;

import api.contentpack.common.json.datas.blocks.events.actions.base.IProcess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HealProcess implements IProcess {

    private float healAmount;

    public HealProcess() {
    }

    public HealProcess(float healAmount) {
        this.healAmount = healAmount;
    }

    public float getHealAmount() {
        return healAmount;
    }

    @Override
    public void processAction(World worldIn, BlockPos blockPosIn, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            ((LivingEntity) entityIn).heal(getHealAmount());
        }
    }

    @Override
    public String getRegistryName() {
        return "nuwa:heal_process";
    }
}

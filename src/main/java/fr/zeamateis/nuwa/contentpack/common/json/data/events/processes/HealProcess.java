package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import api.contentpack.json.process.IProcess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * {@link IProcess} to process {@link LivingEntity#heal(float)}
 *
 * @author ZeAmateis
 */
public class HealProcess implements IProcess {

    private float healAmount;

    public float getHealAmount() {
        return healAmount;
    }

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            ((LivingEntity) entityIn).heal(getHealAmount());
        }
    }

    @Override
    public String getRegistryName() {
        return "nuwa:heal_process";
    }
}

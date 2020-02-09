package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IProcess;
import fr.zeamateis.nuwa.contentpack.common.json.data.type.DamageSourceType;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class AttackProcess implements IProcess {

    private float attackDamage;
    private DamageSourceType damageSource;

    public float getAttackDamage() {
        return attackDamage;
    }

    public DamageSource getDamageSource() {
        return damageSource.getDamageSource();
    }

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        entityIn.attackEntityFrom(getDamageSource(), getAttackDamage());
    }

    @Override
    public String getRegistryName() {
        return "nuwa:attack_process";
    }
}

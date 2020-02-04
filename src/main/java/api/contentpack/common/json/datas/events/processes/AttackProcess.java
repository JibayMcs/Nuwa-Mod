package api.contentpack.common.json.datas.events.processes;

import api.contentpack.common.json.datas.damageSources.type.DamageSourceType;
import api.contentpack.common.json.datas.events.processes.base.IEntityProcess;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;


public class AttackProcess implements IEntityProcess {

    private float attackDamage;
    private DamageSourceType damageSource;

    public float getAttackDamage() {
        return attackDamage;
    }

    public DamageSource getDamageSource() {
        return damageSource.getDamageSource();
    }

    @Override
    public void process(World worldIn, Entity entityIn) {
        entityIn.attackEntityFrom(getDamageSource(), getAttackDamage());
    }

    @Override
    public String getRegistryName() {
        return "nuwa:attack_process";
    }
}

package api.contentpack.common.json.datas.blocks.events.actions;

import api.contentpack.common.json.datas.blocks.events.actions.base.IProcess;
import api.contentpack.common.json.datas.damageSources.type.DamageSourceType;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AttackProcess implements IProcess {

    private float attackDamage;
    private DamageSourceType damageSource;

    public AttackProcess() {
    }

    public AttackProcess(DamageSourceType damageSource, float attackDamage) {
        this.attackDamage = attackDamage;
        this.damageSource = damageSource;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public DamageSource getDamageSource() {
        return damageSource.getDamageSource();
    }

    @Override
    public void processAction(World worldIn, BlockPos blockPosIn, Entity entityIn) {
        entityIn.attackEntityFrom(getDamageSource(), getAttackDamage());
    }

    @Override
    public String getRegistryName() {
        return "nuwa:attack_process";
    }
}

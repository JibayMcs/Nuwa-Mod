package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IEntityProcess;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.PositionObject;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SummonProcess implements IEntityProcess {

    private String entity;
    private PositionObject position;
    private boolean randomizeProperties;

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        if (!worldIn.isRemote()) {
            ServerWorld serverworld = (ServerWorld) worldIn;
            if (entity != null) {
                if (position != null) {
                    BlockPos summonPos = position.getPos(serverworld, posIn);
                    ResourceLocation entityType = new ResourceLocation(entity);
                    if (!EntityType.getKey(EntityType.PLAYER).equals(entityType))
                        if (EntityType.getKey(EntityType.LIGHTNING_BOLT).equals(entityType)) {
                            LightningBoltEntity lightningboltentity = new LightningBoltEntity(serverworld, summonPos.getX(), summonPos.getY(), summonPos.getZ(), false);
                            serverworld.addLightningBolt(lightningboltentity);
                        } else {
                            CompoundNBT compoundnbt = new CompoundNBT();
                            compoundnbt.putString("id", entityType.toString());
                            Entity entity = EntityType.func_220335_a(compoundnbt, serverworld, (entityParsed) -> {
                                entityParsed.setLocationAndAngles(summonPos.getX(), summonPos.getY(), summonPos.getZ(), entityParsed.rotationYaw, entityParsed.rotationPitch);
                                return !serverworld.summonEntity(entityParsed) ? null : entityParsed;
                            });
                            if (entity != null) {
                                if (randomizeProperties && entity instanceof MobEntity) {
                                    ((MobEntity) entity).onInitialSpawn(serverworld, serverworld.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.COMMAND, (ILivingEntityData) null, (CompoundNBT) null);
                                }
                            }
                        }
                }
            }
        }
    }

    @Override
    public String getRegistryName() {
        return "nuwa:summon_process";
    }
}

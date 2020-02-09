package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IProcess;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.PositionObject;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;

import java.util.EnumSet;
import java.util.Set;

public class TeleportProcess implements IProcess {

    private PositionObject position;

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        if (worldIn instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) worldIn;
            if (!serverWorld.isRemote()) {
                float yaw = entityIn.rotationYaw;
                float pitch = entityIn.rotationPitch;

                if (position != null) {
                    Set<SPlayerPositionLookPacket.Flags> relativeList = EnumSet.noneOf(SPlayerPositionLookPacket.Flags.class);

                    BlockPos blockPos = position.getPos(serverWorld, posIn);
                    double x = blockPos.getX();
                    double y = blockPos.getY();
                    double z = blockPos.getZ();

                    if (position.getDimension() != null) {
                        DimensionType dimensionType = DimensionType.byName(new ResourceLocation(position.getDimension()));
                        if (dimensionType != null)
                            entityIn.changeDimension(dimensionType);
                    } else {
                        if (entityIn instanceof ServerPlayerEntity) {
                            ChunkPos chunkpos = new ChunkPos(blockPos);
                            serverWorld.getChunkProvider().func_217228_a(TicketType.POST_TELEPORT, chunkpos, 1, entityIn.getEntityId());
                            entityIn.stopRiding();
                            if (((ServerPlayerEntity) entityIn).isSleeping()) {
                                ((ServerPlayerEntity) entityIn).wakeUpPlayer(true, true, false);
                            }

                            if (serverWorld == entityIn.world) {
                                ((ServerPlayerEntity) entityIn).connection.setPlayerLocation(x, y, z, yaw, pitch, relativeList);
                            } else {
                                ((ServerPlayerEntity) entityIn).teleport(serverWorld, x, y, z, yaw, pitch);
                            }

                            entityIn.setRotationYawHead(yaw);
                        } else {
                            float f1 = MathHelper.wrapDegrees(yaw);
                            float f = MathHelper.wrapDegrees(pitch);
                            f = MathHelper.clamp(f, -90.0F, 90.0F);
                            if (serverWorld == entityIn.world) {
                                entityIn.setLocationAndAngles(x, y, z, f1, f);
                                entityIn.setRotationYawHead(f1);
                            } else {
                                entityIn.detach();
                                entityIn.dimension = serverWorld.dimension.getType();
                                Entity entity = entityIn;
                                entityIn = entityIn.getType().create(serverWorld);
                                if (entityIn == null) {
                                    return;
                                }

                                entityIn.copyDataFromOld(entity);
                                entityIn.setLocationAndAngles(x, y, z, f1, f);
                                entityIn.setRotationYawHead(f1);
                                serverWorld.func_217460_e(entityIn);
                            }
                        }
                        if (position.getFacing() != null) {
                            entityIn.lookAt(EntityAnchorArgument.Type.EYES, new Vec3d(position.getFacing()[0], position.getFacing()[1], position.getFacing()[2]));
                        }

                        if (!(entityIn instanceof LivingEntity) || !((LivingEntity) entityIn).isElytraFlying()) {
                            entityIn.setMotion(entityIn.getMotion().mul(1.0D, 0.0D, 1.0D));
                            entityIn.onGround = true;
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getRegistryName() {
        return "nuwa:teleport_process";
    }
}

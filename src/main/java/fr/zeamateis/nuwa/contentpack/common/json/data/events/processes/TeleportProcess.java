package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IEntityProcess;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.PositionObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;
import java.util.Set;

public class TeleportProcess implements IEntityProcess {

    private PositionObject position;

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        if (!worldIn.isRemote()) {
            if (entityIn instanceof ServerPlayerEntity) {
                ServerPlayerEntity playerEntity = (ServerPlayerEntity) entityIn;
                float yaw = playerEntity.rotationYaw;
                float pitch = playerEntity.rotationPitch;

                if (position != null) {
                    BlockPos blockPos = position.getPos(worldIn, posIn);
                    if (worldIn == entityIn.world) {
                        Set<SPlayerPositionLookPacket.Flags> set = EnumSet.noneOf(SPlayerPositionLookPacket.Flags.class);
                        playerEntity.connection.setPlayerLocation(blockPos.getX(), blockPos.getY(), blockPos.getZ(), yaw, pitch, set);
                    } else {
                        playerEntity.teleport((ServerWorld) worldIn, blockPos.getX(), blockPos.getY(), blockPos.getZ(), yaw, pitch);
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

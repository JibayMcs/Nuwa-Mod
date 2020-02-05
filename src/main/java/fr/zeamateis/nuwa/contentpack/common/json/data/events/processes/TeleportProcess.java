package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IEntityProcess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;
import java.util.Set;

public class TeleportProcess implements IEntityProcess {

    private int[] position;
    private String toPlayer;
    private String toEntity;

    @Override
    public void process(World worldIn, Entity entityIn) {
        if (!worldIn.isRemote()) {
            if (entityIn instanceof ServerPlayerEntity) {
                ServerPlayerEntity playerEntity = (ServerPlayerEntity) entityIn;
                float yaw = playerEntity.rotationYaw;
                float pitch = playerEntity.rotationPitch;

                if (position != null) {
                    int posX = position[0];
                    int posY = position[1];
                    int posZ = position[2];
                    if (worldIn == entityIn.world) {
                        Set<SPlayerPositionLookPacket.Flags> set = EnumSet.noneOf(SPlayerPositionLookPacket.Flags.class);
                        playerEntity.connection.setPlayerLocation(posX, posY, posZ, yaw, pitch, set);
                    } else {
                        playerEntity.teleport((ServerWorld) worldIn, posX, posY, posZ, yaw, pitch);
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

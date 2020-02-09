package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class PositionObject {

    private String dimension;
    private double[] blockPos;
    private String atEntity;

    private double[] facing;

    public String getDimension() {
        return dimension;
    }

    public double[] getBlockPos() {
        return blockPos;
    }

    public String getAtEntity() {
        return atEntity;
    }

    public double[] getFacing() {
        return facing;
    }

    public BlockPos getPos(World worldIn, BlockPos defaultPosIn) {
        AtomicReference<BlockPos> parsedBlockPos = new AtomicReference<>();
        parsedBlockPos.set(defaultPosIn.up());
        if (blockPos != null) {
            parsedBlockPos.set(new BlockPos(blockPos[0],
                    blockPos[1],
                    blockPos[2]));
        } else if (atEntity != null) {
            Entity entity;
            if (Pattern.compile("[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}").matcher(atEntity).matches()) {
                entity = ((ServerWorld) worldIn).getEntityByUuid(UUID.fromString(atEntity));
            } else {//if(Pattern.compile("").matcher(atEntity).matches()){
                entity = worldIn.getPlayers().stream().filter(o -> o.getDisplayName().getFormattedText().equals(atEntity)).findFirst().orElse(null);
            }

            if (entity != null)
                parsedBlockPos.set(entity.getPosition());
        }
        return parsedBlockPos.get();
    }
}

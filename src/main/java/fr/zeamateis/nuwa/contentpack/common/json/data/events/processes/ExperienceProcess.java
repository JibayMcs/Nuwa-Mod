package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IProcess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExperienceProcess implements IProcess {

    private AddObject add;
    private RemoveObject remove;

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        if (!worldIn.isRemote())
            if (entityIn instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entityIn;
                if (add != null) {
                    if (add.level != -1) {
                        playerEntity.addExperienceLevel(add.level);
                    }
                } else if (remove != null)
                    if (remove.level != -1) {
                        playerEntity.addExperienceLevel(-remove.level);

                    }
            }
    }

    @Override
    public String getRegistryName() {
        return "nuwa:experience_process";
    }

    static class AddObject {
        private int level = -1;

        public int getLevel() {
            return level;
        }
    }

    static class RemoveObject {
        private int level = -1;

        public int getLevel() {
            return level;
        }
    }
}

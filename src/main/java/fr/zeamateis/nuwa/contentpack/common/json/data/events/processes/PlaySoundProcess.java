package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IProcess;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class PlaySoundProcess implements IProcess {

    private String sound;
    private float volume = 1.0F;
    private float pitch;

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        if (!worldIn.isRemote())
            if (sound != null) {
                worldIn.playSound(null, posIn, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(sound)), SoundCategory.BLOCKS, volume, pitch);
            }
    }

    @Override
    public String getRegistryName() {
        return "nuwa:play_sound_process";
    }
}

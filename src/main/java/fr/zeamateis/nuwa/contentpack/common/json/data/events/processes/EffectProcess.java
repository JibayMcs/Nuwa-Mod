package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IProcess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class EffectProcess implements IProcess {

    private List<String> effects;
    private String effect;

    public List<String> getEffects() {
        return effects;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entityIn;
            if (effects != null) {
                effects.stream().forEach(effect -> {
                    Effect effectInstance = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effect)).getEffect();

                });
            }
        }
    }

    private void applyEffect(PlayerEntity playerEntityIn, EffectInstance effectInstanceIn) {
        playerEntityIn.addPotionEffect(effectInstanceIn);
    }

    @Override
    public String getRegistryName() {
        return "nuwa:effect_process";
    }
}

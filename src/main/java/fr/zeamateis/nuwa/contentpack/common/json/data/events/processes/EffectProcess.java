package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("applyEffect")
    private ApplyEffectObject applyEffectObject;

    @SerializedName("removeEffect")
    private EffectObject removeEffectObject;

    private boolean clearEffects;

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entityIn;
            if (applyEffectObject != null) {
                if (applyEffectObject.getEffects() != null) {
                    applyEffectObject.getEffects().forEach(effect -> {
                        Effect parsedEffect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(applyEffectObject.getEffect()));
                        if (parsedEffect != null) {
                            EffectInstance effectInstance = new EffectInstance(parsedEffect, applyEffectObject.getDuration(), applyEffectObject.getAmplifier());
                            applyEffect(playerEntity, effectInstance);
                        }
                    });
                } else if (applyEffectObject.getEffect() != null) {
                    Effect parsedEffect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(applyEffectObject.getEffect()));
                    if (parsedEffect != null) {
                        EffectInstance effectInstance = new EffectInstance(parsedEffect, applyEffectObject.getDuration(), applyEffectObject.getAmplifier());
                        applyEffect(playerEntity, effectInstance);
                    }
                }
            } else if (removeEffectObject != null) {
                if (removeEffectObject.getEffects() != null) {
                    removeEffectObject.getEffects().forEach(effect -> {
                        Effect parsedEffect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(removeEffectObject.getEffect()));
                        if (parsedEffect != null) {
                            removeEffect(playerEntity, parsedEffect);
                        }
                    });
                } else if (removeEffectObject.getEffect() != null) {
                    Effect parsedEffect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(removeEffectObject.getEffect()));
                    if (parsedEffect != null) {
                        removeEffect(playerEntity, parsedEffect);
                    }
                }
            } else if (clearEffects)
                playerEntity.clearActivePotions();
        }
    }

    private void applyEffect(PlayerEntity playerEntityIn, EffectInstance effectInstanceIn) {
        playerEntityIn.addPotionEffect(effectInstanceIn);
    }

    private void removeEffect(PlayerEntity playerEntityIn, Effect effectIn) {
        playerEntityIn.removePotionEffect(effectIn);
    }

    @Override
    public String getRegistryName() {
        return "nuwa:effect_process";
    }

    static class EffectObject {
        private List<String> effects;
        private String effect;

        public List<String> getEffects() {
            return effects;
        }

        public String getEffect() {
            return effect;
        }
    }

    static class ApplyEffectObject extends EffectObject {
        private int duration, amplifier;

        public int getDuration() {
            return duration;
        }

        public int getAmplifier() {
            return amplifier;
        }
    }
}

package fr.zeamateis.nuwa.contentpack.common.json.data.effects;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.base.ProcessEvent;
import net.minecraft.potion.EffectType;

public class EffectObject {

    private String name, registryName;
    private int duration;
    private int amplifier;
    private boolean ambient;
    private boolean showParticles = true;

    private EffectType effectType;
    private int liquidColor;
    private boolean isInstant;
    private ProcessEvent performEffect;

    public String getEffectName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public boolean isAmbient() {
        return ambient;
    }

    public boolean isShowParticles() {
        return showParticles;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public int getLiquidColor() {
        return liquidColor;
    }

    public boolean isInstant() {
        return isInstant;
    }

    public ProcessEvent getPerformEffect() {
        return performEffect;
    }

    public String getRegistryName() {
        return registryName;
    }
}

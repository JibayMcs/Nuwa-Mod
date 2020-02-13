package fr.zeamateis.nuwa.contentpack.common.json.data;

public class EffectObject {

    private String name;
    private int duration;
    private int amplifier;
    private boolean ambient;
    private boolean showParticles = true;

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
}

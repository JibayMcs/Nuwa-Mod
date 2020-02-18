package fr.zeamateis.nuwa.contentpack.common.minecraft.effect;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.base.ProcessEvent;
import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Json parsed {@link Effect}
 *
 * @author ZeAmateis
 */
//TODO Curative Items
public class JsonEffect extends Effect {
    private boolean isInstant;
    private ProcessEvent process;

    public JsonEffect(EffectType typeIn, int liquidColorIn, @Nonnull ResourceLocation registryNameIn, boolean isInstant) {
        super(typeIn, liquidColorIn);
        RegistryUtil.forceRegistryName(this, registryNameIn);
        this.isInstant = isInstant;
    }


    public JsonEffect(EffectType typeIn, int liquidColorIn, @Nonnull ResourceLocation registryNameIn) {
        this(typeIn, liquidColorIn, registryNameIn, false);
    }

    public ProcessEvent getProcess() {
        return process;
    }

    public void setProcess(ProcessEvent process) {
        this.process = process;
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);

        if (getProcess() != null) {
            getProcess().getProcesses().forEach(iProcess -> iProcess.process(entityLivingBaseIn.getEntityWorld(), entityLivingBaseIn.getPosition(), entityLivingBaseIn));
        }
    }

    /**
     * Returns true if the potion has an instant effect instead of a continuous one (eg Harming)
     */
    @Override
    public boolean isInstant() {
        return isInstant;
    }

    /**
     * checks if Potion effect is ready to be applied this tick.
     */
    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration >= 1;
    }

}

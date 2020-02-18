package fr.zeamateis.nuwa.contentpack.common.minecraft.potion;

import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Json parsed {@link Potion}
 *
 * @author ZeAmateis
 */
public class JsonPotion extends Potion {

    public JsonPotion(@Nullable String baseNameIn, @Nonnull ResourceLocation registryNameIn, EffectInstance... effectsIn) {
        super(baseNameIn, effectsIn);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

package fr.zeamateis.nuwa.contentpack.common.minecraft.potion;

import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Json parsed {@link Potion}
 *
 * @author ZeAmateis
 */
public class JsonPotion extends Potion {

    private final String baseName;

    public JsonPotion(@Nonnull String baseNameIn, @Nonnull ResourceLocation registryNameIn, EffectInstance... effectsIn) {
        super(baseNameIn, effectsIn);
        this.baseName = baseNameIn;
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }

    /**
     * Gets the name of this PotionType with a prefix (such as "Splash" or "Lingering") prepended
     */
    @Override
    public String getNamePrefixed(String prefix) {
        String originalPrefix = prefix.substring("item.minecraft.".length());
        return String.format("item.%s.%s%s", this.getRegistryName().getNamespace(), originalPrefix, this.baseName);
    }
}

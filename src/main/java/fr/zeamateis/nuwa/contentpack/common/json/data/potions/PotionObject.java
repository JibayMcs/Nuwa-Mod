package fr.zeamateis.nuwa.contentpack.common.json.data.potions;

import fr.zeamateis.nuwa.contentpack.common.json.data.effects.EffectObject;

import java.util.List;

/**
 * Reprensentation of Json {@link net.minecraft.potion.Potion} object
 *
 * @author ZeAmateis
 */
public class PotionObject {

    private String registryName, baseName;
    private List<EffectObject> effects;

    public String getRegistryName() {
        return registryName;
    }

    public String getBaseName() {
        return baseName;
    }

    public List<EffectObject> getEffects() {
        return effects;
    }
}

package fr.zeamateis.nuwa.contentpack.common.json.data.events;

import net.minecraft.util.ResourceLocation;

/**
 * Reprensentation of Json {@link api.contentpack.json.conditions.ICondition} object
 *
 * @author ZeAmateis
 */
public class ConditionObject {

    private String conditionClass;
    private String registryName;

    public ConditionObject(String conditionClass, String registryName) {
        this.conditionClass = conditionClass;
        this.registryName = registryName;
    }

    public String getConditionClass() {
        return conditionClass;
    }

    public ResourceLocation getRegistryName() {
        return new ResourceLocation(registryName);
    }
}

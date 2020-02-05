package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.PackManager;
import api.contentpack.data.IRegistryData;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.ConditionObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition.PlayerHeldItemCondition;
import fr.zeamateis.nuwa.contentpack.common.minecraft.registries.ConditionType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedList;

public class ConditionsData implements IRegistryData {

    private final LinkedList<ConditionType> conditionTypes;

    public ConditionsData() {
        this.conditionTypes = new LinkedList<>();
    }

    /**
     * Use {@link PackManager}
     * instance to parse datas from Content Pack zip file
     *
     * @param packManagerIn
     */
    @Override
    public void parseData(PackManager packManagerIn) {
        ConditionType playerHeldItem = new ConditionType(new ConditionObject(PlayerHeldItemCondition.class.getName(), "nuwa:player_held_item"));
        System.out.println(playerHeldItem.getConditionObject().getConditionClass());
        System.out.println(playerHeldItem.getConditionObject().getRegistryName());
        this.conditionTypes.add(playerHeldItem);
    }


    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<ConditionType> getObjectsList() {
        return this.conditionTypes;
    }
}

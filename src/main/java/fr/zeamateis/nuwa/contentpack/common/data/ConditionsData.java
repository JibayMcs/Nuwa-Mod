package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IData;
import api.contentpack.json.conditions.ICondition;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.ConditionObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.registries.ConditionType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.LinkedList;

/**
 * Data reading class for json representation of {@link ICondition} objects
 *
 * @author ZeAmateis
 */
public class ConditionsData implements IData {

    private final LinkedList<ConditionType> conditionTypes;

    public ConditionsData() {
        this.conditionTypes = new LinkedList<>();
    }

    /**
     * Use {@link PackManager}
     * instance to parse datas from Content Pack
     *
     * @param packManagerIn The {@link PackManager} instance
     * @param contentPackIn The {@link ContentPack} instance
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn) {
        ConditionType playerHeldItem = new ConditionType(new ConditionObject("fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition.PlayerHeldItemCondition", "nuwa:player_held_item"));
        this.conditionTypes.add(playerHeldItem);
    }


    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList} type of {@link IForgeRegistryEntry}
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<ConditionType> getObjectsList() {
        return this.conditionTypes;
    }
}

package fr.zeamateis.nuwa.contentpack.common.json.data.events.blocks;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.base.ProcessEvent;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Reprensentation of Json {@link ProcessEvent} objects
 *
 * @author ZeAmateis
 */
public class BlockEventObject {

    private String registryName;

    private List<EntityBlockEvent> entitiesCollideBlockEvents;
    private ProcessEvent playerDestroyBlockEvent;
    private ProcessEvent leftClickBlockEvent, rightClickBlockEvent;

    public String getRegistryName() {
        return registryName;
    }

    public void setRegistryName(ResourceLocation registryName) {
        this.registryName = registryName.toString();
    }

    public List<EntityBlockEvent> getEntitiesCollideBlockEvents() {
        return entitiesCollideBlockEvents;
    }

    public ProcessEvent getPlayerDestroyBlockEvent() {
        return playerDestroyBlockEvent;
    }

    public ProcessEvent getLeftClickBlockEvent() {
        return leftClickBlockEvent;
    }

    public ProcessEvent getRightClickBlockEvent() {
        return rightClickBlockEvent;
    }
}

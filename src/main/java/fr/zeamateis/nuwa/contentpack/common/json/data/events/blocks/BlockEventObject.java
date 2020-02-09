package fr.zeamateis.nuwa.contentpack.common.json.data.events.blocks;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.base.ProcessEvent;
import net.minecraft.util.ResourceLocation;

public class BlockEventObject {

    private String registryName;

    private EntityBlockEvent entityCollideBlockEvent;
    private ProcessEvent playerDestroyBlockEvent;
    private ProcessEvent leftClickBlockEvent, rightClickBlockEvent;

    public String getRegistryName() {
        return registryName;
    }

    public void setRegistryName(ResourceLocation registryName) {
        this.registryName = registryName.toString();
    }

    public EntityBlockEvent getEntityCollideBlockEvent() {
        return entityCollideBlockEvent;
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

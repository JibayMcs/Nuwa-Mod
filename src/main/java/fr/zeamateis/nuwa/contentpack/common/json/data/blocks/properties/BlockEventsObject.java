package fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.base.EntityBlockEvent;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.base.ProcessEvent;

public class BlockEventsObject {

    private EntityBlockEvent entityCollideBlockEvent;
    private ProcessEvent playerDestroyBlockEvent;
    private ProcessEvent leftClickBlockEvent, rightClickBlockEvent;

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

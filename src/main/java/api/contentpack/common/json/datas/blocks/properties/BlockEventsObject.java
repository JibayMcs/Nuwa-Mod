package api.contentpack.common.json.datas.blocks.properties;

import api.contentpack.common.json.datas.events.base.EntityBlockEvent;
import api.contentpack.common.json.datas.events.base.ProcessEvent;

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

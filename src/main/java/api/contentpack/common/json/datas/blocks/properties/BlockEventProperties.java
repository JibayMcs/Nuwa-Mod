package api.contentpack.common.json.datas.blocks.properties;

import api.contentpack.common.json.datas.blocks.events.EntityCollideEvent;

public class BlockEventProperties {

    private EntityCollideEvent entityCollideEvent;

    public BlockEventProperties(EntityCollideEvent entityCollideEvent) {
        this.entityCollideEvent = entityCollideEvent;
    }

    public EntityCollideEvent getEntityCollideEvent() {
        return entityCollideEvent;
    }

}

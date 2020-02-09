package fr.zeamateis.nuwa.contentpack.common.json.data.containers.type;

import fr.zeamateis.nuwa.contentpack.common.minecraft.containers.JsonChestContainer;
import net.minecraft.inventory.container.Container;

public enum Type {
    NULL(),
    CHEST(JsonChestContainer.class);

    private Class<? extends Container> containerType;

    Type() {
    }

    Type(Class<? extends Container> containerTypeIn) {
        this.containerType = containerTypeIn;
    }

    public static Type containerTypeOf(String valueIn) {
        try {
            return Type.valueOf(valueIn);
        } catch (IllegalArgumentException ex) {
            return NULL;
        }
    }

    public Class<? extends Container> getContainerType() {
        return containerType;
    }
}

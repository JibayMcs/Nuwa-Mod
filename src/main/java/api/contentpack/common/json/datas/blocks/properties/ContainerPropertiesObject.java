package api.contentpack.common.json.datas.blocks.properties;

import api.contentpack.common.json.datas.containers.ContainerObject;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.util.ResourceLocation;

public class ContainerPropertiesObject {

    private String container;
    private int totalSlots;

    public ContainerObject getContainer() {
        return NuwaMod.Registries.CONTAINER.getValue(new ResourceLocation(container)).getContainersObject();
    }

    public int getTotalSlots() {
        return totalSlots;
    }
}

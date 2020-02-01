package api.contentpack.common.json.datas.containers;

import java.util.ArrayList;
import java.util.List;

public class ContainersObject {

    private String registryName;

    private List<SlotObject> slots = new ArrayList<>();

    public List<SlotObject> getSlots() {
        return slots;
    }

    public String getRegistryName() {
        return registryName;
    }
}

package api.contentpack.client.itemGroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class VanillaItemGroups {

    private static Map<ResourceLocation, ItemGroup> itemGroupsMap = new HashMap<>();

    public static Map<ResourceLocation, ItemGroup> getItemGroupsMap() {
        return itemGroupsMap;
    }

    public static void addVanillaItemGroup(ResourceLocation registryNameIn, ItemGroup itemGroupIn) {
        itemGroupsMap.put(registryNameIn, itemGroupIn);
    }
}

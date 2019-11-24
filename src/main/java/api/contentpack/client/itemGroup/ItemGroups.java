package api.contentpack.client.itemGroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ItemGroups {

    private static Map<ResourceLocation, ItemGroup> itemGroupsMap = new HashMap<>();


    public static void putItemGroup(ResourceLocation registryNameIn, ItemGroup itemGroupIn) {
        itemGroupsMap.put(registryNameIn, itemGroupIn);
    }

    public static ItemGroup get(ResourceLocation registryNameIn) {
        return itemGroupsMap.get(registryNameIn);
    }

    public static boolean contains(ResourceLocation registryNameIn) {
        return itemGroupsMap.containsKey(registryNameIn);
    }

    public static void removeItemGroup(ResourceLocation registryNameIn) {
        itemGroupsMap.remove(registryNameIn);
    }
}

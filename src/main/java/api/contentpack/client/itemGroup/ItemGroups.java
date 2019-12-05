package api.contentpack.client.itemGroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ItemGroups {

    private static Map<ResourceLocation, ItemGroup> itemGroupsMap = new HashMap<>();


    /**
     * Register an itemgroup to the global {@link ItemGroups#itemGroupsMap}
     *
     * @param registryNameIn Registry name of item group
     * @param itemGroupIn    item group instance
     */
    public static void putItemGroup(ResourceLocation registryNameIn, ItemGroup itemGroupIn) {
        itemGroupsMap.put(registryNameIn, itemGroupIn);
    }


    /**
     * Get item group by his registry name
     *
     * @param registryNameIn
     * @return
     */
    public static ItemGroup get(ResourceLocation registryNameIn) {
        return itemGroupsMap.get(registryNameIn);
    }


    /**
     * Check if {@link ItemGroups#itemGroupsMap} contain item group key by his registry name
     *
     * @param registryNameIn
     * @return
     */
    public static boolean contains(ResourceLocation registryNameIn) {
        return itemGroupsMap.containsKey(registryNameIn);
    }

    /**
     * Remove item group from {@link ItemGroups#itemGroupsMap} by his registry name
     *
     * @param registryNameIn
     */
    public static void removeItemGroup(ResourceLocation registryNameIn) {
        itemGroupsMap.remove(registryNameIn);
    }
}

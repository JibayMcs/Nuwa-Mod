package fr.zeamateis.nuwa.contentpack.common.minecraft.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Field;

/**
 * Helper class for registries
 *
 * @author ZeAmateis
 */
public class RegistryUtil {

    private static Field regName;

    /**
     * Hackky method from {@link net.minecraftforge.registries.GameData#forceRegistryName}
     * to remove 'Potentially dangerous alternative [..]' line from logs
     *
     * @param entryIn        The entry to force registry
     * @param registryNameIn The future registry name of the entry
     */
    public static void forceRegistryName(IForgeRegistryEntry<?> entryIn, ResourceLocation registryNameIn) {
        if (regName == null) {
            try {
                regName = ForgeRegistryEntry.class.getDeclaredField("registryName");
                regName.setAccessible(true);
            } catch (NoSuchFieldException | SecurityException e) {
                throw new RuntimeException("Could not get `registryName` field from IForgeRegistryEntry.Impl", e);
            }
        }
        try {
            regName.set(entryIn, registryNameIn);
            //NuwaMod.getLogger().debug("{} has now registryName : {}", entryIn, registryNameIn);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(String.format("Could not set `registryName` field in IForgeRegistryEntry.Impl to `%s`", registryNameIn.toString()), e);
        }

    }
}

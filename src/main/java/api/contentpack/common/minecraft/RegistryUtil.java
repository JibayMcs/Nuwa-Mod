package api.contentpack.common.minecraft;

import fr.zeamateis.mjson.MJsonMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Field;

import static net.minecraftforge.registries.ForgeRegistry.REGISTRIES;

public class RegistryUtil {

    private static Field regName;

    /**
     * Hackky method from {@link net.minecraftforge.registries.GameData#forceRegistryName}
     * to remove 'Potentially dangerous alternative [..]' line from logs
     */
    public static void forceRegistryName(IForgeRegistryEntry<?> entry, ResourceLocation name) {
        if (regName == null) {
            try {
                regName = ForgeRegistryEntry.class.getDeclaredField("registryName");
                regName.setAccessible(true);
            } catch (NoSuchFieldException | SecurityException e) {
                MJsonMod.getLogger().error(REGISTRIES, "Could not get `registryName` field from IForgeRegistryEntry.Impl", e);
                throw new RuntimeException(e);
            }
        }
        try {
            regName.set(entry, name);
            MJsonMod.getLogger().debug("{} has now registryName : {}", entry, name);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            MJsonMod.getLogger().error(REGISTRIES, "Could not set `registryName` field in IForgeRegistryEntry.Impl to `{}`", name.toString(), e);
            throw new RuntimeException(e);
        }

    }
}

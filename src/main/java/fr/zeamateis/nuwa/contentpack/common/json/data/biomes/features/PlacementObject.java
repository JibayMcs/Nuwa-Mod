package fr.zeamateis.nuwa.contentpack.common.json.data.biomes.features;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Reprensentation of Json {@link Placement} object
 *
 * @author ZeAmateis
 */
public class PlacementObject {

    private String name;
    private PlacementConfig config = PlacementConfig.NO_PLACEMENT_CONFIG;

    private int count, extraCount;
    private float extraChance;

    public PlacementObject() {
        this.name = "minecraft:nope";
        this.config = PlacementConfig.NO_PLACEMENT_CONFIG;
    }

    public <C extends IPlacementConfig, F extends Placement<C>> F getPlacement() {
        return (F) ForgeRegistries.DECORATORS.getValue(new ResourceLocation(name));
    }

    public IPlacementConfig getPlacementConfig() {
        switch (config) {
            case COUNT_EXTRA_HEIGHTMAP:
                return new AtSurfaceWithExtraConfig(count, extraChance, extraCount);
            default:
                return IPlacementConfig.NO_PLACEMENT_CONFIG;
        }
    }

    public enum PlacementConfig {
        NO_PLACEMENT_CONFIG,
        COUNT_EXTRA_HEIGHTMAP

    }
}

package fr.zeamateis.nuwa.contentpack.common.json.data.biomes;

import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Reprensentation of Json {@link SurfaceBuilder} object
 *
 * @author ZeAmateis
 */
public class SurfaceObject {

    private EnumSurfaceBuilder surfaceBuilderType;

    private TerrainConfig terrainConfig;

    public EnumSurfaceBuilder getSurfaceBuilderType() {
        return surfaceBuilderType;
    }

    public TerrainConfig getTerrainConfig() {
        return terrainConfig;
    }

    public enum EnumSurfaceBuilder {
        DEFAULT(SurfaceBuilder.DEFAULT),
        MOUNTAIN(SurfaceBuilder.MOUNTAIN),
        SHATTERED_SAVANNA(SurfaceBuilder.SHATTERED_SAVANNA),
        GRAVELLY_MOUNTAIN(SurfaceBuilder.GRAVELLY_MOUNTAIN),
        GIANT_TREE_TAIGA(SurfaceBuilder.GIANT_TREE_TAIGA),
        SWAMP(SurfaceBuilder.SWAMP),
        BADLANDS(SurfaceBuilder.BADLANDS),
        WOODED_BADLANDS(SurfaceBuilder.WOODED_BADLANDS),
        ERODED_BADLANDS(SurfaceBuilder.ERODED_BADLANDS),
        FROZEN_OCEAN(SurfaceBuilder.FROZEN_OCEAN),
        NETHER(SurfaceBuilder.NETHER),
        NOPE(SurfaceBuilder.NOPE);

        SurfaceBuilder surfaceBuilder;

        EnumSurfaceBuilder(SurfaceBuilder surfaceBuilder) {
            this.surfaceBuilder = surfaceBuilder;
        }

        public SurfaceBuilder getSurfaceBuilder() {
            return surfaceBuilder;
        }
    }

    public static class TerrainConfig {
        private String topBlock, undergroundBlock, underWaterBlock;

        public String getTopBlock() {
            return topBlock;
        }

        public String getUndergroundBlock() {
            return undergroundBlock;
        }

        public String getUnderWaterBlock() {
            return underWaterBlock;
        }

        public SurfaceBuilderConfig getConfig() {
            BlockState topMaterial = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(topBlock)).getDefaultState();
            BlockState underMaterial = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(undergroundBlock)).getDefaultState();
            BlockState underWaterMaterial = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(underWaterBlock)).getDefaultState();
            return new SurfaceBuilderConfig(topMaterial, underMaterial, underWaterMaterial);
        }
    }
}

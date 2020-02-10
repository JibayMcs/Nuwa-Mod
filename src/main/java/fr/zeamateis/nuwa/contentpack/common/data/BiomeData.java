package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.NuwaMod;
import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.BiomeObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.biome.JsonBiome;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.zip.ZipFile;

public class BiomeData implements IPackData {

    private final LinkedList<Biome> biomes;

    public BiomeData() {
        this.biomes = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return String
     */
    @Override
    public String getEntryFolder() {
        return "objects/biomes/";
    }

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param packManagerIn
     * @param contentPackIn
     * @param zipFileIn
     * @param readerIn
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        BiomeObject biomeObject = packManagerIn.getGson().fromJson(readerIn, BiomeObject.class);

        Biome.Builder builder = new Biome.Builder()
                .surfaceBuilder(new LoggingConfiguredSurfaceBuilder<>(biomeObject.getSurface().getSurfaceBuilderType().getSurfaceBuilder(), biomeObject.getSurface().getTerrainConfig().getConfig()))
                .category(biomeObject.getCategory() != null ? biomeObject.getCategory() : Biome.Category.NONE)
                .depth(biomeObject.getDepth())
                .scale(biomeObject.getScale())
                .precipitation(biomeObject.getClimat().getRainType())
                .temperature(biomeObject.getClimat().getTemperature())
                .downfall(biomeObject.getClimat().getDownfall())
                .waterColor(biomeObject.getBiomeColor().getWaterColor())
                .waterFogColor(biomeObject.getBiomeColor().getWaterFogColor())
                .parent(null);

        JsonBiome parsedBiome = new JsonBiome(builder, biomeObject.getRegistryName(), biomeObject.getStructures(), biomeObject.getSpawns(), biomeObject.getCarvers(), biomeObject.getFeatures());

        parsedBiome.setBiomeType(biomeObject.getBiomeType());
        parsedBiome.setBiomeDictionnaryTypes(biomeObject.getBiomeDictionaryTypes());
        parsedBiome.setWeight(biomeObject.getWeight());
        parsedBiome.setGrassColor(biomeObject.getBiomeColor().getGrassColor());
        parsedBiome.setFoliageColor(biomeObject.getBiomeColor().getFoliageColor());

        this.biomes.add(parsedBiome);
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<Biome> getObjectsList() {
        return this.biomes;
    }

    private static class LoggingConfiguredSurfaceBuilder<C extends ISurfaceBuilderConfig> extends ConfiguredSurfaceBuilder<C> {

        private LoggingConfiguredSurfaceBuilder(final SurfaceBuilder<C> surfaceBuilder, final C config) {
            super(surfaceBuilder, config);
        }

        @Override
        public void buildSurface(
                final Random random, final IChunk chunk, final Biome biome,
                final int x, final int z, final int startHeight, final double noise,
                final BlockState defaultBlock, final BlockState defaultFluid,
                final int seaLevel, final long seed
        ) {
            super.buildSurface(random, chunk, biome, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed);
            final ChunkPos chunkPos = chunk.getPos();
            NuwaMod.getPackManager().getLogger().info("Generating {} at {},{}", biome.getRegistryName(), chunkPos.getXStart(), chunkPos.getZStart());
        }
    }
}

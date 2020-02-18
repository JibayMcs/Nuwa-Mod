package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.NuwaMod;
import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.BiomeObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.biome.JsonBiome;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.zip.ZipFile;

/**
 * Data reading class for json representation of {@link Biome} objects
 *
 * @author ZeAmateis
 */
public class BiomeData implements IPackData {

    private final LinkedList<Biome> biomes;

    public BiomeData() {
        this.biomes = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return the full entry folder path
     */
    @Override
    public String getEntryFolder() {
        return "objects/biomes/";
    }

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack
     *
     * @param packManagerIn The {@link PackManager} instance
     * @param contentPackIn The {@link ContentPack} instance
     * @param readerIn      The {@link InputStreamReader} instance
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, InputStreamReader readerIn) {
        BiomeObject biomeObject = packManagerIn.getGson().fromJson(readerIn, BiomeObject.class);

        ResourceLocation registryName = new ResourceLocation(contentPackIn.getNamespace(), biomeObject.getRegistryName());

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

        JsonBiome parsedBiome;

        if (biomeObject.getPreconfiguredFeatures() != null)
            parsedBiome = new JsonBiome(builder, registryName, biomeObject.getStructures(), biomeObject.getSpawns(), biomeObject.getCarvers(), biomeObject.getFeatures(), biomeObject.getPreconfiguredFeatures());
        else
            parsedBiome = new JsonBiome(builder, registryName, biomeObject.getStructures(), biomeObject.getSpawns(), biomeObject.getCarvers(), biomeObject.getFeatures());

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
     * @return {@link LinkedList} type of {@link IForgeRegistryEntry}
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<Biome> getObjectsList() {
        return this.biomes;
    }


    /**
     * Surface Builder who's logging position of the custom generating biome
     *
     * @param <C> extends {@link ISurfaceBuilderConfig}
     */
    private static class LoggingConfiguredSurfaceBuilder<C extends ISurfaceBuilderConfig> extends ConfiguredSurfaceBuilder<C> {

        private LoggingConfiguredSurfaceBuilder(final SurfaceBuilder<C> surfaceBuilder, final C config) {
            super(surfaceBuilder, config);
        }

        @Override
        public void buildSurface(
                final Random random, final IChunk chunk, final Biome biome,
                final int x, final int z, final int startHeight, final double noise,
                final BlockState defaultBlock, final BlockState defaultFluid,
                final int seaLevel, final long seed) {

            super.buildSurface(random, chunk, biome, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed);
            final ChunkPos chunkPos = chunk.getPos();
            NuwaMod.getPackManager().getLogger().debug("Generating {} at {},{}", biome.getRegistryName(), chunkPos.getXStart(), chunkPos.getZStart());
        }
    }
}

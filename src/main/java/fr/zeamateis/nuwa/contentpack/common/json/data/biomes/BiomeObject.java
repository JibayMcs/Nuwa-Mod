package fr.zeamateis.nuwa.contentpack.common.json.data.biomes;

import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.features.FeatureObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.features.structures.StructureObject;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

import java.util.List;

/**
 * Reprensentation of Json {@link Biome} object
 *
 * @author ZeAmateis
 */
public class BiomeObject {

    private String registryName;

    private SurfaceObject surface;
    private Biome.Category category;
    private BiomeManager.BiomeType biomeType;
    private List<BiomeDictionaryType> biomeDictionaryTypes;
    private ClimatObject climat = new ClimatObject();
    private BiomeColorObject biomeColor;
    private float depth;
    private float scale;
    private int weight;
    private List<StructureObject> structures;
    private List<SpawnObject> spawns;
    private List<CarverObject> carvers;
    private List<FeatureObject> features;
    private List<PreconfiguredFeaturesObject> preconfiguredFeatures;

    public String getRegistryName() {
        return registryName;
    }

    public SurfaceObject getSurface() {
        return surface;
    }

    public Biome.Category getCategory() {
        return category;
    }

    public BiomeManager.BiomeType getBiomeType() {
        return biomeType;
    }

    public List<BiomeDictionaryType> getBiomeDictionaryTypes() {
        return biomeDictionaryTypes;
    }

    public ClimatObject getClimat() {
        return climat;
    }

    public float getDepth() {
        return depth;
    }

    public float getScale() {
        return scale;
    }

    public int getWeight() {
        return weight;
    }

    public List<StructureObject> getStructures() {
        return structures;
    }

    public List<SpawnObject> getSpawns() {
        return spawns;
    }

    public BiomeColorObject getBiomeColor() {
        return biomeColor;
    }

    public List<CarverObject> getCarvers() {
        return carvers;
    }

    public List<FeatureObject> getFeatures() {
        return features;
    }

    public List<PreconfiguredFeaturesObject> getPreconfiguredFeatures() {
        return preconfiguredFeatures;
    }

    public enum BiomeDictionaryType {
        HOT(BiomeDictionary.Type.HOT),
        COLD(BiomeDictionary.Type.COLD),
        SPARSE(BiomeDictionary.Type.SPARSE),
        DENSE(BiomeDictionary.Type.DENSE),
        WET(BiomeDictionary.Type.WET),
        DRY(BiomeDictionary.Type.DRY),
        SAVANNA(BiomeDictionary.Type.SAVANNA),
        CONIFEROUS(BiomeDictionary.Type.CONIFEROUS),
        JUNGLE(BiomeDictionary.Type.JUNGLE),
        SPOOKY(BiomeDictionary.Type.SPOOKY),
        DEAD(BiomeDictionary.Type.DEAD),
        LUSH(BiomeDictionary.Type.LUSH),
        NETHER(BiomeDictionary.Type.NETHER),
        END(BiomeDictionary.Type.END),
        MUSHROOM(BiomeDictionary.Type.MUSHROOM),
        MAGICAL(BiomeDictionary.Type.MAGICAL),
        RARE(BiomeDictionary.Type.RARE),
        OCEAN(BiomeDictionary.Type.OCEAN),
        RIVER(BiomeDictionary.Type.RIVER),
        WATER(BiomeDictionary.Type.WATER),
        MESA(BiomeDictionary.Type.MESA),
        FOREST(BiomeDictionary.Type.FOREST),
        PLAINS(BiomeDictionary.Type.PLAINS),
        MOUNTAIN(BiomeDictionary.Type.MOUNTAIN),
        HILLS(BiomeDictionary.Type.HILLS),
        SWAMP(BiomeDictionary.Type.SWAMP),
        SANDY(BiomeDictionary.Type.SANDY),
        SNOWY(BiomeDictionary.Type.SNOWY),
        WASTELAND(BiomeDictionary.Type.WASTELAND),
        BEACH(BiomeDictionary.Type.BEACH),
        VOID(BiomeDictionary.Type.VOID);

        BiomeDictionary.Type biomeDictionaryType;

        BiomeDictionaryType(BiomeDictionary.Type biomeDictionaryType) {
            this.biomeDictionaryType = biomeDictionaryType;
        }

        public BiomeDictionary.Type getBiomeDictionaryType() {
            return biomeDictionaryType;
        }
    }
}

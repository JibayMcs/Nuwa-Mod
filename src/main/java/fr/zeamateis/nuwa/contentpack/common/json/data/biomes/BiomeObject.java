package fr.zeamateis.nuwa.contentpack.common.json.data.biomes;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

import java.util.List;

public class BiomeObject {

    private String registryName;

    private SurfaceObject surface;
    private Biome.Category category;
    private BiomeManager.BiomeType biomeType;
    private List<BiomeDictionaryType> biomeDictionnaryTypes;
    private ClimatPropertiesObject climatProperties = new ClimatPropertiesObject();
    private float depth;
    private float scale;
    private int waterColor, waterFogColor;
    private int weight;
    private int grassColor, foliageColor;
    private List<StructureObject> structures;
    private List<SpawnPropertiesObject> spawns;

    public ResourceLocation getRegistryName() {
        return new ResourceLocation(registryName);
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

    public List<BiomeDictionaryType> getBiomeDictionnaryTypes() {
        return biomeDictionnaryTypes;
    }

    public ClimatPropertiesObject getClimatProperties() {
        return climatProperties;
    }

    public float getDepth() {
        return depth;
    }

    public float getScale() {
        return scale;
    }

    public int getWaterColor() {
        return waterColor;
    }

    public int getWaterFogColor() {
        return waterFogColor;
    }

    public int getWeight() {
        return weight;
    }

    public int getGrassColor() {
        return grassColor;
    }

    public int getFoliageColor() {
        return foliageColor;
    }

    public List<StructureObject> getStructures() {
        return structures;
    }

    public List<SpawnPropertiesObject> getSpawns() {
        return spawns;
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

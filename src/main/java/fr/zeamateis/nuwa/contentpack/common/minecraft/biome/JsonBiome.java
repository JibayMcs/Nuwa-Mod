package fr.zeamateis.nuwa.contentpack.common.minecraft.biome;

import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.BiomeObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.SpawnPropertiesObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.StructureObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeManager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class JsonBiome extends Biome {

    private BiomeManager.BiomeType biomeType;
    private List<BiomeObject.BiomeDictionaryType> biomeDictionnaryTypes;
    private int weight;
    private Integer grassColor, foliageColor;

    public JsonBiome(Builder biomeBuilder, @Nonnull ResourceLocation registryNameIn, List<StructureObject> structures, List<SpawnPropertiesObject> spawns) {
        super(biomeBuilder);
        RegistryUtil.forceRegistryName(this, registryNameIn);

        if (structures != null)
            structures.stream()
                    .filter(Objects::nonNull)
                    .forEach(structureObject -> this.addStructure(structureObject.getStructure(), IFeatureConfig.NO_FEATURE_CONFIG));

        if (spawns != null)
            spawns.stream()
                    .filter(Objects::nonNull)
                    .forEach(spawn -> this.addSpawn(spawn.getEntityClassification(), spawn.getSpawnListEntry()));

        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addStructures(this);
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addDoubleFlowers(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addSedimentDisks(this);
        DefaultBiomeFeatures.addForestTrees(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addGrass(this);
        DefaultBiomeFeatures.addMushrooms(this);
        DefaultBiomeFeatures.addReedsAndPumpkins(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFreezeTopLayer(this);
    }

    public BiomeManager.BiomeType getBiomeType() {
        return biomeType;
    }

    public void setBiomeType(BiomeManager.BiomeType biomeType) {
        this.biomeType = biomeType;
    }

    public List<BiomeObject.BiomeDictionaryType> getBiomeDictionnaryTypes() {
        return biomeDictionnaryTypes;
    }

    public void setBiomeDictionnaryTypes(List<BiomeObject.BiomeDictionaryType> biomeDictionnaryTypes) {
        this.biomeDictionnaryTypes = biomeDictionnaryTypes;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Integer getGrassColor() {
        return grassColor;
    }

    public void setGrassColor(Integer grassColor) {
        this.grassColor = grassColor;
    }

    public Integer getFoliageColor() {
        return foliageColor;
    }

    public void setFoliageColor(Integer foliageColor) {
        this.foliageColor = foliageColor;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getGrassColor(BlockPos pos) {
        return getGrassColor() != null ? getGrassColor() : super.getGrassColor(pos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getFoliageColor(BlockPos pos) {
        return getFoliageColor() != null ? getFoliageColor() : super.getFoliageColor(pos);
    }
}

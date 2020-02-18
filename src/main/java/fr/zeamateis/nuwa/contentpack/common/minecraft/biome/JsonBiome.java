package fr.zeamateis.nuwa.contentpack.common.minecraft.biome;

import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.BiomeObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.CarverObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.PreconfiguredFeaturesObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.SpawnObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.features.FeatureObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.features.structures.StructureObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeManager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

/**
 * Json parsed {@link Biome}
 *
 * @author ZeAmateis
 */
public class JsonBiome extends Biome {

    private BiomeManager.BiomeType biomeType;
    private List<BiomeObject.BiomeDictionaryType> biomeDictionnaryTypes;
    private int weight;
    private Integer grassColor = null, foliageColor = null;

    public JsonBiome(Builder biomeBuilder, @Nonnull ResourceLocation registryNameIn,
                     List<StructureObject> structures,
                     List<SpawnObject> spawns,
                     List<CarverObject> carvers,
                     List<FeatureObject> features,
                     List<PreconfiguredFeaturesObject> preconfiguredFeatures) {
        super(biomeBuilder);
        RegistryUtil.forceRegistryName(this, registryNameIn);

        if (preconfiguredFeatures != null) preconfiguredFeatures.forEach(f -> f.getFeature(this));
    }

    public JsonBiome(Builder biomeBuilder, @Nonnull ResourceLocation registryNameIn,
                     List<StructureObject> structures,
                     List<SpawnObject> spawns,
                     List<CarverObject> carvers,
                     List<FeatureObject> features) {
        this(biomeBuilder, registryNameIn, structures, spawns, carvers, features, null);

        if (structures != null) structures.stream()
                .filter(Objects::nonNull)
                .forEach(structureObject -> {
                    this.addStructure(structureObject.getFeature(), structureObject.getFeatureConfig());
                });

        if (spawns != null) spawns.stream()
                .filter(Objects::nonNull)
                .forEach(spawn -> this.addSpawn(spawn.getEntityClassification(), spawn.getSpawnListEntry()));

        if (carvers != null) carvers.stream()
                .filter(Objects::nonNull)
                .forEach(carver -> this.addCarver(carver.getType(), Biome.createCarver(carver.getCarver(), new ProbabilityConfig(carver.getProbability()))));

        if (features != null) features.stream()
                .filter(Objects::nonNull)
                .forEach(feature -> this.addFeature(feature.getDecoration(), feature.getFeature()));

      /*  DefaultBiomeFeatures.addCarvers(this);
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
        DefaultBiomeFeatures.addFreezeTopLayer(this);*/
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
        if (getGrassColor() != null)
            return getGrassColor();
        else {
            double d0 = (double) MathHelper.clamp(this.func_225486_c(pos), 0.0F, 1.0F);
            double d1 = (double) MathHelper.clamp(this.getDownfall(), 0.0F, 1.0F);
            return GrassColors.get(d0, d1);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getFoliageColor(BlockPos pos) {
        if (getFoliageColor() != null) {
            return getFoliageColor();
        } else {
            double d0 = (double) MathHelper.clamp(this.func_225486_c(pos), 0.0F, 1.0F);
            double d1 = (double) MathHelper.clamp(this.getDownfall(), 0.0F, 1.0F);
            return FoliageColors.get(d0, d1);
        }
    }
}

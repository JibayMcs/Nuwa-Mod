package fr.zeamateis.nuwa.contentpack.common.json.data.biomes.features.structures;

import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.features.MultipleRandomFeatureObject;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraftforge.registries.ForgeRegistries;

public class StructureObject {

    private String name;
    private FeatureConfig config = FeatureConfig.NO_FEATURE;

    private float probability, largeProbability, clusterProbability;
    private boolean isBeached;

    //Village Config
    private String startPool;
    private int size;

    //Multiple Random Config
    private MultipleRandomFeatureObject multipleRandomFeature;

    private MineshaftStructure.Type mineshaftType;
    private OceanRuinStructure.Type oceanRuinType;

    public <C extends IFeatureConfig, F extends Feature<C>> F getFeature() {
        return (F) ForgeRegistries.FEATURES.getValue(new ResourceLocation(name));
    }

    public IFeatureConfig getFeatureConfig() {
        switch (config) {
            case BURIED_TREASURE:
                return new BuriedTreasureConfig(probability);
            case MINESHAFT:
                return new MineshaftConfig(probability, mineshaftType);
            case OCEAN_RUIN:
                return new OceanRuinConfig(oceanRuinType, largeProbability, clusterProbability);
            case PILLAGER_OUTPOST:
                return new PillagerOutpostConfig(probability);
            case SHIPWRECK:
                return new ShipwreckConfig(isBeached);
            case VILLAGE:
                return new VillageConfig(startPool, size);
            case MULTIPLE_RANDOM:
                return new MultipleRandomFeatureConfig(
                        multipleRandomFeature.getFeatures().stream().map(StructureObject::getFeature).toArray(Feature[]::new),
                        multipleRandomFeature.getFeatures().stream().map(StructureObject::getFeatureConfig).toArray(IFeatureConfig[]::new),
                        new float[]{0.2F, 0.1F},
                        Feature.NORMAL_TREE,
                        IFeatureConfig.NO_FEATURE_CONFIG);
            default:
                return IFeatureConfig.NO_FEATURE_CONFIG;
        }
    }

    public enum FeatureConfig {
        NO_FEATURE,
        BURIED_TREASURE,
        MINESHAFT,
        OCEAN_RUIN,
        PILLAGER_OUTPOST,
        SHIPWRECK,
        VILLAGE,
        MULTIPLE_RANDOM
    }
}

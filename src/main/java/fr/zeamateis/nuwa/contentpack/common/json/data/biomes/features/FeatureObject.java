package fr.zeamateis.nuwa.contentpack.common.json.data.biomes.features;

import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.features.structures.StructureObject;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class FeatureObject {

    private GenerationStage.Decoration decoration;
    private StructureObject feature;
    private PlacementObject placement = new PlacementObject();

    public GenerationStage.Decoration getDecoration() {
        return decoration;
    }

    public ConfiguredFeature getFeature() {
        return Biome.createDecoratedFeature(feature.getFeature(), feature.getFeatureConfig(), placement.getPlacement(), placement.getPlacementConfig());
    }
}

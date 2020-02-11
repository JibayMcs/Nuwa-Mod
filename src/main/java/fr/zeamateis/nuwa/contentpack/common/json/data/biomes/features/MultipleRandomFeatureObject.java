package fr.zeamateis.nuwa.contentpack.common.json.data.biomes.features;

import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.features.structures.StructureObject;

import java.util.List;

public class MultipleRandomFeatureObject {
    private List<StructureObject> features;
    private StructureObject featureProperties = new StructureObject();

    public List<StructureObject> getFeatures() {
        return features;
    }

    public StructureObject getFeatureProperties() {
        return featureProperties;
    }
}

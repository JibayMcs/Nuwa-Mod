package fr.zeamateis.nuwa.contentpack.common.json.data.biomes;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

public class StructureObject {

    private String structure;

    public <C extends IFeatureConfig, F extends Feature<C>> F getStructure() {
        return (F) ForgeRegistries.FEATURES.getValue(new ResourceLocation(structure));
    }
}

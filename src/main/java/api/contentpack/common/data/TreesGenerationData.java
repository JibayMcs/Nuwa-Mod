package api.contentpack.common.data;

import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.generations.treeGeneration.TestTreeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class TreesGenerationData implements IPackData {
    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return String
     */
    @Override
    public String getEntryFolder() {
        return "objects/generations/trees.json";
    }

    /**
     * Use {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param contentPackIn
     * @param zipFileIn
     * @param readerIn
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        final Feature testTreeFeature = new TestTreeFeature(NoFeatureConfig::deserialize, false);
        testTreeFeature.setRegistryName("mff:test_tree");
        ForgeRegistries.FEATURES.register(testTreeFeature);
        for (final Biome biome : ForgeRegistries.BIOMES) {
            biome.addFeature(
                    GenerationStage.Decoration.VEGETAL_DECORATION,
                    Biome.createDecoratedFeature(
                            testTreeFeature,
                            IFeatureConfig.NO_FEATURE_CONFIG,
                            Placement.COUNT_EXTRA_HEIGHTMAP,
                            new AtSurfaceWithExtraConfig(10, 10, 1))
            );
        }

    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<? extends IForgeRegistryEntry> getObjectsList() {
        return null;
    }

    /**
     * Link {@link IPackData#getObjectsList()} to the correct Forge Registry
     *
     * @return IForgeRegistry
     * @see ForgeRegistries
     */
    @Override
    public IForgeRegistry getObjectsRegistry() {
        return null;
    }
}

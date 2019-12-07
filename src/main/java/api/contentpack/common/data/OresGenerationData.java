package api.contentpack.common.data;

import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.oresGeneration.OresGenerationObject;
import com.google.common.reflect.TypeToken;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipFile;

public class OresGenerationData implements IPackData {
    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return String
     */
    @Override
    public String getEntryName() {
        return "objects/oresgeneration.json";
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
    public void parseData(ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        Type oresGenerationType = new TypeToken<List<OresGenerationObject>>() {
        }.getType();
        List<OresGenerationObject> generationsList = PackManager.GSON.fromJson(readerIn, oresGenerationType);

        if (oresGenerationType != null && generationsList != null) {
            generationsList.forEach(generationObject -> {
                Block blockToGen = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(generationObject.getOreBlock()));

                for (final Biome biome : ForgeRegistries.BIOMES) {

                    switch (generationObject.getDimensionType()) {
                        case OVERWORLD:
                            biome.addFeature(
                                    GenerationStage.Decoration.UNDERGROUND_ORES,
                                    Biome.createDecoratedFeature(Feature.ORE,
                                            new OreFeatureConfig(
                                                    OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                                    blockToGen.getDefaultState(),
                                                    generationObject.getVeinObject().getVeinSize()),
                                            Placement.COUNT_RANGE,
                                            generationObject.getVeinObject().getRangeConfig()
                                    )
                            );
                            break;
                        case NETHER:
                            biome.addFeature(
                                    GenerationStage.Decoration.UNDERGROUND_ORES,
                                    Biome.createDecoratedFeature(Feature.ORE,
                                            new OreFeatureConfig(
                                                    OreFeatureConfig.FillerBlockType.NETHERRACK,
                                                    blockToGen.getDefaultState(),
                                                    generationObject.getVeinObject().getVeinSize()),
                                            Placement.COUNT_RANGE,
                                            generationObject.getVeinObject().getRangeConfig()
                                    )
                            );
                            break;
                        case END:
                            biome.addFeature(
                                    GenerationStage.Decoration.UNDERGROUND_DECORATION,
                                    Biome.createDecoratedFeature(Feature.EMERALD_ORE,
                                            new ReplaceBlockConfig(
                                                    Blocks.END_STONE.getDefaultState(),
                                                    blockToGen.getDefaultState()
                                            ),
                                            Placement.COUNT_RANGE,
                                            generationObject.getVeinObject().getRangeConfig()
                                    )
                            );
                            break;
                    }
                }
            });
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

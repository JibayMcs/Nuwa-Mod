package api.contentpack.common.data;

import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.generations.oresGeneration.OresGenerationObject;
import com.google.common.reflect.TypeToken;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
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
    public String getEntryFolder() {
        return "objects/generations/ores/";
    }

    /**
     * Use {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param contentPackIn
     * @param zipFileIn
     * @param readerIn
     */
    //TODO Check if it's a correct check of nonnull/empty and list contain object from other.
    @Override
    public void parseData(ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        Type oresGenerationType = new TypeToken<List<OresGenerationObject>>() {
        }.getType();
        List<OresGenerationObject> generationsList = PackManager.GSON.fromJson(readerIn, oresGenerationType);

        if (oresGenerationType != null && generationsList != null) {
            generationsList.forEach(generationObject -> {
                if (generationObject.getOreBlock() != null) {
                    Block blockToGen = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(generationObject.getOreBlock()));
                    if (generationObject.getGenInBiomes() != null && !generationObject.getGenInBiomes().isEmpty()) {
                        for (String biomeToGen : generationObject.getGenInBiomes()) {
                            if (ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeToGen)) == null) {
                                NuwaMod.getLogger().warn("Biome \"{}\" doesn't exist or was not registered in the BiomeDictionnary, Ore \"{}\" was not generated in the world.", biomeToGen, blockToGen);
                            } else {
                                for (final Biome biome : ForgeRegistries.BIOMES) {
                                    if (biomeToGen.equals(biome.getRegistryName().toString())) {
                                        if (BiomeDictionary.hasAnyType(ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeToGen)))) {
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
                                    }
                                }
                            }
                        }
                    } else {
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

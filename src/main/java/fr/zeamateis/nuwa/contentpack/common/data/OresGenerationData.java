package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.contentpack.common.json.data.generations.oresGeneration.OresGenerationObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.JsonOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class OresGenerationData implements IPackData {

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return the full entry folder path
     */
    @Override
    public String getEntryFolder() {
        return "objects/generations/ores/";
    }

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param packManagerIn The {@link PackManager} instance
     * @param contentPackIn The {@link ContentPack} instance
     * @param zipFileIn     The {@link ZipFile} instance
     * @param readerIn      The {@link InputStreamReader} instance
     */
    //TODO Check if it's a correct check of nonnull/empty and list contain object from other.
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        OresGenerationObject generationObject = packManagerIn.getGson().fromJson(readerIn, OresGenerationObject.class);

        if (generationObject.getOreBlock() != null) {
            Block blockToGen = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(generationObject.getOreBlock()));
            if (blockToGen instanceof JsonOreBlock) {
                if (generationObject.getGenInBiomes() != null && !generationObject.getGenInBiomes().isEmpty()) {
                    for (String biomeToGen : generationObject.getGenInBiomes()) {
                        if (ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeToGen)) == null) {
                            packManagerIn.getLogger().warn("Biome \"{}\" doesn't exist or was not registered in the BiomeDictionnary, Ore \"{}\" was not generated in the world.", biomeToGen, blockToGen);
                        } else if (DimensionType.byName(new ResourceLocation(generationObject.getDimension())) != null) {
                            packManagerIn.getLogger().warn("Dimension \"{}\" doesn't exist or was not registered in the DimensionManager, Ore \"{}\" was not generated in the world.", generationObject.getDimension(), blockToGen);
                        } else {
                            for (final Biome biome : ForgeRegistries.BIOMES) {
                                if (biomeToGen.equals(biome.getRegistryName().toString())) {
                                    if (BiomeDictionary.hasAnyType(ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeToGen)))) {
                                        switch (generationObject.getDimension()) {
                                            case "minecraft:overworld":
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
                                            case "minecraft:the_nether":
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
                                            case "minecraft:the_end":
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
                        switch (generationObject.getDimension()) {
                            case "overworld":
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
                            case "minecraft:the_nether":
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
                            case "minecraft:the_end":
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

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList} type of {@link IForgeRegistryEntry}
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    @Override
    public LinkedList<? extends IForgeRegistryEntry> getObjectsList() {
        return null;
    }

}

package fr.zeamateis.nuwa.contentpack.common.json.data.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;

/**
 * Reprensentation of Json {@link DefaultBiomeFeatures} preconfigurations object
 *
 * @author ZeAmateis
 */
public enum PreconfiguredFeaturesObject {
    CARVERS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addCarvers(biomeIn);
        }
    },
    OCEAN_CARVERS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addOceanCarvers(biomeIn);
        }
    },
    STRUCTURES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addStructures(biomeIn);
        }
    },
    LAKES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addLakes(biomeIn);
        }
    },
    DESERT_LAKES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addDesertLakes(biomeIn);
        }
    },
    MONSTER_ROOMS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addMonsterRooms(biomeIn);
        }
    },
    STONE_VARIANTS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addStoneVariants(biomeIn);
        }
    },
    ORES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addOres(biomeIn);
        }
    },
    EXTRA_GOLD_ORE {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addExtraGoldOre(biomeIn);
        }
    },
    EXTRA_EMERALD_ORE {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addExtraEmeraldOre(biomeIn);
        }
    },
    INFESTED_STONE {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addInfestedStone(biomeIn);
        }
    },
    SWAMP_CLAY_DISKS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addSwampClayDisks(biomeIn);
        }
    },
    TAIGA_ROCKS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addTaigaRocks(biomeIn);
        }
    },
    TAIGA_LARGE_FERNS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addTaigaLargeFerns(biomeIn);
        }
    },
    SPARSE_BERRY_BUSHES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addSparseBerryBushes(biomeIn);
        }
    },
    BERRY_BUSHES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addBerryBushes(biomeIn);
        }
    },
    BAMBOO {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addBamboo(biomeIn);
        }
    },
    BAMBOO_JUNGLE_VEGETATION {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addBambooJungleVegetation(biomeIn);
        }
    },
    TAIGA_CONIFERS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addTaigaConifers(biomeIn);
        }
    },
    BIRCH_TREES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addBirchTrees(biomeIn);
        }
    },
    FOREST_TREES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addForestTrees(biomeIn);
        }
    },
    SAVANNA_TREES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addSavannaTrees(biomeIn);
        }
    },
    SHATTERED_SAVANNA_TREES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addShatteredSavannaTrees(biomeIn);
        }
    },
    JUNGLE_GRASS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addJungleGrass(biomeIn);
        }
    },
    DOUBLE_FLOWERS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addDoubleFlowers(biomeIn);
        }
    },
    GRASS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addGrass(biomeIn);
        }
    },
    SWAMP_VEGETATION {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addSwampVegetation(biomeIn);
        }
    },
    HUGE_MUSHROOMS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addHugeMushrooms(biomeIn);
        }
    },
    DEAD_BUSHES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addDeadBushes(biomeIn);
        }
    },
    DEFAULT_FLOWERS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addDefaultFlowers(biomeIn);
        }
    },
    EXTRA_DEFAULT_FLOWERS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addExtraDefaultFlowers(biomeIn);
        }
    },
    MUSHROOMS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addMushrooms(biomeIn);
        }
    },
    REEDS_AND_PUMPKINS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addReedsAndPumpkins(biomeIn);
        }
    },
    REEDS_PUMPKINS_CACTUS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addReedsPumpkinsCactus(biomeIn);
        }
    },
    JUNGLE_PLANTS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addJunglePlants(biomeIn);
        }
    },
    EXTRA_REEDS_PUMPKINS_CACTUS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addExtraReedsPumpkinsCactus(biomeIn);
        }
    },
    DESERT_FEATURES {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addDesertFeatures(biomeIn);
        }
    },
    FOSSILS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addFossils(biomeIn);
        }
    },
    KELP {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addKelp(biomeIn);
        }
    },
    SPRINGS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addSprings(biomeIn);
        }
    },
    ICEBERGS {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addIcebergs(biomeIn);
        }
    },
    BLUE_ICE {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addBlueIce(biomeIn);
        }
    },
    FREEZE_TOP_LAYER {
        @Override
        public void getFeature(Biome biomeIn) {
            DefaultBiomeFeatures.addFreezeTopLayer(biomeIn);
        }
    };

    public abstract void getFeature(Biome biomeIn);
}

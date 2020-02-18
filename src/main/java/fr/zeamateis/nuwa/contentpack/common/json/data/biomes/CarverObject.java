package fr.zeamateis.nuwa.contentpack.common.json.data.biomes;

import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

/**
 * Reprensentation of Json {@link WorldCarver} object
 *
 * @author ZeAmateis
 */
public class CarverObject {

    private GenerationStage.Carving type;
    private Carver carver;
    private float probability;

    public GenerationStage.Carving getType() {
        return type;
    }

    public WorldCarver<ProbabilityConfig> getCarver() {
        return carver.getCarver();
    }

    public float getProbability() {
        return probability;
    }

    public enum Carver {
        CAVE(WorldCarver.CAVE),
        HELL_CAVE(WorldCarver.HELL_CAVE),
        CANYON(WorldCarver.CANYON),
        UNDERWATER_CANYON(WorldCarver.UNDERWATER_CANYON),
        UNDERWATER_CAVE(WorldCarver.UNDERWATER_CAVE);

        WorldCarver<ProbabilityConfig> carver;

        Carver(WorldCarver<ProbabilityConfig> carver) {
            this.carver = carver;
        }

        public WorldCarver<ProbabilityConfig> getCarver() {
            return carver;
        }
    }
}

package fr.zeamateis.nuwa.contentpack.common.json.data.biomes;

import net.minecraft.world.biome.Biome;

/**
 * Reprensentation of Json biome climat parameters object
 *
 * @author ZeAmateis
 */
public class ClimatObject {

    private Biome.RainType rainType;
    private float temperature, downfall;

    public ClimatObject() {
        this.rainType = Biome.RainType.NONE;
        this.temperature = 1.0F;
        this.downfall = 0.0F;
    }

    public Biome.RainType getRainType() {
        return rainType;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getDownfall() {
        return downfall;
    }
}

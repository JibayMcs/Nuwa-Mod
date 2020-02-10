package fr.zeamateis.nuwa.contentpack.common.json.data.biomes;

import net.minecraft.world.biome.Biome;

public class ClimatPropertiesObject {

    private Biome.RainType rainType;
    private float temperature, downfall;

    public ClimatPropertiesObject() {
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

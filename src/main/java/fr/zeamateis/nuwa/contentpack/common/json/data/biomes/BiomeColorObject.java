package fr.zeamateis.nuwa.contentpack.common.json.data.biomes;

/**
 * Representation of Json biome colors parameters objects
 *
 * @author ZeAmateis
 */
public class BiomeColorObject {
    private int waterColor, waterFogColor;
    private Integer grassColor = null, foliageColor = null;

    public int getWaterColor() {
        return waterColor;
    }

    public int getWaterFogColor() {
        return waterFogColor;
    }

    public Integer getGrassColor() {
        return grassColor;
    }

    public Integer getFoliageColor() {
        return foliageColor;
    }
}

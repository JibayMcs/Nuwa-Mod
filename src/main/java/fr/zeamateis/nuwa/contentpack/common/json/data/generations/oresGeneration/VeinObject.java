package fr.zeamateis.nuwa.contentpack.common.json.data.generations.oresGeneration;

import net.minecraft.world.gen.placement.CountRangeConfig;

/**
 * Reprensentation of Json {@link OresGenerationObject} parameters
 *
 * @author ZeAmateis
 */
public class VeinObject {

    private int veinSize;
    private CountRangeConfig rangeConfig;

    public int getVeinSize() {
        return veinSize;
    }

    public CountRangeConfig getRangeConfig() {
        return rangeConfig;
    }

}

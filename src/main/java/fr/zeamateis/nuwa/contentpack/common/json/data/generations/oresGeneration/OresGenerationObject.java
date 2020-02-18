package fr.zeamateis.nuwa.contentpack.common.json.data.generations.oresGeneration;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprensentation of Json Ore {@link net.minecraft.world.gen.feature.Feature} Generation object
 *
 * @author ZeAmateis
 */
public class OresGenerationObject {

    private String dimension;

    private List<String> genInBiomes = new ArrayList<>();

    @SerializedName("ore")
    private String oreBlock;

    @SerializedName("vein")
    private VeinObject veinObject;

    public String getDimension() {
        return dimension;
    }

    public List<String> getGenInBiomes() {
        return genInBiomes;
    }

    public String getOreBlock() {
        return oreBlock;
    }

    public VeinObject getVeinObject() {
        return veinObject;
    }

}

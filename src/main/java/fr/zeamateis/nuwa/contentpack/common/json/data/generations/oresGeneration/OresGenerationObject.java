package fr.zeamateis.nuwa.contentpack.common.json.data.generations.oresGeneration;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OresGenerationObject {

    private DimensionType dimensionType;

    private List<String> genInBiomes = new ArrayList<>();

    @SerializedName("ore")
    private String oreBlock;

    @SerializedName("vein")
    private VeinObject veinObject;

    public DimensionType getDimensionType() {
        return dimensionType;
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

    public enum DimensionType {
        OVERWORLD,
        NETHER,
        END
    }
}

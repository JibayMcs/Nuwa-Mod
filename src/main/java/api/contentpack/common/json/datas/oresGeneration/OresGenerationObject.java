package api.contentpack.common.json.datas.oresGeneration;

import com.google.gson.annotations.SerializedName;

public class OresGenerationObject {

    private DimensionType dimensionType;

    @SerializedName("ore")
    private String oreBlock;

    @SerializedName("vein")
    private VeinObject veinObject;

    public DimensionType getDimensionType() {
        return dimensionType;
    }

    public String getOreBlock() {
        return oreBlock;
    }

    public VeinObject getVeinObject() {
        return veinObject;
    }

    public static enum DimensionType {
        OVERWORLD,
        NETHER,
        END
    }
}

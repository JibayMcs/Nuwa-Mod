package api.contentpack.common.json.datas.blocks.properties;

import com.google.gson.annotations.SerializedName;
import net.minecraft.block.Block;
import net.minecraftforge.common.ToolType;

public class BlockPropertiesObject {

    /**
     * Default Properties for a basic block if json file has no datas
     */
    @SerializedName("material")
    private MaterialObject materialObject = MaterialObject.ROCK;
    private float hardness = 0.0F;
    private float resistance = 0.0F;
    private int lightValue = 0;
    @SerializedName("soundType")
    private SoundTypeObject soundTypeObject = SoundTypeObject.STONE;
    private boolean doesNotBlockMovement = true;
    private int harvestLevel = -1;
    private String harvestTool = ToolType.get("pickaxe").getName();
    private float slipperiness = 0.6F;
    private boolean hasVariableOpacity = false;
    private boolean noDrops = false;

    private transient boolean tickingRandomly = false;

    public Block.Properties getParsedProperties() {
        Block.Properties properties = Block.Properties.create(getMaterialObject().getMaterial());

        properties.hardnessAndResistance(getHardness(), getResistance());
        properties.lightValue(getLightValue());
        properties.sound(getSoundTypeObject().getSoundType());
        properties.harvestLevel(getHarvestLevel());
        properties.harvestTool(getHarvestTool());
        properties.slipperiness(getSlipperiness());

        if (hasVariableOpacity()) {
            properties.variableOpacity();
        }
        if (hasNoDrops()) {
            properties.noDrops();
        }
        if (doesNotBlockMovement()) {
            properties.doesNotBlockMovement();
        }

        /*if (isTickingRandomly()) {
            properties.tickRandomly();
        }*/

        return properties;
    }

    public MaterialObject getMaterialObject() {
        return materialObject;
    }

    public float getHardness() {
        return hardness;
    }

    public float getResistance() {
        return resistance;
    }


    public int getLightValue() {
        return this.lightValue;
    }

    public SoundTypeObject getSoundTypeObject() {
        return soundTypeObject;
    }

    public boolean doesNotBlockMovement() {
        return doesNotBlockMovement;
    }


    public int getHarvestLevel() {
        return harvestLevel;
    }


    public ToolType getHarvestTool() {
        return ToolType.get(this.harvestTool);
    }

    public float getSlipperiness() {
        return slipperiness;
    }

    public boolean hasVariableOpacity() {
        return hasVariableOpacity;
    }

    public boolean hasNoDrops() {
        return noDrops;
    }

    @Deprecated
    public boolean isTickingRandomly() {
        return tickingRandomly;
    }

}
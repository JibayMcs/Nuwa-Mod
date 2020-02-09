package fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties;

import com.google.gson.annotations.SerializedName;
import net.minecraft.block.Block;
import net.minecraftforge.common.ToolType;

public class BlockPropertiesObject {


    /**
     * Default Properties for a basic block if json file has no datas
     */
    @SerializedName("material")
    private MaterialObject materialObject;
    private float hardness;
    private float resistance;
    private int lightValue;
    @SerializedName("soundType")
    private SoundTypeObject soundTypeObject;
    private boolean doesNotBlockMovement;
    private int harvestLevel;
    private String harvestTool;
    private float slipperiness;
    private boolean hasVariableOpacity;
    private boolean noDrops;
    private transient boolean tickingRandomly;

    public BlockPropertiesObject(MaterialObject materialObject, float hardness, float resistance, int lightValue, SoundTypeObject soundTypeObject, boolean doesNotBlockMovement, int harvestLevel, String harvestTool, float slipperiness, boolean hasVariableOpacity, boolean noDrops, boolean tickingRandomly) {
        this.materialObject = materialObject;
        this.hardness = hardness;
        this.resistance = resistance;
        this.lightValue = lightValue;
        this.soundTypeObject = soundTypeObject;
        this.doesNotBlockMovement = doesNotBlockMovement;
        this.harvestLevel = harvestLevel;
        this.harvestTool = harvestTool;
        this.slipperiness = slipperiness;
        this.hasVariableOpacity = hasVariableOpacity;
        this.noDrops = noDrops;
        this.tickingRandomly = tickingRandomly;
    }

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

        if (isTickingRandomly()) {
            properties.tickRandomly();
        }

        return properties;
    }

    private MaterialObject getMaterialObject() {
        return materialObject;
    }

    private float getHardness() {
        return hardness;
    }

    private float getResistance() {
        return resistance;
    }


    private int getLightValue() {
        return this.lightValue;
    }

    private SoundTypeObject getSoundTypeObject() {
        return soundTypeObject;
    }

    private boolean doesNotBlockMovement() {
        return doesNotBlockMovement;
    }


    private int getHarvestLevel() {
        return harvestLevel;
    }


    private ToolType getHarvestTool() {
        return ToolType.get(this.harvestTool);
    }

    private float getSlipperiness() {
        return slipperiness;
    }

    private boolean hasVariableOpacity() {
        return hasVariableOpacity;
    }

    private boolean hasNoDrops() {
        return noDrops;
    }

    @Deprecated
    public boolean isTickingRandomly() {
        return tickingRandomly;
    }


}
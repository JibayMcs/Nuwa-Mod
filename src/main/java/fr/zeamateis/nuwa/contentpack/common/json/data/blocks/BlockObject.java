package fr.zeamateis.nuwa.contentpack.common.json.data.blocks;

import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties.BlockPropertiesObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties.CropsProperties;
import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties.FallingProperties;
import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties.OreProperties;
import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.shape.VoxelShapeObject;

/**
 * Reprensentation of Json {@link net.minecraft.block.Block} object
 *
 * @author ZeAmateis
 */
public class BlockObject {

    private String registryName;
    private String itemGroup;
    private String blockType;

    private BlockPropertiesObject properties;

    private VoxelShapeObject voxelShape;

    private CropsProperties cropsProperties;

    private OreProperties oreProperties;

    private String event;

    /**
     * Used for {@link fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.JsonFallingBlock}
     */
    private FallingProperties fallingProperties;

    public BlockPropertiesObject getProperties() {
        return properties;
    }

    public String getRegistryName() {
        return registryName;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public VoxelShapeObject getVoxelShape() {
        return voxelShape;
    }

    public String getBlockType() {
        return blockType;
    }

    public CropsProperties getCropsProperties() {
        return cropsProperties;
    }

    public OreProperties getOreProperties() {
        return oreProperties;
    }

    public String getEvent() {
        return event;
    }

    public FallingProperties getFallingProperties() {
        return fallingProperties;
    }
}

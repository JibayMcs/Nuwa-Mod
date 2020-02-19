package fr.zeamateis.nuwa.contentpack.common.json.data.blocks;

import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties.BlockPropertiesObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.properties.OreProperties;
import fr.zeamateis.nuwa.contentpack.common.json.data.blocks.shape.VoxelShapeObject;

/**
 * Reprensentation of Json {@link net.minecraft.block.Block} object
 *
 * @author ZeAmateis
 */
public class BlockObject {

    private BlockPropertiesObject properties;
    private String registryName;
    private String itemGroup;
    private VoxelShapeObject voxelShape;
    private String blockType;

    private String cropSeed;

    private OreProperties oreProperties;

    private String event;

    public BlockObject(String registryName, String itemGroup) {
        this.registryName = registryName;
        this.itemGroup = itemGroup;
    }

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

    public String getCropSeed() {
        return cropSeed;
    }

    public OreProperties getOreProperties() {
        return oreProperties;
    }

    public String getEvent() {
        return event;
    }
}

package api.contentpack.common.json.datas.blocks;

import api.contentpack.common.json.datas.blocks.properties.BlockEventsObject;
import api.contentpack.common.json.datas.blocks.properties.BlockPropertiesObject;
import api.contentpack.common.json.datas.blocks.properties.ContainerPropertiesObject;
import api.contentpack.common.json.datas.blocks.properties.OreProperties;
import api.contentpack.common.json.datas.blocks.shape.VoxelShapeObject;

public class BlockObject {

    private BlockPropertiesObject properties;
    private String registryName;
    private String itemGroup;
    private VoxelShapeObject voxelShape;
    private String blockType;

    private String cropSeed;

    private ContainerPropertiesObject containerProperties;

    private OreProperties oreProperties;

    private BlockEventsObject events;

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

    public ContainerPropertiesObject getContainerProperties() {
        return containerProperties;
    }

    public OreProperties getOreProperties() {
        return oreProperties;
    }

    public BlockEventsObject getEvents() {
        return events;
    }
}

package api.contentpack.common.json.datas.blocks;

import api.contentpack.common.json.datas.blocks.properties.BlockPropertiesObject;
import api.contentpack.common.json.datas.blocks.shape.VoxelShapeObject;

public class BlocksObject {

    private BlockPropertiesObject properties;
    private String registryName;
    private String itemGroup;
    private VoxelShapeObject voxelShape;

    public BlocksObject(String registryName, String itemGroup) {
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

}

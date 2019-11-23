package api.contentpack.common.json.datas.blocks.shape;

import net.minecraft.util.math.shapes.VoxelShape;

public class VoxelShapeObject {

    private ShapeObject shape, collisionShape;

    public VoxelShape getShape() {
        return ShapeObject.hasVoxelShape(shape);
    }

    public VoxelShape getCollisionShape() {
        return ShapeObject.hasVoxelShape(collisionShape);
    }

}

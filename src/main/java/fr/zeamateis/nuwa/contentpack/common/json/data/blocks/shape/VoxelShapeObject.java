package fr.zeamateis.nuwa.contentpack.common.json.data.blocks.shape;

import net.minecraft.util.math.shapes.VoxelShape;

/**
 * Reprensentation of Json {@link VoxelShape} object
 *
 * @author ZeAmateis
 */
public class VoxelShapeObject {

    private ShapeObject shape, collisionShape;

    public ShapeObject getShape() {
        return shape;
    }

    public ShapeObject getCollisionShape() {
        return collisionShape;
    }

    public VoxelShape getVoxelShape() {
        return ShapeObject.toVoxelShape(shape);
    }

    public VoxelShape getVoxelCollisionShape() {
        return ShapeObject.toVoxelShape(collisionShape);
    }

}

package fr.zeamateis.nuwa.contentpack.common.json.data.blocks.shape;

import net.minecraft.util.math.shapes.VoxelShape;

/**
 * Reprensentation of Json {@link VoxelShape} object
 *
 * @author ZeAmateis
 */
public class VoxelShapeObject {

    private ShapeObject shape, collisionShape;

    public VoxelShape getShape() {
        return ShapeObject.toVoxelShape(shape);
    }

    public VoxelShape getCollisionShape() {
        return ShapeObject.toVoxelShape(collisionShape);
    }

}

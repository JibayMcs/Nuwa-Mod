package fr.zeamateis.nuwa.contentpack.common.json.data.blocks.shape;

import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;

/**
 * Reprensentation of Json {@link VoxelShape} parameters object
 *
 * @author ZeAmateis
 */
public class ShapeObject {

    private double x1, y1, z1, x2, y2, z2;

    public ShapeObject(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    /**
     * Convert {@link ShapeObject} to a {@link VoxelShape}
     *
     * @param shapeIn
     * @return Parsed {@link ShapeObject} values as {@link VoxelShape}
     */
    public static VoxelShape toVoxelShape(ShapeObject shapeIn) {
        //return VoxelShapes.create(shapeIn.getX1(), shapeIn.getY1(), shapeIn.getZ1(), shapeIn.getX2(), shapeIn.getY2(), shapeIn.getZ2());
        return Block.makeCuboidShape(shapeIn.getX1(), shapeIn.getY1(), shapeIn.getZ1(), shapeIn.getX2(), shapeIn.getY2(), shapeIn.getZ2());
    }


    public static ShapeObject toShapeObject(AxisAlignedBB axisAlignedBBIn) {
        return new ShapeObject(axisAlignedBBIn.minX, axisAlignedBBIn.minY, axisAlignedBBIn.minZ, axisAlignedBBIn.maxX, axisAlignedBBIn.maxY, axisAlignedBBIn.maxZ);
    }

    /**
     * Convert {@link VoxelShape} to a {@link ShapeObject}
     *
     * @param voxelShapeIn
     * @return {@link ShapeObject} value
     */
    public static ShapeObject toShapeObject(VoxelShape voxelShapeIn) {
        return toShapeObject(voxelShapeIn.getBoundingBox());
    }

    public double getX1() {
        return x1;
    }


    public double getY1() {
        return y1;
    }


    public double getZ1() {
        return z1;
    }


    public double getX2() {
        return x2;
    }


    public double getY2() {
        return y2;
    }


    public double getZ2() {
        return z2;
    }

}

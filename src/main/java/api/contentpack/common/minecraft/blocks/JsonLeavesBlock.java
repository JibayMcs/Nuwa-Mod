package api.contentpack.common.minecraft.blocks;

import api.contentpack.common.minecraft.RegistryUtil;
import api.contentpack.common.minecraft.blocks.base.IJsonBlock;
import api.contentpack.common.minecraft.blocks.base.ILeavesColor;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.shapes.VoxelShape;

import javax.annotation.Nonnull;

public class JsonLeavesBlock extends LeavesBlock implements IJsonBlock, ILeavesColor {

    private ItemGroup itemGroup;

    public JsonLeavesBlock(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }

    @Override
    public VoxelShape getShape() {
        return null;
    }

    @Override
    public void setShape(VoxelShape shape) {

    }

    @Override
    public VoxelShape getCollisionShape() {
        return null;
    }

    @Override
    public void setCollisionShape(VoxelShape collisionShape) {

    }

    @Override
    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    @Override
    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }
}

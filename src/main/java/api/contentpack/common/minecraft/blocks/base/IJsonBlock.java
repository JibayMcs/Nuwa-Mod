package api.contentpack.common.minecraft.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IJsonBlock extends IForgeRegistryEntry<Block>, IForgeBlock {
    VoxelShape getShape();

    void setShape(VoxelShape shape);

    VoxelShape getCollisionShape();

    void setCollisionShape(VoxelShape collisionShape);

    ItemGroup getItemGroup();

    void setItemGroup(ItemGroup itemGroup);

}

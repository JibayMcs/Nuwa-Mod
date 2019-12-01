package api.contentpack.common.minecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeBlock;

public interface IJsonBlock extends net.minecraftforge.registries.IForgeRegistryEntry<Block>, IForgeBlock {
    VoxelShape getShape();

    void setShape(VoxelShape shape);

    VoxelShape getCollisionShape();

    void setCollisionShape(VoxelShape collisionShape);

    ItemGroup getItemGroup();

    void setItemGroup(ItemGroup itemGroup);
}

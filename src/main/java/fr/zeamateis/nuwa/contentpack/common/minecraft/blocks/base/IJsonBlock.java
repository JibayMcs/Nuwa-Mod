package fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.blocks.BlockEventObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.blocks.EntityBlockEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IJsonBlock extends IForgeRegistryEntry<Block>, IForgeBlock {
    VoxelShape getShape();

    void setShape(VoxelShape shape);

    VoxelShape getCollisionShape();

    void setCollisionShape(VoxelShape collisionShape);

    ItemGroup getItemGroup();

    void setItemGroup(ItemGroup itemGroup);

    BlockEventObject getBlockEventObject();

    void setBlockEventObject(BlockEventObject eventProperties);

    default void onEntityCollisionEvent(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (getBlockEventObject() != null) {
            EntityBlockEvent entityBlockEvent = getBlockEventObject().getEntityCollideBlockEvent();
            if (entityBlockEvent != null) {
                if (!entityBlockEvent.getAffectedEntities().isEmpty()) {
                    entityBlockEvent.getAffectedEntities().stream().forEach(entityType -> {
                        entityBlockEvent.getProcesses().forEach(process -> {
                            if (entityIn.getType().equals(entityType)) {
                                process.process(worldIn, pos, entityIn);
                            }
                        });
                    });
                } else {
                    entityBlockEvent.getProcesses().forEach(process -> {
                        process.process(worldIn, pos, entityIn);
                    });
                }
            }
        }
    }
}

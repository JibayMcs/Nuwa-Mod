package api.contentpack.common.minecraft.blocks.base;

import api.contentpack.common.json.datas.blocks.events.base.EntityBlockEvent;
import api.contentpack.common.json.datas.blocks.events.processes.base.IEntityProcess;
import api.contentpack.common.json.datas.blocks.properties.BlockEventsObject;
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

    BlockEventsObject getBlockEventObject();

    void setBlockEventObject(BlockEventsObject eventProperties);

    default void onEntityCollisionEvent(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (getBlockEventObject() != null) {
            EntityBlockEvent entityBlockEvent = getBlockEventObject().getEntityCollideBlockEvent();
            if (entityBlockEvent != null) {
                if (!entityBlockEvent.getAffectedEntities().isEmpty()) {
                    entityBlockEvent.getAffectedEntities().stream().forEach(entityType -> {
                        entityBlockEvent.getProcesses().stream().filter(process -> process instanceof IEntityProcess).forEach(process -> {
                            if (entityIn.getType().equals(entityType)) {
                                ((IEntityProcess) process).process(worldIn, entityIn);
                            }
                        });
                    });

                }
            }
        }
    }
}

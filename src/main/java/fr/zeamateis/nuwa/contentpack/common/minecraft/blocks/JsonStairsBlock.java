package fr.zeamateis.nuwa.contentpack.common.minecraft.blocks;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.blocks.BlockEventObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.IJsonBlock;
import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.shapes.VoxelShape;

import javax.annotation.Nonnull;

/**
 * Json parsed {@link StairsBlock}
 *
 * @author ZeAmateis
 */
public class JsonStairsBlock extends StairsBlock implements IJsonBlock {

    private VoxelShape shape, collisionShape;
    private ItemGroup itemGroup;
    private BlockEventObject eventProperties;

    public JsonStairsBlock(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(Blocks.STONE.getDefaultState(), properties);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }

    @Override
    public BlockEventObject getBlockEventObject() {
        return this.eventProperties;
    }

    @Override
    public void setBlockEventObject(BlockEventObject eventProperties) {
        this.eventProperties = eventProperties;
    }

    @Override
    public VoxelShape getShape() {
        return shape;
    }

    @Override
    public void setShape(VoxelShape shape) {
        this.shape = shape;
    }

    @Override
    public VoxelShape getCollisionShape() {
        return collisionShape;
    }

    @Override
    public void setCollisionShape(VoxelShape collisionShape) {
        this.collisionShape = collisionShape;
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

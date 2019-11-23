package api.contentpack.common.minecraft.blocks;


import api.contentpack.common.minecraft.RegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class JsonBlock extends Block {

    private VoxelShape shape, collisionShape;
    private ItemGroup itemGroup;

    public JsonBlock(Properties properties, @Nonnull ResourceLocation registryNameIn) {
        super(properties);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }

    public void setCollisionShape(VoxelShape collisionShape) {
        this.collisionShape = collisionShape;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shape;
    }

    public VoxelShape getShape() {
        return shape;
    }

    public void setShape(VoxelShape shape) {
        this.shape = shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.blocksMovement ? this.collisionShape : VoxelShapes.empty();
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityCollision(state, worldIn, pos, entityIn);

       /* MCSMethod parserMethod = parser.parsedMethods.get(0);
        boolean asCheckParams = !parserMethod.getCheckArgs().isEmpty();

        try {
            Field[] fields = DamageSource.class.getDeclaredFields();
            Method attackEntityFrom = entityIn.getClass().getDeclaredMethod(parserMethod.getMethod(), DamageSource.class, float.class);

            if (attackEntityFrom != null) {
                if (!asCheckParams) {
                    for (Field damageSourceField : fields) {
                        if (damageSourceField.getType().equals(DamageSource.class)) {
                            if (damageSourceField.getName().equals(parserMethod.getMethodParameters().get(0))) {
                                attackEntityFrom.invoke(entityIn, damageSourceField.get(DamageSource.class), Float.valueOf(parserMethod.getMethodParameters().get(1)));
                            }
                        }
                    }
                } else {
                    for (Field damageSourceField : fields) {
                        if (damageSourceField.getType().equals(DamageSource.class)) {
                            if (damageSourceField.getName().equals(parserMethod.getMethodParameters().get(0))) {
                                for (String instanceofArgs : parserMethod.getCheckArgs()) {
                                    Class checkClass = Class.forName(instanceofArgs);
                                    if (checkClass.isAssignableFrom(entityIn.getClass())) {
                                        attackEntityFrom.invoke(entityIn, damageSourceField.get(DamageSource.class), Float.valueOf(parserMethod.getMethodParameters().get(1)));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
    }


}

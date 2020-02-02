package api.contentpack.common.minecraft.items.tool;

import api.contentpack.common.minecraft.items.base.IJsonItem;
import api.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Set;

public class JsonPixaxeItem extends ToolItem implements IJsonItem {

    public JsonPixaxeItem(IItemTier tier, Set<Block> effectiveOn, float attackDamageIn, float attackSpeedIn, Item.Properties builder, @Nonnull ResourceLocation registryNameIn) {
        super(attackDamageIn, attackSpeedIn, tier, effectiveOn, builder.addToolType(net.minecraftforge.common.ToolType.PICKAXE, tier.getHarvestLevel()));
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        int i = this.getTier().getHarvestLevel();
        if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
            return i >= blockIn.getHarvestLevel();
        }
        Material material = blockIn.getMaterial();
        return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? super.getDestroySpeed(stack, state) : this.efficiency;
    }
}

package api.contentpack.common.minecraft.items;

import api.contentpack.common.minecraft.RegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class JsonBlockItem extends BlockItem {

    private String craftstudioModelID = "null";

    public JsonBlockItem(Block blockIn, Properties builder, @Nonnull ResourceLocation registryNameIn) {
        super(blockIn, builder);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }

    public void setCraftstudioModelID(String craftstudioModelID) {
        this.craftstudioModelID = craftstudioModelID;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     *
     * @param stack
     * @param worldIn
     * @param tooltip
     * @param flagIn
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        CompoundNBT compoundNBT = new CompoundNBT();
        if (!compoundNBT.contains("ModelID")) {
            compoundNBT.putString("ModelID", craftstudioModelID);
            stack.setTag(compoundNBT);
        }
    }
}

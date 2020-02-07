package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IEntityProcess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class GiveItemProcess implements IEntityProcess {

    private List<ItemStack> itemStack;

    public List<ItemStack> getItemStack() {
        return itemStack;
    }

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entityIn;
            if (itemStack != null)
                itemStack.stream().forEach(itemStack -> {
                    boolean flag = playerEntity.inventory.addItemStackToInventory(itemStack);
                    if (flag && itemStack.isEmpty()) {
                        itemStack.setCount(1);
                        ItemEntity itementity1 = playerEntity.dropItem(itemStack, false);
                        if (itementity1 != null) {
                            itementity1.makeFakeItem();
                        }
                        itemStack.setCount(1);
                    }
                });

        }
    }

    @Override
    public String getRegistryName() {
        return "nuwa:give_item_process";
    }
}

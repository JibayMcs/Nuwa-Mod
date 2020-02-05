package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IEntityProcess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GiveItemProcess implements IEntityProcess {

    private ItemStack itemStack;

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void process(World worldIn, Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entityIn;
            ItemStack itemstack = getItemStack();
            boolean flag = playerEntity.inventory.addItemStackToInventory(itemstack);

            if (flag && itemstack.isEmpty()) {
                itemstack.setCount(1);
                ItemEntity itementity1 = playerEntity.dropItem(itemstack, false);
                if (itementity1 != null) {
                    itementity1.makeFakeItem();
                }
                itemstack.setCount(1);
            }
        }
    }

    @Override
    public String getRegistryName() {
        return "nuwa:give_item_process";
    }
}

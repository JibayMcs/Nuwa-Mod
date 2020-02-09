package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import com.google.gson.annotations.SerializedName;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IProcess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class InventoryProcess implements IProcess {

    @SerializedName("addItemToInventory")
    private AddObject add;
    @SerializedName("removeItemFromInventory")
    private RemoveObject remove;
    private DamageArmorObject damageArmor;
    private DamageItemObject damageItem;
    private boolean dropAllItems;

    @Override
    public void process(World worldIn, BlockPos posIn, Entity entityIn) {
        if (!worldIn.isRemote())
            if (entityIn instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entityIn;
                if (add != null) {
                    if (add.itemStackList != null)
                        add.itemStackList.forEach(itemStack -> giveItemStack(playerEntity, itemStack));
                    else if (add.itemStack != null) {
                        giveItemStack(playerEntity, add.itemStack);
                    }
                }
                if (remove != null) {
                    if (remove.itemStackList != null) {
                        remove.itemStackList.forEach(itemStack -> removeItemStack(playerEntity, itemStack));
                    } else if (remove.itemStack != null) {
                        removeItemStack(playerEntity, remove.itemStack);
                    }
                }
                if (damageArmor != null) {
                    if (damageArmor.amount != -1.0) {
                        playerEntity.inventory.damageArmor(damageArmor.amount);
                    }
                }
                if (damageItem != null) {
                    if (damageItem.amount != -1) {
                        if (damageItem.itemStackList != null)
                            damageItem.itemStackList.stream().filter(playerEntity.inventory::hasItemStack).forEach(itemStack -> itemStack.damageItem(damageItem.amount, playerEntity, onBroken -> {
                                if (playerEntity.getHeldItemMainhand().equals(itemStack) || playerEntity.getHeldItemOffhand().equals(itemStack))
                                    playerEntity.sendBreakAnimation(playerEntity.getActiveHand());
                            }));
                        else if (damageItem.itemStack != null) {
                            if (playerEntity.inventory.hasItemStack(damageItem.itemStack)) {
                                damageItem.itemStack.damageItem(damageItem.amount, playerEntity, onBroken -> {
                                    if (playerEntity.getHeldItemMainhand().equals(damageItem.itemStack) || playerEntity.getHeldItemOffhand().equals(damageItem.itemStack))
                                        playerEntity.sendBreakAnimation(playerEntity.getActiveHand());
                                });
                            }
                        }
                    }
                }
                if (dropAllItems) {
                    playerEntity.inventory.dropAllItems();
                }
            }
    }

    private void giveItemStack(PlayerEntity playerEntityIn, ItemStack itemStackIn) {
        boolean flag = playerEntityIn.inventory.addItemStackToInventory(itemStackIn);
        if (flag && itemStackIn.isEmpty()) {
            itemStackIn.setCount(1);
            ItemEntity itemEntity = playerEntityIn.dropItem(itemStackIn, false);
            if (itemEntity != null) {
                itemEntity.makeFakeItem();
            }
            itemStackIn.setCount(1);
        }
    }

    private void removeItemStack(PlayerEntity playerEntityIn, ItemStack itemStackIn) {
        if (playerEntityIn.inventory.hasItemStack(itemStackIn))
            playerEntityIn.inventory.deleteStack(itemStackIn);
    }

    @Override
    public String getRegistryName() {
        return "nuwa:inventory_process";
    }

    static class AddObject {
        private List<ItemStack> itemStackList;
        private ItemStack itemStack;

        public List<ItemStack> getItemStackList() {
            return itemStackList;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }
    }

    static class RemoveObject {
        private List<ItemStack> itemStackList;
        private ItemStack itemStack;

        public List<ItemStack> getItemStackList() {
            return itemStackList;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

    }

    static class DamageArmorObject {
        private float amount;

        public float getAmount() {
            return amount;
        }
    }

    static class DamageItemObject {
        private List<ItemStack> itemStackList;
        private ItemStack itemStack;
        private int amount;

        public int getAmount() {
            return amount;
        }

        public List<ItemStack> getItemStackList() {
            return itemStackList;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

    }

}

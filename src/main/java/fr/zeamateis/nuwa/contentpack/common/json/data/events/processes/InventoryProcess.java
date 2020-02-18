package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes;

import api.contentpack.json.process.IProcess;
import com.google.gson.annotations.SerializedName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

/**
 * {@link IProcess} to manipulate {@link PlayerEntity#inventory}
 *
 * @author ZeAmateis
 */
public class InventoryProcess implements IProcess {

    @SerializedName("addItemToInventory")
    private ItemStackObject add;
    @SerializedName("removeItemFromInventory")
    private ItemStackObject remove;
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
                        add.itemStackList.forEach(itemStack -> giveItemStack(playerEntity, getItemStack(itemStack)));
                    else if (add.itemStack != null) {
                        giveItemStack(playerEntity, getItemStack(add.itemStack));
                    }
                }
                if (remove != null) {
                    if (remove.itemStackList != null) {
                        remove.itemStackList.forEach(itemStack -> removeItemStack(playerEntity, getItemStack(itemStack)));
                    } else if (remove.itemStack != null) {
                        removeItemStack(playerEntity, getItemStack(remove.itemStack));
                    }
                }
                if (damageArmor != null) {
                    if (damageArmor.amount != -1.0) {
                        playerEntity.inventory.damageArmor(damageArmor.amount);
                    }
                }
                if (damageItem != null) {
                    if (damageItem.amount != -1) {
                        if (damageItem.getItemStackList() != null)
                            damageItem.getItemStackList().forEach(itemStackName -> {
                                ItemStack parsedStack = getItemStack(itemStackName);
                                parsedStack.damageItem(damageItem.amount, playerEntity, onBroken -> {
                                    if (playerEntity.getHeldItemMainhand().equals(parsedStack) || playerEntity.getHeldItemOffhand().equals(parsedStack))
                                        playerEntity.sendBreakAnimation(playerEntity.getActiveHand());
                                });
                            });
                        else if (damageItem.getItemStack() != null) {
                            ItemStack parsedStack = getItemStack(damageItem.getItemStack());
                            if (playerEntity.inventory.hasItemStack(parsedStack)) {
                                parsedStack.damageItem(damageItem.amount, playerEntity, onBroken -> {
                                    if (playerEntity.getHeldItemMainhand().equals(parsedStack) || playerEntity.getHeldItemOffhand().equals(parsedStack))
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

    private ItemStack getItemStack(String itemRegistryNameIn) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemRegistryNameIn)).getDefaultInstance();
    }

    @Override
    public String getRegistryName() {
        return "nuwa:inventory_process";
    }

    static class ItemStackObject {
        private List<String> itemStackList;
        private String itemStack;

        public List<String> getItemStackList() {
            return itemStackList;
        }

        public String getItemStack() {
            return itemStack;
        }
    }


    static class DamageArmorObject {
        private float amount;

        public float getAmount() {
            return amount;
        }
    }

    static class DamageItemObject extends ItemStackObject {
        private int amount;

        public int getAmount() {
            return amount;
        }

    }

}

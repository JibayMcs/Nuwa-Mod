package fr.zeamateis.nuwa.contentpack.common.minecraft.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;

public class JsonChestContainer extends Container {

    protected JsonChestContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }

    /**
     * Determines whether supplied player can use this container
     *
     * @param playerIn
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return false;
    }
}

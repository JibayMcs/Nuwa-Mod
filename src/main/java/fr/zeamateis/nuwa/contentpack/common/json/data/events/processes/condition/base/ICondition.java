package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition.base;

import net.minecraft.entity.player.PlayerEntity;

public interface ICondition {

    boolean test(PlayerEntity playerEntityIn);

    String getRegistryName();
}

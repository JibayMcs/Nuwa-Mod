package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition.base;

import net.minecraft.entity.player.PlayerEntity;

public interface IPlayerCondition extends ICondition {

    boolean test(PlayerEntity playerEntityIn);
}

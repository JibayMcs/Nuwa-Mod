package api.contentpack.json.conditions;

import net.minecraft.entity.player.PlayerEntity;

public interface IPlayerCondition extends ICondition {

    boolean test(PlayerEntity playerEntityIn);
}

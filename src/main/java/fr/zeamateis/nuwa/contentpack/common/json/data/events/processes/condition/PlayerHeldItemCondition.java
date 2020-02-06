package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition.base.Check;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition.base.IPlayerCondition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class PlayerHeldItemCondition implements IPlayerCondition {
    private Hand hand;
    private Check check;

    public Hand getHand() {
        return hand;
    }

    @Override
    public boolean test(PlayerEntity playerEntityIn) {
        System.out.println(check.value);

        switch (check.type) {
            case IS_EMPTY: {
                return (Boolean) check.value;
            }
            case EQUAL: {
                return playerEntityIn.getHeldItem(hand).getItem().equals(ForgeRegistries.ITEMS.getValue(new ResourceLocation((String) check.value)));
            }
            default:
                return false;
        }
    }

    @Override
    public String getRegistryName() {
        return "nuwa:player_held_item";
    }
}
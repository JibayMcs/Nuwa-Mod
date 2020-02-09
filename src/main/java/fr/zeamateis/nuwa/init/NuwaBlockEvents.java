package fr.zeamateis.nuwa.init;

import fr.zeamateis.nuwa.Constant;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.base.ProcessEvent;
import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.IJsonBlock;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constant.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NuwaBlockEvents {


    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent event) {
        Block block = event.getState().getBlock();
        World world = event.getWorld().getWorld();

        if (block instanceof IJsonBlock) {
            IJsonBlock jsonBlock = (IJsonBlock) block;
            if (jsonBlock.getBlockEventObject() != null) {
                ProcessEvent playerBreakBlock = jsonBlock.getBlockEventObject().getPlayerDestroyBlockEvent();
                if (playerBreakBlock != null) {
                    playerBreakBlock.getProcesses().forEach(process -> {
                            process.process(world, event.getPos(), event.getPlayer());
                    });
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

        if (block instanceof IJsonBlock) {
            IJsonBlock jsonBlock = (IJsonBlock) block;
            if (jsonBlock.getBlockEventObject() != null) {
                ProcessEvent leftClickBlock = jsonBlock.getBlockEventObject().getLeftClickBlockEvent();
                if (leftClickBlock != null) {
                    leftClickBlock.getProcesses().stream().forEach(process -> {
                        process.process(event.getWorld(), event.getPos(), event.getPlayer());
                    });
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

        if (block instanceof IJsonBlock) {
            IJsonBlock jsonBlock = (IJsonBlock) block;
            if (jsonBlock.getBlockEventObject() != null) {
                ProcessEvent rightClickBlock = jsonBlock.getBlockEventObject().getRightClickBlockEvent();
                if (rightClickBlock != null) {
                    rightClickBlock.getProcesses().forEach(process -> {
                      /*  process.conditions.stream().filter(iCondition -> iCondition instanceof IPlayerCondition).forEach(iCondition -> {
                            System.out.println(iCondition.getRegistryName());
                            System.out.println(((IPlayerCondition) iCondition).test(event.getPlayer()));
                        });*/
                        process.process(event.getWorld(), event.getPos(), event.getPlayer());
                    });
                }
            }
        }
    }

}

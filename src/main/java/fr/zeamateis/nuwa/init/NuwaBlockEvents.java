package fr.zeamateis.nuwa.init;

import api.contentpack.common.json.datas.events.base.ProcessEvent;
import api.contentpack.common.json.datas.events.processes.base.IEntityProcess;
import api.contentpack.common.minecraft.blocks.base.IJsonBlock;
import fr.zeamateis.nuwa.Constant;
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
                        if (process instanceof IEntityProcess) {
                            ((IEntityProcess) process).process(world, event.getPlayer());
                        }
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
                    leftClickBlock.getProcesses().stream().filter(iProcess -> iProcess instanceof IEntityProcess).forEach(process -> {
                        ((IEntityProcess) process).process(event.getWorld(), event.getPlayer());
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
                    rightClickBlock.getProcesses().stream().filter(iProcess -> iProcess instanceof IEntityProcess).forEach(process -> {
                        ((IEntityProcess) process).process(event.getWorld(), event.getPlayer());
                    });
                }
            }
        }
    }

}

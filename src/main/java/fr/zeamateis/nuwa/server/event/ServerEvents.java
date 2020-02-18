package fr.zeamateis.nuwa.server.event;

import fr.zeamateis.nuwa.Constant;
import fr.zeamateis.nuwa.NuwaMod;
import fr.zeamateis.nuwa.common.network.S2CContentPackInfoPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

/**
 * Server-side events
 *
 * @author ZeAmateis
 */
@Mod.EventBusSubscriber(modid = Constant.MODID, value = Dist.DEDICATED_SERVER, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    /**
     * Sending packet on player connecting on server to check any content packs mismatch from client/server
     */
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() != null) {
            NuwaMod.getNetwork().send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new S2CContentPackInfoPacket(event.getPlayer().getUniqueID()));
        }
    }

}

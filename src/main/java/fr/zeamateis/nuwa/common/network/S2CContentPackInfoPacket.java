package fr.zeamateis.nuwa.common.network;

import api.contentpack.ContentPack;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Supplier;

public class S2CContentPackInfoPacket {

    private UUID playerEntity;

    public S2CContentPackInfoPacket(UUID playerEntity) {
        this.playerEntity = playerEntity;
    }

    public static void encode(S2CContentPackInfoPacket packet, PacketBuffer buffer) {
        buffer.writeUniqueId(packet.playerEntity);
    }

    public static S2CContentPackInfoPacket decode(PacketBuffer buffer) {
        return new S2CContentPackInfoPacket(buffer.readUniqueId());
    }

    public static void handle(S2CContentPackInfoPacket packet, Supplier<NetworkEvent.Context> ctx) {
        for (ContentPack packs : NuwaMod.getPackManager().getPacks()) {
            try {
                try (InputStream is = Files.newInputStream(Paths.get(packs.getFile().getPath()))) {
                    String md5 = DigestUtils.md5Hex(is);
                    NuwaMod.getNetwork().sendToServer(new C2SContentPackInfoPacket(packet.playerEntity, packs.getPackName(), packs.getVersion(), md5));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        ctx.get().setPacketHandled(true);
    }
}

package fr.zeamateis.nuwa.common.network;

import api.contentpack.ContentPack;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Client-to-Server Content pack info packet to check md5 mismatch between Client and Server
 *
 * @author ZeAmateis
 */
public class C2SContentPackInfoPacket {

    private UUID playerEntity;
    private String contentPackName;
    private String contentPackVersion;
    private String md5;

    C2SContentPackInfoPacket(UUID playerEntity, String contentPackName, String contentPackVersion, String md5) {
        this.playerEntity = playerEntity;
        this.contentPackName = contentPackName;
        this.contentPackVersion = contentPackVersion;
        this.md5 = md5;
    }

    public static void encode(C2SContentPackInfoPacket packet, PacketBuffer buffer) {
        buffer.writeUniqueId(packet.playerEntity);
        buffer.writeString(packet.contentPackName, 32767);
        buffer.writeString(packet.contentPackVersion, 32767);
        buffer.writeString(packet.md5, 32 + 1);
    }

    public static C2SContentPackInfoPacket decode(PacketBuffer buffer) {
        return new C2SContentPackInfoPacket(buffer.readUniqueId(), buffer.readString(32767), buffer.readString(32767), buffer.readString(32 + 1));
    }

    public static void handle(C2SContentPackInfoPacket packet, Supplier<NetworkEvent.Context> ctx) {
        for (ContentPack packs : NuwaMod.getPackManager().getPacks()) {
            try {
                if (packs.getPackName().equals(packet.contentPackName)) {
                    try (InputStream is = Files.newInputStream(Paths.get(packs.getFile().getPath()))) {
                        String md5 = DigestUtils.md5Hex(is);
                        if (!md5.equals(packet.md5)) {
                            Objects.requireNonNull(ctx.get().getSender()).connection.disconnect(new TranslationTextComponent("nuwa.multiplayer.contentpack.mismatch", packet.contentPackName, packet.contentPackVersion, packet.md5, packet.contentPackName, packet.contentPackVersion, md5, packs.getPackName(), packs.getVersion()));
                        }
                    }
                }
            } catch (IOException ex) {
                ctx.get().setPacketHandled(false);
            }
        }
        ctx.get().setPacketHandled(true);
    }
}

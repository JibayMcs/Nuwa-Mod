package fr.zeamateis.nuwa.proxy;

import net.minecraft.client.Minecraft;

import java.io.File;

/**
 * @author ZeAmateis
 */
public class ClientProxy implements IProxy {

    /**
     * @return Client-side Content Packs folder
     */
    @Override
    public File getPackDir() {
        return new File(Minecraft.getInstance().gameDir, "/contentpacks/");
    }

}

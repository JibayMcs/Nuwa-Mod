package fr.zeamateis.nuwa.proxy;

import net.minecraft.client.Minecraft;

import java.io.File;

public class ClientProxy extends CommonProxy {

    @Override
    public File getPackDir() {
        return new File(Minecraft.getInstance().gameDir, "/contentpacks/");
    }
}

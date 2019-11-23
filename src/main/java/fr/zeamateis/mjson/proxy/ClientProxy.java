package fr.zeamateis.mjson.proxy;

import api.contentpack.common.ContentPack;
import net.minecraft.client.Minecraft;

import java.io.File;

public class ClientProxy extends CommonProxy {

    @Override
    public File getPackDir() {
        return new File(Minecraft.getInstance().gameDir, "/contentpacks/");
    }

    @Override
    public void registerPackFinder(ContentPack contentPackIn) {
        //ContentPackFinder contentPackFinder = new ContentPackFinder(contentPackIn);
        //Minecraft.getInstance().getResourcePackList().addPackFinder(contentPackFinder);
    }

}

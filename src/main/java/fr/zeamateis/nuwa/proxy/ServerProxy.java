package fr.zeamateis.nuwa.proxy;

import java.io.File;

/**
 * @author ZeAmateis
 */
public class ServerProxy implements IProxy {

    /**
     * @return Server-side Content Packs folder
     */
    @Override
    public File getPackDir() {
        return new File(".", "/contentpacks/");
    }

}

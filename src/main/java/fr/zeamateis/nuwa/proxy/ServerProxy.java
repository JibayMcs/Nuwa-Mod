package fr.zeamateis.nuwa.proxy;

import java.io.File;

public class ServerProxy extends CommonProxy {

    @Override
    public File getPackDir() {
        return new File(".", "/contentpacks/");
    }

}

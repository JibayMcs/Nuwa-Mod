package fr.zeamateis.nuwa.proxy;

import api.contentpack.common.ContentPack;

import java.io.File;

public interface IProxy {

    File getPackDir();

    void objectsRegistry(ContentPack contentPackIn);

}

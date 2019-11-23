package fr.zeamateis.mjson.proxy;

import api.contentpack.common.ContentPack;

import java.io.File;

public interface IProxy {

    File getPackDir();

    void objectsRegistry(ContentPack contentPackIn);

    void registerPackFinder(ContentPack contentPackIn);

}

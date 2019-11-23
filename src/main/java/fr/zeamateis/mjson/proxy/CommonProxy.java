package fr.zeamateis.mjson.proxy;

import api.contentpack.common.ContentPack;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonProxy implements IProxy {

    @Override
    public void objectsRegistry(ContentPack contentPackIn) {
        contentPackIn.getBlockList().forEach(ForgeRegistries.BLOCKS::register);
        contentPackIn.getItemList().forEach(ForgeRegistries.ITEMS::register);
    }

    @Override
    public void registerPackFinder(ContentPack contentPackIn) {
    }

}

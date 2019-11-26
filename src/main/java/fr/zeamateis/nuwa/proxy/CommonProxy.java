package fr.zeamateis.nuwa.proxy;

import api.contentpack.common.ContentPack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonProxy implements IProxy {

    @Override
    public void objectsRegistry(ContentPack contentPackIn) {
        contentPackIn.getObjectsList().stream()
                .filter(registryEntry -> registryEntry instanceof Block)
                .map(registryEntry -> ((Block) registryEntry))
                .forEach(ForgeRegistries.BLOCKS::register);

        contentPackIn.getObjectsList().stream()
                .filter(registryEntry -> registryEntry instanceof Item)
                .map(registryEntry -> ((Item) registryEntry))
                .forEach(ForgeRegistries.ITEMS::register);
    }

}

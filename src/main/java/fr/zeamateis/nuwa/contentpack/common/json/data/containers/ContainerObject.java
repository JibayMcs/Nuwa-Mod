package fr.zeamateis.nuwa.contentpack.common.json.data.containers;

import net.minecraft.util.ResourceLocation;

public class ContainerObject {

    private String registryName;

    public ResourceLocation getRegistryName() {
        return new ResourceLocation(registryName);
    }
}

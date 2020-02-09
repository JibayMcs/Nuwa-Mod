package api.contentpack.data;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class Data {

    private final ResourceLocation dataRegistryName;
    private final Class<? extends IData> dataClass;

    private IForgeRegistry forgeRegistryIn;

    public Data(ResourceLocation dataRegistryName, Class<? extends IData> dataClass) {
        this.dataRegistryName = dataRegistryName;
        this.dataClass = dataClass;
    }

    public Data(ResourceLocation dataRegistryName, Class<? extends IData> dataClass, IForgeRegistry forgeRegistryIn) {
        this.dataRegistryName = dataRegistryName;
        this.dataClass = dataClass;
        this.forgeRegistryIn = forgeRegistryIn;
    }

    public ResourceLocation getDataRegistryName() {
        return dataRegistryName;
    }

    public Class<? extends IData> getDataClass() {
        return dataClass;
    }

    public IForgeRegistry getForgeRegistry() {
        return forgeRegistryIn;
    }

}

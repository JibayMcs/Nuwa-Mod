package api.contentpack.data;

import net.minecraftforge.registries.IForgeRegistry;

public class Data implements Comparable<Data> {

    private final Class<? extends IData> dataClass;

    private IForgeRegistry forgeRegistryIn;

    public Data(Class<? extends IData> dataClass) {
        this.dataClass = dataClass;
    }

    public Data(Class<? extends IData> dataClass, IForgeRegistry forgeRegistryIn) {
        this.dataClass = dataClass;
        this.forgeRegistryIn = forgeRegistryIn;
    }

    public Class<? extends IData> getDataClass() {
        return dataClass;
    }

    public IForgeRegistry getForgeRegistry() {
        return forgeRegistryIn;
    }

    @Override
    public int compareTo(Data o) {
        return 0;
    }
}

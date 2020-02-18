package api.contentpack.data;

import net.minecraftforge.registries.IForgeRegistry;

/**
 * Define a Class that represent the datas to parse on reading content pack
 * Assign an {@link IForgeRegistry} to put parsed datas objects in registries
 *
 * @author ZeAmateis
 */
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

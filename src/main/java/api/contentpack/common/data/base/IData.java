package api.contentpack.common.data.base;

import api.contentpack.common.PackManager;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.LinkedList;

public interface IData {

    void parseData(PackManager packManager);

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    LinkedList<? extends IForgeRegistryEntry> getObjectsList();

}

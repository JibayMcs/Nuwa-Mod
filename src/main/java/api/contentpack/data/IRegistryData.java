package api.contentpack.data;

import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.LinkedList;

public interface IRegistryData extends IData {


    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    LinkedList<? extends IForgeRegistryEntry> getObjectsList();

}

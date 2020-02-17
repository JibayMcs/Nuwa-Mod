package api.contentpack.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.LinkedList;

public interface IData {

    /**
     * Use {@link PackManager}
     * instance to parse datas from Content Pack zip file
     *
     * @param packManagerIn
     */
    void parseData(PackManager packManagerIn, ContentPack contentPackIn);


    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    LinkedList<? extends IForgeRegistryEntry> getObjectsList();

}

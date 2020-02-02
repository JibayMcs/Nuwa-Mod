package api.contentpack.common;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public interface IPackData {


    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return String
     */
    String getEntryFolder();

    /**
     * Use {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param contentPackIn
     * @param zipFileIn
     * @param readerIn
     */
    void parseData(PackManager packManagerIn, ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn);


    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    LinkedList<? extends IForgeRegistryEntry> getObjectsList();


    /**
     * Link {@link IPackData#getObjectsList()} to the correct Forge Registry
     *
     * @return IForgeRegistry
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    IForgeRegistry getObjectsRegistry();

}

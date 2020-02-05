package api.contentpack.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public interface IPackData extends IData {


    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return String
     */
    String getEntryFolder();

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param packManagerIn
     * @param contentPackIn
     * @param zipFileIn
     * @param readerIn
     */
    void parseData(PackManager packManagerIn, ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn);

    /**
     * Use {@link PackManager}
     * instance to parse datas from Content Pack zip file
     *
     * @param packManagerIn
     */
    @Override
    default void parseData(PackManager packManagerIn) {

    }


    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    LinkedList<? extends IForgeRegistryEntry> getObjectsList();
}

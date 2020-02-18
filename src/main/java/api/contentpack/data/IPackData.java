package api.contentpack.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;

import java.io.InputStreamReader;
import java.util.zip.ZipFile;

/**
 * Representing abstract data to parse inside Content pack,
 * but with a specific entry path<br>
 * Especially for parsed data from json
 *
 * @author ZeAmateis
 */
public interface IPackData extends IData {


    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return the full entry folder path
     */
    String getEntryFolder();

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack
     *
     * @param packManagerIn The {@link PackManager} instance
     * @param contentPackIn The {@link ContentPack} instance
     * @param readerIn      The {@link InputStreamReader} instance
     */
    void parseData(PackManager packManagerIn, ContentPack contentPackIn, InputStreamReader readerIn);

    /**
     * Use {@link PackManager}
     * instance to parse datas from Content Pack
     *
     * @param packManagerIn The {@link PackManager} instance
     * @param contentPackIn The {@link ContentPack} instance
     */
    @Override
    default void parseData(PackManager packManagerIn, ContentPack contentPackIn) {

    }
}

package api.contentpack.common.data.base;

import api.contentpack.common.ContentPack;
import api.contentpack.common.PackManager;

import java.io.InputStreamReader;
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

    @Override
    default void parseData(PackManager packManager) {

    }
}
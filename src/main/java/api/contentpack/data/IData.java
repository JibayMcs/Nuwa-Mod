package api.contentpack.data;

import api.contentpack.PackManager;

public interface IData {

    /**
     * Use {@link PackManager}
     * instance to parse datas from Content Pack zip file
     *
     * @param packManagerIn
     */
    void parseData(PackManager packManagerIn);

}

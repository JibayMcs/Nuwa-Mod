package api.contentpack.common.json.datas;

import java.util.ArrayList;
import java.util.List;

public class WhitelistObject {

    private List<String> blocks = new ArrayList<>();
    private List<String> items = new ArrayList<>();

    public List<String> getBlocks() {
        return blocks;
    }

    public List<String> getItems() {
        return items;
    }
}

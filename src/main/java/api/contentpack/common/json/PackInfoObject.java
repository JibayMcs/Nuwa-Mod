package api.contentpack.common.json;

import java.util.List;

public class PackInfoObject {

    private String packName;

    private String namespace;

    private String version;

    private List<String> description, authors, credits;

    private String license;

    private transient int totalBlocks, totalItems;


    public String getPackName() {
        return packName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getVersion() {
        return version;
    }

    public List<String> getDescription() {
        return description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getCredits() {
        return credits;
    }

    public String getLicense() {
        return license;
    }

    @Override
    public String toString() {
        return "PackInfoObject{" +
                "packName='" + packName + '\'' +
                ", namespace='" + namespace + '\'' +
                ", version='" + version + '\'' +
                ", description=" + description +
                ", authors=" + authors +
                ", credits=" + credits +
                ", license='" + license + '\'' +
                '}';
    }


    public int getTotalBlocks() {
        return totalBlocks;
    }

    void setTotalBlocks(int totalBlocks) {
        this.totalBlocks = totalBlocks;
    }

    public int getTotalItems() {
        return totalItems;
    }

    void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

}

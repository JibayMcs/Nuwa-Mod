package api.contentpack.common.json;

import java.util.List;

public class PackInfoObject {

    private String packName;

    private String namespace;

    private String version;

    private int nuwaDataVersion;

    private List<String> description, authors, credits;

    private String license;

    private transient int totalBlocks, totalItems;

    public PackInfoObject(String packName, String namespace, String version, int nuwaDataVersion, List<String> description, List<String> authors, List<String> credits, String license) {
        this.packName = packName;
        this.namespace = namespace;
        this.version = version;
        this.nuwaDataVersion = nuwaDataVersion;
        this.description = description;
        this.authors = authors;
        this.credits = credits;
        this.license = license;
    }

    public String getPackName() {
        return packName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getVersion() {
        return version;
    }

    public int getNuwaDataVersion() {
        return nuwaDataVersion;
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
                ", nuwaDataVersion=" + nuwaDataVersion +
                ", description=" + description +
                ", authors=" + authors +
                ", credits=" + credits +
                ", license='" + license + '\'' +
                ", totalBlocks=" + totalBlocks +
                ", totalItems=" + totalItems +
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

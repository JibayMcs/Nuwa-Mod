package api.contentpack.json;

import java.util.List;

/**
 * Representation of the "content.pack" file inside each Content Pack
 *
 * @author ZeAmateis
 */
public class PackInfoObject {

    /**
     * The name of the content pack
     * Accepting upper cases
     */
    private String packName;

    /**
     * The name of the content pack without any
     * upper cases, spaces, special caracters,etc
     * Used to register content pack objects in Forge Registry
     */
    private String namespace;

    /**
     * String version of the content pack
     */
    private String version;

    /**
     * Integer version of the parsing system of Nuwa
     * Updated if reading jsons way change
     */
    private int nuwaDataVersion;

    /**
     * String list to describe, define authors and credits your content pack
     */
    private List<String> description, authors, credits;

    /**
     * Licence of the content pack
     */
    private String license;

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
                '}';
    }

}

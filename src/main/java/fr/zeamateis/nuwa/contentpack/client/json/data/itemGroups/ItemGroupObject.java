package fr.zeamateis.nuwa.contentpack.client.json.data.itemGroups;

public class ItemGroupObject {

    private String registryName, itemIcon, backgroundImage;
    private boolean noTitle, hasSearchBar, hasScrollBar;


    public String getRegistryName() {
        return registryName;
    }

    public String getItemIcon() {
        return itemIcon;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public boolean hasNoTitle() {
        return noTitle;
    }

    public boolean hasSearchBar() {
        return hasSearchBar;
    }

    public boolean hasScrollBar() {
        return hasScrollBar;
    }
}

package fr.zeamateis.nuwa.contentpack.client.json.data.itemGroups;

import net.minecraft.util.ResourceLocation;

public class ItemGroupObject {

    private String registryName, itemIcon, backgroundImage;
    private boolean noTitle, hasSearchBar, hasScrollBar;


    public ResourceLocation getRegistryName() {
        return new ResourceLocation(registryName);
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

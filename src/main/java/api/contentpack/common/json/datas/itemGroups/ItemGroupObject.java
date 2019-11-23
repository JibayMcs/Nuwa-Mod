package api.contentpack.common.json.datas.itemGroups;

import net.minecraft.util.ResourceLocation;

public class ItemGroupObject {

    private String id, icon, backgroundImage;
    private boolean noTitle, hasSearchBar, hasScrollBar;

    public ResourceLocation getId() {
        return new ResourceLocation(id);
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation(icon);
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

    public String getBackgroundImage() {
        return backgroundImage;
    }
}

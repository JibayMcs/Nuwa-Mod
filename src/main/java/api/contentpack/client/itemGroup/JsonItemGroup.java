package api.contentpack.client.itemGroup;

import net.minecraft.item.ItemGroup;

public abstract class JsonItemGroup extends ItemGroup {

    private boolean hasSearchBar, hasScrollBar;
    private String backgroundImage;

    protected JsonItemGroup(String label, boolean hasNoTitle, boolean hasSearchBar, boolean hasScrollBar, String backgroundImage) {
        super(label);
        if (hasNoTitle) {
            this.setNoTitle();
        }
        this.hasSearchBar = hasSearchBar;
        this.hasScrollBar = hasScrollBar;
        this.backgroundImage = backgroundImage;
    }

    @Override
    public boolean hasSearchBar() {
        return this.hasSearchBar;
    }

    @Override
    public String getBackgroundImageName() {
        return this.hasSearchBar ? "item_search.png" : backgroundImage != null ? backgroundImage : super.getBackgroundImageName();
    }

    @Override
    public boolean hasScrollbar() {
        return this.hasScrollBar;
    }

}

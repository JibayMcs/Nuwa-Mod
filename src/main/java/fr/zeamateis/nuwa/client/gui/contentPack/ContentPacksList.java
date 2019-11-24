package fr.zeamateis.nuwa.client.gui.contentPack;

import api.contentpack.common.ContentPack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;

public class ContentPacksList extends ExtendedList<ContentPacksList.PackEntry> {
    private final int listWidth;
    private ContentPacksScreen parent;

    ContentPacksList(ContentPacksScreen parent, int listWidth) {
        super(parent.getMinecraftInstance(), listWidth, parent.height, 65, parent.height - 40, parent.getFontRenderer().FONT_HEIGHT * 2 + 16);
        this.parent = parent;
        this.listWidth = listWidth;
        this.refreshList();
    }

    private static String stripControlCodes(String value) {
        return net.minecraft.util.StringUtils.stripControlCodes(value);
    }

    @Override
    protected int getScrollbarPosition() {
        return this.listWidth;
    }

    @Override
    public int getRowWidth() {
        return this.listWidth;
    }

    void refreshList() {
        this.clearEntries();
        parent.buildModList(this::addEntry, pack -> new PackEntry(pack, this.parent));
    }

    @Override
    protected void renderBackground() {
        this.parent.renderBackground();
    }

    public class PackEntry extends ExtendedList.AbstractListEntry<PackEntry> {
        private final ContentPack contentPack;
        private final ContentPacksScreen parent;
        private ResourceLocation packIcon = null;
        private boolean hasPackIcon;

        PackEntry(ContentPack info, ContentPacksScreen parent) {
            this.contentPack = info;
            this.parent = parent;
        }


        @Override
        public void render(int entryIdx, int top, int left, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean p_194999_5_, float partialTicks) {
            String name = stripControlCodes(contentPack.getPackName());
            String version = stripControlCodes(contentPack.getVersion());
            String zipSize = stripControlCodes(FileUtils.byteCountToDisplaySize(contentPack.getZipFileSize()));
            FontRenderer font = this.parent.getFontRenderer();
            font.drawString(font.trimStringToWidth(name, listWidth), left + (this.hasPackIcon ? 32 : 3), top + 2, 0xFFFFFF);
            font.drawString(font.trimStringToWidth(version, listWidth), left + (this.hasPackIcon ? 32 : 3), top + 2 + font.FONT_HEIGHT, 0x666666);

            font.drawString(font.trimStringToWidth(zipSize, listWidth), left + (this.hasPackIcon ? 32 : 3), top + 12 + font.FONT_HEIGHT, 0x666666);

            bindPackIcon();
            if (hasPackIcon) {
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                AbstractGui.blit(left, top, 0.0F, 0.0F, 30, 30, 30, 30);
            }
        }

        private void bindPackIcon() {
            if (this.packIcon == null) {
                if (this.getContentPack().getPackIcon() != null) {
                    this.packIcon = minecraft.getTextureManager().getDynamicTextureLocation("contentpackicon", new DynamicTexture(this.getContentPack().getPackIcon()));
                    this.hasPackIcon = true;
                } else {
                    this.packIcon = new ResourceLocation("textures/misc/unknown_pack.png");
                    this.hasPackIcon = false;
                }
            }
            minecraft.getTextureManager().bindTexture(this.packIcon);
        }

        @Override
        public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
            parent.setSelected(this);
            ContentPacksList.this.setSelected(this);
            return false;
        }

        ContentPack getContentPack() {
            return contentPack;
        }
    }
}
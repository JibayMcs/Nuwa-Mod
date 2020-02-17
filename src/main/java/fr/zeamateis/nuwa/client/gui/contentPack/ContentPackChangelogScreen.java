package fr.zeamateis.nuwa.client.gui.contentPack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ScrollPanel;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContentPackChangelogScreen extends Screen {

    private Gson gson = new GsonBuilder().create();
    private ChangelogObject changelogObject;
    private LogsList logsList;
    private NewsPanel newsPanel;
    private boolean changelogFetched;

    public ContentPackChangelogScreen() {
        super(new TranslationTextComponent("nuwa.screen.changelog.title"));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();

        if (this.changelogFetched) {
            if (this.logsList != null) this.logsList.render(mouseX, mouseY, partialTicks);
            if (this.newsPanel != null) this.newsPanel.render(mouseX, mouseY, partialTicks);
        } else {
            this.drawCenteredString(this.font, I18n.format("nuwa.screen.changelog.fetchError"), this.width / 2, this.height / 2, 0xFF0000);
        }
        super.render(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 10, -1);
        this.drawString(this.font, I18n.format("nuwa.screen.changelog.version"), 15, 10, -1);
    }

    @Override
    protected void init() {
        super.init();
        try {
            URL url = new URL("https://files.leviathan-studio.com/amateis2/Nuwa/changelogs/changelogs.json");
            BufferedReader read = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            this.changelogObject = gson.fromJson(read, ChangelogObject.class);

            this.logsList = new LogsList(this.changelogObject);
            this.children.add(this.logsList);

            this.newsPanel = new NewsPanel(this.minecraft, width, this.height - 15, 25);
            this.children.add(this.newsPanel);
            this.changelogFetched = true;
        } catch (IOException ex) {
            this.changelogFetched = false;
        }
    }

    public static class ChangelogObject {

        private List<ReleaseObject> unreleased;
        private List<ReleaseObject> releases;

        public List<ReleaseObject> getUnreleased() {
            return unreleased;
        }

        public List<ReleaseObject> getReleases() {
            return releases;
        }

        enum ReleaseType {
            UNRELEASED("UR"),
            RELEASES("R");

            private String type;

            ReleaseType(String type) {
                this.type = type;
            }

            public String get() {
                return type;
            }
        }

        public class ReleaseObject {
            private String version;
            private List<String> news;

            public String getVersion() {
                return version;
            }

            public List<String> getNews() {
                return news;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    class LogsList extends ExtendedList<LogsList.LogEntry> {

        private final ChangelogObject changelogObject;

        private LogsList(ChangelogObject changelogObjectIn) {
            super(ContentPackChangelogScreen.this.minecraft, 70, ContentPackChangelogScreen.this.height - 15, 25, ContentPackChangelogScreen.this.height, 18);
            this.changelogObject = changelogObjectIn;

            Collections.reverse(this.changelogObject.getReleases());
            Collections.reverse(this.changelogObject.getUnreleased());

            this.changelogObject.getReleases().forEach(releaseObject -> {
                this.addEntry(new LogEntry(releaseObject, ChangelogObject.ReleaseType.RELEASES));
            });

            this.changelogObject.getUnreleased().forEach(unReleaseObject -> {
                this.addEntry(new LogEntry(unReleaseObject, ChangelogObject.ReleaseType.UNRELEASED));
            });
        }

        @Override
        protected int getScrollbarPosition() {
            return 70;
        }

        @Override
        public int getRowWidth() {
            return 68;
        }


        @Override
        protected boolean isFocused() {
            return ContentPackChangelogScreen.this.getFocused() == this;
        }

        @Override
        public void setSelected(@Nullable LogEntry entryIn) {
            super.setSelected(entryIn);
            if (entryIn != null) {
                ContentPackChangelogScreen.this.newsPanel.setInfo(entryIn.releaseObject.news);
            }

        }

        @Override
        protected void moveSelection(int p_moveSelection_1_) {
            super.moveSelection(p_moveSelection_1_);
        }

        @OnlyIn(Dist.CLIENT)
        class LogEntry extends ExtendedList.AbstractListEntry<LogEntry> {
            private final ChangelogObject.ReleaseObject releaseObject;
            private final ChangelogObject.ReleaseType releaseType;

            public LogEntry(ChangelogObject.ReleaseObject releaseObjectIn, ChangelogObject.ReleaseType releaseTypeIn) {
                this.releaseObject = releaseObjectIn;
                this.releaseType = releaseTypeIn;
            }

            @Override
            public void render(int p_render_1_, int p_render_2_, int p_render_3_, int p_render_4_, int p_render_5_, int p_render_6_, int p_render_7_, boolean p_render_8_, float p_render_9_) {
                switch (this.releaseType) {
                    case RELEASES:
                        LogsList.this.drawCenteredString(ContentPackChangelogScreen.this.font, new StringTextComponent(this.releaseType.get()).applyTextStyle(TextFormatting.ITALIC).applyTextStyle(TextFormatting.BOLD).getFormattedText(), 12, p_render_2_ + 2, 5635925);
                        break;
                    case UNRELEASED:
                        LogsList.this.drawCenteredString(ContentPackChangelogScreen.this.font, new StringTextComponent(this.releaseType.get()).applyTextStyle(TextFormatting.BOLD).getFormattedText(), 12, p_render_2_ + 2, 16733525);
                        break;
                }
                LogsList.this.drawCenteredString(ContentPackChangelogScreen.this.font, this.releaseObject.version, width / 2, p_render_2_ + 2, 16777215);
            }

            @Override
            public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
                if (p_mouseClicked_5_ == 0) {
                    LogsList.this.setSelected(this);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean isMouseOver(double p_isMouseOver_1_, double p_isMouseOver_3_) {
                System.out.println("YAY");
                return super.isMouseOver(p_isMouseOver_1_, p_isMouseOver_3_);
            }
        }
    }

    class NewsPanel extends ScrollPanel {
        private List<ITextComponent> lines = Collections.emptyList();

        NewsPanel(Minecraft mcIn, int widthIn, int heightIn, int topIn) {
            super(mcIn, widthIn, heightIn, topIn, ContentPackChangelogScreen.this.logsList.getLeft() + 10);
        }

        void setInfo(List<String> lines) {
            this.lines = resizeContent(lines);
        }

        void clearInfo() {
            this.lines = Collections.emptyList();
        }

        private List<ITextComponent> resizeContent(List<String> lines) {
            List<ITextComponent> ret = new ArrayList<ITextComponent>();
            for (String line : lines) {
                if (line == null) {
                    ret.add(null);
                    continue;
                }
                ret.add(null);

                ITextComponent chat = ForgeHooks.newChatWithLinks(line, false);
                int maxTextLength = this.width - 100;
                if (maxTextLength >= 0) {
                    if (chat.getFormattedText().startsWith("[+]"))
                        chat.applyTextStyle(TextFormatting.GREEN);
                    else if (chat.getFormattedText().startsWith("[~]"))
                        chat.applyTextStyle(TextFormatting.GOLD);
                    else if (chat.getFormattedText().startsWith("[-]"))
                        chat.applyTextStyle(TextFormatting.RED);
                    ret.addAll(RenderComponentsUtil.splitText(chat, maxTextLength, ContentPackChangelogScreen.this.font, false, true));
                }
            }
            return ret;
        }

        @Override
        public int getContentHeight() {
            int height = 50;
            height += (lines.size() * font.FONT_HEIGHT);
            if (height < this.bottom - this.top - 8) {
                height = this.bottom - this.top - 8;
            }
            return height;
        }

        @Override
        protected int getScrollAmount() {
            return font.FONT_HEIGHT * 3;
        }

        @Override
        protected void drawPanel(int entryRight, int relativeY, Tessellator tess, int mouseX, int mouseY) {
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i) != null) {
                    GlStateManager.enableBlend();
                    ContentPackChangelogScreen.this.font.drawSplitString(lines.get(i).getFormattedText(), left + 4, relativeY, this.width - 50, -1);
                    GlStateManager.disableAlphaTest();
                    GlStateManager.disableBlend();
                }
                relativeY += font.FONT_HEIGHT;
            }

            final ITextComponent component = findTextLine(mouseX, mouseY);
            if (component != null) {
            }
        }

        private ITextComponent findTextLine(final int mouseX, final int mouseY) {
            double offset = (mouseY - top) + border + scrollDistance + 1;

            if (offset <= 0) {
                return null;
            }

            int lineIdx = (int) (offset / font.FONT_HEIGHT);
            if (lineIdx >= lines.size() || lineIdx < 1) {
                return null;
            }

            ITextComponent line = lines.get(lineIdx - 1);
            if (line != null) {
                int k = left + border;
                for (ITextComponent part : line) {
                    if (!(part instanceof StringTextComponent)) {
                        continue;
                    }
                    k += ContentPackChangelogScreen.this.font.getStringWidth(((StringTextComponent) part).getText());
                    if (k >= mouseX) {
                        return part;
                    }
                }
            }
            return null;
        }

        @Override
        protected void drawBackground() {
        }
    }
}

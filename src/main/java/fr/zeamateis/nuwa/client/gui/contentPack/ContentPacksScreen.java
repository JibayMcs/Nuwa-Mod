package fr.zeamateis.nuwa.client.gui.contentPack;

import api.contentpack.common.ContentPack;
import com.mojang.blaze3d.platform.GlStateManager;
import fr.zeamateis.nuwa.Constant;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.gui.ScrollPanel;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Size2i;
import net.minecraftforge.fml.loading.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ContentPacksScreen extends Screen {
    private static final Logger LOGGER = LogManager.getLogger();
    private final List<ContentPack> unsortedMods;
    private Screen mainMenu;
    private ContentPacksList contentPacksList;
    private InfoPanel infoPanel;
    private ContentPacksList.PackEntry selected = null;
    private int listWidth;
    private List<ContentPack> contentPacks = NuwaMod.getPackManager().getPacks();
    private int buttonMargin = 1;
    private int numButtons = SortType.values().length;
    private String lastFilterText = "";
    private TextFieldWidget search;
    private boolean sorted = false;
    private SortType sortType = SortType.NORMAL;
    private boolean hasLicenseIcon;

    /**
     * @param mainMenu
     */
    public ContentPacksScreen(Screen mainMenu) {
        super(new StringTextComponent(""));
        this.mainMenu = mainMenu;
        this.unsortedMods = Collections.unmodifiableList(this.contentPacks);
    }

    private static String stripControlCodes(String value) {
        return net.minecraft.util.StringUtils.stripControlCodes(value);
    }

    @Override
    public void init() {
        for (ContentPack pack : contentPacks) {
            listWidth = Math.max(listWidth, getFontRenderer().getStringWidth(pack.getPackName()) + 30);
            listWidth = Math.max(listWidth, getFontRenderer().getStringWidth(pack.getVersion()) + 5);
        }
        listWidth = Math.max(Math.min(listWidth, width / 3), 150);
        listWidth += listWidth % numButtons != 0 ? (numButtons - listWidth % numButtons) : 0;
        this.contentPacksList = new ContentPacksList(this, this.width / 2 - 25);
        this.contentPacksList.setLeftPos(6);


        int modInfoWidth = this.width - this.listWidth - 20;
        this.infoPanel = new InfoPanel(this.minecraft, modInfoWidth, this.height - 30, 10);

        int doneButtonWidth = Math.min(modInfoWidth, 200);
        this.addButton(new Button(((contentPacksList.getWidth() + 8 + this.width - doneButtonWidth) / 2), this.height - 24, doneButtonWidth, 20,
                I18n.format("gui.done"), b -> ContentPacksScreen.this.minecraft.displayGuiScreen(ContentPacksScreen.this.mainMenu)));
        this.addButton(new Button(6, this.height - 24, this.listWidth - 25, 20,
                I18n.format("nuwa.screen.label.openFolder"), b -> Util.getOSType().openFile(Constant.MODELS_PACK_DIR)));


        this.addButton(new ContentPackButton(true, listWidth - 14, this.height - 24, 20, 20, 20, 0, 20, 64, 64, onPress -> {
            ForgeHooksClient.refreshResources(this.minecraft);
        }));

        search = new TextFieldWidget(getFontRenderer(), 8, 20, this.width / 2 - 28, 14, I18n.format("fml.menu.mods.search"));
        children.add(search);
        children.add(contentPacksList);
        children.add(infoPanel);
        search.setFocused2(false);
        search.setCanLoseFocus(true);

        final int width = listWidth / numButtons;
        int x = 25, y = 40;
        addButton(SortType.NORMAL.button = new Button(x, y, width - buttonMargin, 20, SortType.NORMAL.getButtonText(), b -> resortMods(SortType.NORMAL)));
        x += width + buttonMargin;
        addButton(SortType.A_TO_Z.button = new Button(x, y, width - buttonMargin, 20, SortType.A_TO_Z.getButtonText(), b -> resortMods(SortType.A_TO_Z)));
        x += width + buttonMargin;
        addButton(SortType.Z_TO_A.button = new Button(x, y, width - buttonMargin, 20, SortType.Z_TO_A.getButtonText(), b -> resortMods(SortType.Z_TO_A)));
        resortMods(SortType.NORMAL);
        updateCache();
    }

    @Override
    public void tick() {
        search.tick();
        contentPacksList.setSelected(selected);

        if (!search.getText().equals(lastFilterText)) {
            reloadMods();
            sorted = false;
        }

        if (!sorted) {
            reloadMods();
            contentPacks.sort(sortType);
            contentPacksList.refreshList();
            if (selected != null) {
                selected = contentPacksList.children().stream().filter(e -> e.getContentPack() == selected.getContentPack()).findFirst().orElse(null);
                updateCache();
            }
            sorted = true;
        }
    }

    <T extends ExtendedList.AbstractListEntry<T>> void buildModList(Consumer<T> modListViewConsumer, Function<ContentPack, T> newEntry) {
        contentPacks.forEach(pack -> modListViewConsumer.accept(newEntry.apply(pack)));
    }

    private void reloadMods() {
        this.contentPacks = this.unsortedMods.stream().
                filter(mi -> StringUtils.toLowerCase(stripControlCodes(mi.getPackName())).contains(StringUtils.toLowerCase(search.getText()))).collect(Collectors.toList());
        lastFilterText = search.getText();
    }

    private void resortMods(SortType newSort) {
        this.sortType = newSort;

        for (SortType sort : SortType.values()) {
            if (sort.button != null) {
                sort.button.active = sortType != sort;
            }
        }
        sorted = false;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.contentPacksList.render(mouseX, mouseY, partialTicks);
        if (this.infoPanel != null) {
            this.infoPanel.render(mouseX, mouseY, partialTicks);

            if (selected != null) {
                bindLicenseImage(selected.getContentPack());
                if (hasLicenseIcon) {
                    int licenseWidth = 88 / 2;
                    int licenseHeight = 31 / 2;
                    int licensePosX = width - licenseWidth - 4;
                    int licensePosY = 15;
                    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    AbstractGui.blit(licensePosX, licensePosY, 0.0F, 0.0F, licenseWidth, licenseHeight, licenseWidth, licenseHeight);


                    int j2 = this.width;
                    int k2 = licenseHeight + licensePosY + 5;
                    int i1 = 35;
                    int k = 210;

                    if (j2 + k > width) {
                        j2 -= k + 10;
                    }

                    if (k2 + i1 + 6 > height) {
                        k2 = height - i1 - 6;
                    }
                    final int j1 = new Color(100, 0, 0, 255).getRGB();
                    final int k1 = new Color(200, 136, 132, 255).getRGB();
                    final int f1 = new Color(200, 136, 132, 255).getRGB();

                    if (mouseX > licensePosX && mouseX < licensePosX + licenseWidth && mouseY > licensePosY && mouseY < licensePosY + licenseHeight) {
                        fill(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1);
                        fill(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1);
                        fill(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1);
                        fill(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1);
                        fill(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1);

                        fill(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1);
                        fill(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1);
                        fill(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1);
                        fill(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, f1);

                        font.drawSplitString(I18n.format(String.format("nuwa.license.%s.text", selected.getContentPack().getLicense())), j2, k2, 210, 0xB0A6B0);
                    }
                }
            }
        }

        String text = I18n.format("nuwa.screen.label.search");
        int x = ((contentPacksList.getLeft()) / 2) - (getFontRenderer().getStringWidth(text) / 2);
        getFontRenderer().drawString(text, x, 5, 0xFFFFFF);
        this.search.render(mouseX, mouseY, partialTicks);

        super.render(mouseX, mouseY, partialTicks);

        this.buttons.forEach(buttons -> {
            if (buttons instanceof ContentPackButton) {
                if (buttons.isHovered()) {
                    if (((ContentPackButton) buttons).toolTipText != null) {
                        renderTooltip(Arrays.asList(((ContentPackButton) buttons).toolTipText), mouseX, mouseY);
                    }
                }
            }
        });


    }

    Minecraft getMinecraftInstance() {
        return minecraft;
    }

    FontRenderer getFontRenderer() {
        return font;
    }

    void setSelected(ContentPacksList.PackEntry entry) {
        this.selected = entry == this.selected ? null : entry;
        updateCache();
    }

    private void updateCache() {
        if (selected == null) {
            this.infoPanel.clearInfo();
            return;
        }
        ContentPack selectedContentPack = selected.getContentPack();
        String missingno = "null";

        List<String> lines = new ArrayList<>();

        lines.add(I18n.format("nuwa.infoPanel.label.packVersion", selectedContentPack.getVersion() != null ? selectedContentPack.getVersion() : missingno));
        lines.add(null);
        lines.add(I18n.format("nuwa.infoPanel.label.namespace", selectedContentPack.getNamespace() != null ? selectedContentPack.getNamespace() : missingno));

        if (selectedContentPack.getAuthors() != null) {
            lines.add(null);
            lines.add(I18n.format("nuwa.infoPanel.label.authors", Arrays.toString(selectedContentPack.getAuthors().toArray())));
        }
        if (selectedContentPack.getCredits() != null) {
            lines.add(null);
            lines.add(I18n.format("nuwa.infoPanel.label.credits"));
            lines.add(null);
            selectedContentPack.getCredits().forEach(credits -> lines.add(credits));
        }

        if (selectedContentPack.getDescription() != null) {
            lines.add(null);
            selectedContentPack.getDescription().forEach(descriptions -> lines.add(descriptions));
        }

       /* if (selectedContentPack.getTotalBlocks() > 0) {
            lines.add(null);
            lines.add(null);
            lines.add(String.format("Blocks: " + selectedContentPack.getTotalBlocks()));
        }

        if (selectedContentPack.getTotalItems(true) > 0) {
            lines.add(String.format("Items: " + selectedContentPack.getTotalItems(true)));
        }*/

        infoPanel.setInfo(lines);

    }


    private void bindLicenseImage(ContentPack packInfoObject) {
        ResourceLocation licenceImage;
        if (packInfoObject.getLicense() != null) {
            String formatedLicense = String.format("textures/licenses/%s.png", packInfoObject.getLicense());
            licenceImage = new ResourceLocation(Constant.MODID, formatedLicense);
            this.hasLicenseIcon = true;
        } else {
            licenceImage = new ResourceLocation("textures/misc/unknown_pack.png");
            this.hasLicenseIcon = false;
        }
        assert minecraft != null;
        minecraft.getTextureManager().bindTexture(licenceImage);
    }


    @Override
    public void resize(Minecraft mc, int width, int height) {
        String s = this.search.getText();
        SortType sort = this.sortType;
        ContentPacksList.PackEntry selected = this.selected;
        this.init(mc, width, height);
        this.search.setText(s);
        this.selected = selected;
        if (!this.search.getText().isEmpty()) {
            reloadMods();
        }
        if (sort != SortType.NORMAL) {
            resortMods(sort);
        }
        updateCache();
    }

    private enum SortType implements Comparator<ContentPack> {
        NORMAL,
        A_TO_Z {
            @Override
            protected int compare(String name1, String name2) {
                return name1.compareTo(name2);
            }
        },
        Z_TO_A {
            @Override
            protected int compare(String name1, String name2) {
                return name2.compareTo(name1);
            }
        };

        Button button;

        protected int compare(String name1, String name2) {
            return 0;
        }

        @Override
        public int compare(ContentPack o1, ContentPack o2) {
            String name1 = StringUtils.toLowerCase(stripControlCodes(o1.getPackName()));
            String name2 = StringUtils.toLowerCase(stripControlCodes(o2.getPackName()));
            return compare(name1, name2);
        }

        String getButtonText() {
            return I18n.format("fml.menu.mods." + StringUtils.toLowerCase(name()));
        }
    }

    class InfoPanel extends ScrollPanel {
        private ResourceLocation logoPath;
        private Size2i logoDims = new Size2i(0, 0);
        private List<ITextComponent> lines = Collections.emptyList();

        InfoPanel(Minecraft mcIn, int widthIn, int heightIn, int topIn) {
            super(mcIn, widthIn, heightIn, topIn, contentPacksList.getLeft() + 10);
        }

        void setInfo(List<String> lines, ResourceLocation logoPath, Size2i logoDims) {
            this.logoPath = logoPath;
            this.logoDims = logoDims;
            this.lines = resizeContent(lines);
        }

        void setInfo(List<String> lines) {
            this.lines = resizeContent(lines);
        }

        void clearInfo() {
            this.logoPath = null;
            this.logoDims = new Size2i(0, 0);
            this.lines = Collections.emptyList();
        }

        private List<ITextComponent> resizeContent(List<String> lines) {
            List<ITextComponent> ret = new ArrayList<ITextComponent>();
            for (String line : lines) {
                if (line == null) {
                    ret.add(null);
                    continue;
                }

                ITextComponent chat = ForgeHooks.newChatWithLinks(line, false);
                int maxTextLength = this.width - 12;
                if (maxTextLength >= 0) {
                    ret.addAll(RenderComponentsUtil.splitText(chat, maxTextLength, ContentPacksScreen.this.font, false, true));
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
            for (ITextComponent line : lines) {
                if (line != null) {
                    GlStateManager.enableBlend();
                    ContentPacksScreen.this.font.drawSplitString(line.getFormattedText(), left + 4, relativeY, this.width - 50, 0xFFFFFF);
                    GlStateManager.disableAlphaTest();
                    GlStateManager.disableBlend();
                }
                relativeY += font.FONT_HEIGHT;
            }

            final ITextComponent component = findTextLine(mouseX, mouseY);
            if (component != null) {
                ContentPacksScreen.this.renderComponentHoverEffect(component, mouseX, mouseY);
            }
        }

        private ITextComponent findTextLine(final int mouseX, final int mouseY) {
            double offset = (mouseY - top) + border + scrollDistance + 1;
            if (logoPath != null) {
                offset -= 50;
            }
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
                    k += ContentPacksScreen.this.font.getStringWidth(((StringTextComponent) part).getText());
                    if (k >= mouseX) {
                        return part;
                    }
                }
            }
            return null;
        }

        @Override
        public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
            final ITextComponent component = findTextLine((int) mouseX, (int) mouseY);
            if (component != null) {
                ContentPacksScreen.this.handleComponentClicked(component);
                return true;
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        protected void drawBackground() {
        }
    }
}
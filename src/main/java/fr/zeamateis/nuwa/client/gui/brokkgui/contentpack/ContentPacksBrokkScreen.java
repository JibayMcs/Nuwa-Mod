package fr.zeamateis.nuwa.client.gui.brokkgui.contentpack;

import fr.zeamateis.nuwa.NuwaMod;
import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.IJsonBlock;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.base.IJsonItem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.registries.ForgeRegistries;
import net.voxelindustry.brokkgui.data.RectAlignment;
import net.voxelindustry.brokkgui.data.RectBox;
import net.voxelindustry.brokkgui.data.RelativeBindingHelper;
import net.voxelindustry.brokkgui.element.GuiLabel;
import net.voxelindustry.brokkgui.element.GuiListView;
import net.voxelindustry.brokkgui.gui.BrokkGuiScreen;
import net.voxelindustry.brokkgui.paint.Color;
import net.voxelindustry.brokkgui.panel.GuiAbsolutePane;
import net.voxelindustry.brokkgui.panel.GuiRelativePane;
import net.voxelindustry.brokkgui.shape.Rectangle;
import net.voxelindustry.brokkgui.wrapper.elements.ItemStackView;
import org.apache.commons.io.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContentPacksBrokkScreen extends BrokkGuiScreen {

    private final Minecraft minecraft;
    private final PackListPane packListPane;
    private final PackInfoPane packInfoPane;
    private final ContentPackObjectPane contentPackObjectPane;

    public ContentPacksBrokkScreen(Minecraft minecraft) {
        super(0.5f, 0.5f, Minecraft.getInstance().mainWindow.getScaledWidth(), Minecraft.getInstance().mainWindow.getScaledHeight());
        this.minecraft = minecraft;

        GuiRelativePane mainPane = new GuiRelativePane();
        this.setMainPanel(mainPane);
        this.getMainPanel().setID("mainpanel");

        this.packListPane = new PackListPane();
        mainPane.addChild(this.packListPane, 0.22f, 0.5f);

        this.packInfoPane = new PackInfoPane();
        mainPane.addChild(this.packInfoPane, 0.8f, 0.5f);

        contentPackObjectPane = new ContentPackObjectPane();
        mainPane.addChild(this.contentPackObjectPane, 0.8f, 0.8f);

        this.addStylesheet("/assets/nuwa/screen/css/demo.css");
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    static class ContentPackObjectPane extends GuiRelativePane {

        private List<Item> objects = new ArrayList<>();

        public ContentPackObjectPane() {
            this.setBackgroundColor(Color.GRAY);
            this.setSize(128, 128);

            GuiAbsolutePane mainPanel = new GuiAbsolutePane();
            mainPanel.setWidthRatio(1);
            mainPanel.setHeightRatio(1);
            this.addChild(mainPanel);
            RelativeBindingHelper.bindToPos(mainPanel, this);


            List<Item> items = ForgeRegistries.BLOCKS.getEntries()
                    .stream()
                    .map(Map.Entry::getValue)
                    .filter(block -> block instanceof IJsonBlock)
                    //.filter(block -> block.getRegistryName().getNamespace().equals(contentPackNamespace))
                    .map(Block::asItem).collect(Collectors.toList());

            items.addAll(ForgeRegistries.ITEMS.getEntries().stream()
                    .map(Map.Entry::getValue)
                    .filter(item -> item instanceof IJsonItem)
                    //.filter(block -> block.getRegistryName().getNamespace().equals(contentPackNamespace))
                    .map(Item::asItem).collect(Collectors.toList()));

            System.out.println(items.size());

            for (int i = 0; i < items.size(); i++)
                for (int j = 0; j < 3; ++j) {
                    for (int k = 0; k < 7; ++k) {
                        ItemStackView blockView = new ItemStackView(new ItemStack(items.get(i), 1));
                        blockView.setItemTooltip(true);
                        blockView.setWidth(18);
                        blockView.setHeight(18);
                        blockView.setzLevel(301);
                        mainPanel.addChild(blockView, 8 + k * 18, 18 + j * 18);
                    }
                }


            /*if (!objects.isEmpty())
                for (int x = 0; x < objects.size(); x++) {
                    ItemStackView blockView = new ItemStackView(new ItemStack(objects.get(x), 1));
                    blockView.setItemTooltip(true);
                    blockView.setWidth(18);
                    blockView.setHeight(18);
                    this.addChild(blockView, 18 * x, 18);
                }*/
        }
    }

    class PackListPane extends GuiRelativePane {
        public PackListPane() {
            this.setWidth(170);
            this.setHeight(160);
            this.setBackgroundColor(Color.LIGHT_GRAY);

            final GuiListView<GuiAbsolutePane> contentPacksPane = new GuiListView<>();

            int cellHeight = 32;

            contentPacksPane.setSize(getWidth(), getHeight());

            contentPacksPane.setCellHeight(cellHeight);
            contentPacksPane.setCellWidth(getWidth() - 2);

            List<GuiAbsolutePane> packInfoPanes = new ArrayList<>();

            NuwaMod.getPackManager().getPacks().forEach(contentPack -> {
                GuiAbsolutePane packEntryPane = new GuiAbsolutePane();
                packEntryPane.setBackgroundColor(Color.fromHex("#666666"));
                packEntryPane.setWidth(getWidth());

                Rectangle packIcon = new Rectangle();
                if (contentPack.getPackIcon() != null) {
                    packIcon.setSize(30, 30);
                    packIcon.setStyle("background-texture: assets(\"" + Minecraft.getInstance().getTextureManager().getDynamicTextureLocation("contentpackicon", new DynamicTexture(contentPack.getPackIcon())).toString() + "\", 32px, 32px)");
                    packIcon.setyTranslate(1);
                    packIcon.setxTranslate(1);
                    packEntryPane.addChild(packIcon, 0.25F, 0.2F);
                }

                //Pack Name
                GuiLabel packName = new GuiLabel(contentPack.getPackName());
                packName.setWidth(getWidth() - 2);
                packName.setTextAlignment(RectAlignment.LEFT_CENTER);
                packName.setTextPadding(new RectBox(10, contentPack.getPackIcon() != null ? packIcon.getLeftPos() + 32 : 2, 2, 0));

                //Pack Version
                GuiLabel packVersion = new GuiLabel(contentPack.getVersion());
                packVersion.setWidth(getWidth() - 2);
                packVersion.setTextAlignment(RectAlignment.LEFT_CENTER);
                packVersion.setTextPadding(new RectBox(20, contentPack.getPackIcon() != null ? packIcon.getLeftPos() + 32 : 2, 2, 0));

                //Content Pack File Size
                GuiLabel packFileSize = new GuiLabel(FileUtils.byteCountToDisplaySize(contentPack.getZipFileSize()));
                packFileSize.setWidth(getWidth() - 2);
                packFileSize.setTextAlignment(RectAlignment.RIGHT_CENTER);
                packFileSize.setTextPadding(new RectBox(30, contentPack.getPackIcon() != null ? packIcon.getLeftPos() + 32 : 2, 2, 2));

                packEntryPane.addChilds(packName, packVersion, packFileSize);

                packEntryPane.setOnClickEvent(event -> {
                    minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    packInfoPane.packName.setText(contentPack.getPackName());
                    packInfoPane.contentPackNamespace = contentPack.getNamespace();
                });

                packInfoPanes.add(packEntryPane);
            });

            contentPacksPane.setElements(packInfoPanes);
            contentPacksPane.setCellYPadding(2);

            this.addChild(contentPacksPane);
        }
    }

    class PackInfoPane extends GuiRelativePane {

        GuiLabel packName = new GuiLabel();
        private String contentPackNamespace;

        public PackInfoPane() {
            this.setWidth(170);
            this.setHeight(160);
            this.setBackgroundColor(Color.GREEN);

            packName.setWidth(getWidth() - 2);
            packName.setTextAlignment(RectAlignment.LEFT_CENTER);
            packName.setTextPadding(new RectBox(10, 2, 2, 0));

            this.addChild(packName);

            //SubWindow subWindow = new SubWindow(items);
            //this.addChild(subWindow);
        }
    }
}

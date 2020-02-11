package fr.zeamateis.nuwa.client.gui.brokkgui.contentpack;

import api.contentpack.ContentPack;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.SoundEvents;
import net.voxelindustry.brokkgui.animation.transition.FadeTransition;
import net.voxelindustry.brokkgui.data.RectAlignment;
import net.voxelindustry.brokkgui.data.RectBox;
import net.voxelindustry.brokkgui.element.GuiLabel;
import net.voxelindustry.brokkgui.element.GuiListView;
import net.voxelindustry.brokkgui.gui.BrokkGuiScreen;
import net.voxelindustry.brokkgui.paint.Color;
import net.voxelindustry.brokkgui.panel.GuiRelativePane;
import net.voxelindustry.brokkgui.shape.Rectangle;
import org.apache.commons.io.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ContentPacksBrokkScreen extends BrokkGuiScreen {

    private final Minecraft minecraft;
    private final PackListPane packListPane;

    public ContentPacksBrokkScreen(Minecraft minecraft) {
        super(0.5f, 0.5f, Minecraft.getInstance().mainWindow.getScaledWidth(), Minecraft.getInstance().mainWindow.getScaledHeight());
        this.minecraft = minecraft;

        GuiRelativePane mainPane = new GuiRelativePane();
        this.setMainPanel(mainPane);
        this.getMainPanel().setID("mainpanel");

        this.packListPane = new PackListPane();
        mainPane.addChild(this.packListPane, 0.22f, 0.5f);

        this.addStylesheet("/assets/nuwa/screen/css/demo.css");
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    class PackListPane extends GuiRelativePane {
        public PackListPane() {
            this.setWidth(170);
            this.setHeight(160);
            this.setBackgroundColor(Color.LIGHT_GRAY);

            final GuiListView<GuiRelativePane> contentPacksPane = new GuiListView<>();
            AnimatedInfoPane infoPane = new AnimatedInfoPane();
            infoPane.setVisible(false);

            FadeTransition transition = new FadeTransition(infoPane, 2, TimeUnit.SECONDS);
            transition.setMaxCycles(1);
            transition.setReverse(false);
            transition.setStartOpacity(0);
            transition.setEndOpacity(1.0);

            int cellHeight = 32;

            contentPacksPane.setSize(getWidth(), getHeight());

            contentPacksPane.setCellHeight(cellHeight);
            contentPacksPane.setCellWidth(getWidth() - 2);

            List<GuiRelativePane> packInfoPanes = new ArrayList<>();

            NuwaMod.getPackManager().getPacks().forEach(contentPack -> {
                GuiRelativePane packInfoPane = new GuiRelativePane();
                packInfoPane.setBackgroundColor(Color.fromHex("#666666"));
                packInfoPane.setWidth(getWidth());

                Rectangle packIcon = new Rectangle();
                if (contentPack.getPackIcon() != null) {
                    packIcon.setSize(30, 30);
                    packIcon.setStyle("background-texture: assets(\"" + Minecraft.getInstance().getTextureManager().getDynamicTextureLocation("contentpackicon", new DynamicTexture(contentPack.getPackIcon())).toString() + "\", 32px, 32px)");
                    packIcon.setyTranslate(1);
                    packIcon.setxTranslate(1);
                    packInfoPane.addChild(packIcon, 0.25F, 0.2F);
                }

                GuiLabel packName = new GuiLabel(contentPack.getPackName());
                packName.setWidth(getWidth() - 2);
                packName.setTextAlignment(RectAlignment.LEFT_CENTER);
                packName.setTextPadding(new RectBox(10, contentPack.getPackIcon() != null ? packIcon.getLeftPos() + 32 : 2, 2, 0));

                GuiLabel packVersion = new GuiLabel(contentPack.getVersion());
                packVersion.setWidth(getWidth() - 2);
                packVersion.setTextAlignment(RectAlignment.LEFT_CENTER);
                packVersion.setTextPadding(new RectBox(20, contentPack.getPackIcon() != null ? packIcon.getLeftPos() + 32 : 2, 2, 0));

                GuiLabel packFileSize = new GuiLabel(FileUtils.byteCountToDisplaySize(contentPack.getZipFileSize()));
                packFileSize.setWidth(getWidth() - 2);
                packFileSize.setTextAlignment(RectAlignment.RIGHT_CENTER);
                packFileSize.setTextPadding(new RectBox(30, contentPack.getPackIcon() != null ? packIcon.getLeftPos() + 32 : 2, 2, 2));

                packInfoPane.addChilds(packName, packVersion, packFileSize);


                packInfoPane.setOnClickEvent(event -> {
                    minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    System.out.println(contentPack.getPackName());
                    infoPane.setContentPack(contentPack);

                    if (infoPane.getChildrens() != null) {
                        infoPane.getChildrens().clear();
                    } else {
                        infoPane.addChild(packName);
                    }

                    infoPane.setVisible(true);

                    if (transition.isRunning())
                        transition.reset();
                    else {
                        transition.restart();
                    }
                });

                packInfoPanes.add(packInfoPane);
            });

            contentPacksPane.setElements(packInfoPanes);
            contentPacksPane.setCellYPadding(2);

            this.addChild(contentPacksPane);
            this.addChild(infoPane, 1.8f, 0.5f);

        }
    }

    class AnimatedInfoPane extends GuiRelativePane {

        private ContentPack contentPack;

        public AnimatedInfoPane() {
            this.setWidth(170);
            this.setHeight(160);
            this.setBackgroundColor(Color.GREEN);

            if (this.contentPack != null) {
                GuiLabel packName = new GuiLabel(contentPack.getPackName());
                packName.setWidth(getWidth() - 2);
                packName.setTextAlignment(RectAlignment.LEFT_CENTER);
                packName.setTextPadding(new RectBox(10, 2, 2, 0));

                this.addChild(packName);
            }
        }

        public ContentPack getContentPack() {
            return contentPack;
        }

        public void setContentPack(ContentPack contentPack) {
            this.contentPack = contentPack;
        }
    }
}

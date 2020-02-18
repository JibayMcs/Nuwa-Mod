package fr.zeamateis.nuwa.client.gui.contentPack;

import fr.zeamateis.nuwa.Constant;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

/**
 * Custom animated {@link ImageButton}
 *
 * @author ZeAmateis
 */
public class ContentPackButton extends ImageButton {
    private static final ResourceLocation BUTTONS_TEXTURE = new ResourceLocation(Constant.MODID, "textures/gui/buttons.png");

    String toolTipText = I18n.format("nuwa.screen.tooltip.reloadAssets");
    private int animationFrames = 3, animationTicks;
    private boolean isAnimated;

    public ContentPackButton(boolean isAnimated, int p_i51135_1_, int p_i51135_2_, int p_i51135_3_, int p_i51135_4_, int p_i51135_5_, int p_i51135_6_, int p_i51135_7_, int p_i51135_9_, int p_i51135_10_, IPressable p_i51135_11_) {
        super(p_i51135_1_, p_i51135_2_, p_i51135_3_, p_i51135_4_, p_i51135_5_, p_i51135_6_, p_i51135_7_, BUTTONS_TEXTURE, p_i51135_9_, p_i51135_10_, p_i51135_11_);
        this.isAnimated = isAnimated;
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        super.renderButton(p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
        if (this.isAnimated) {
            if (this.isHovered) {
                this.animationTicks++;
                if (this.animationTicks >= 20) {
                    this.animationTicks = 0;
                    this.animationFrames++;
                }
                if (this.animationFrames > 3) {
                    this.animationFrames = 0;
                    this.animationTicks = 0;
                }
            } else {
                this.animationTicks = 0;
                this.animationFrames = 0;
            }
            blit(this.x, this.y, (float) this.animationFrames == 3 ? 20 : 40, (float) this.animationFrames == 3 ? 40 : this.animationFrames * 20, this.width, this.height, 64, 80);
        }
    }

}

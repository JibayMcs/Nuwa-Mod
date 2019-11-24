package fr.zeamateis.nuwa.client.event;

import fr.zeamateis.nuwa.Constant;
import fr.zeamateis.nuwa.client.gui.contentPack.ContentPackButton;
import fr.zeamateis.nuwa.client.gui.contentPack.ContentPacksScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constant.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onInitGuiEvent(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof MainMenuScreen) {
            int width = event.getGui().width;
            int height = event.getGui().height / 4 + (48 * 2) + 12;
            Button contentPackButton = new ContentPackButton(false, width / 2 - 124, height - 12, 20, 20, 0, 0, 20, 64, 64, onPress -> {
                Minecraft.getInstance().displayGuiScreen(new ContentPacksScreen(event.getGui()));
            });
            event.addWidget(contentPackButton);
        }

    }

}

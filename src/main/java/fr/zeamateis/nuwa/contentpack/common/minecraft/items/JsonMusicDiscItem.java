package fr.zeamateis.nuwa.contentpack.common.minecraft.items;

import fr.zeamateis.nuwa.contentpack.common.minecraft.items.base.IJsonItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

public class JsonMusicDiscItem extends MusicDiscItem implements IJsonItem {

    public JsonMusicDiscItem(int comparatorValueIn, SoundEvent soundIn, Properties builder, @Nonnull ResourceLocation registryNameIn) {
        super(comparatorValueIn, soundIn, builder);
        builder.maxStackSize(1);
        RegistryUtil.forceRegistryName(this, registryNameIn);
    }
}

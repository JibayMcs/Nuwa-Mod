package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.contentpack.common.json.data.effects.EffectObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.effect.JsonEffect;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class EffectsData implements IPackData {

    private LinkedList<Effect> effects;

    public EffectsData() {
        this.effects = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return the full entry folder path
     */
    @Override
    public String getEntryFolder() {
        return "objects/effects/";
    }

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *  @param zipFileIn     The {@link ZipFile} instance
     * @param packManagerIn The {@link PackManager} instance
     * @param contentPackIn The {@link ContentPack} instance
     * @param readerIn      The {@link InputStreamReader} instance
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, InputStreamReader readerIn) {
        EffectObject effectObject = packManagerIn.getGson().fromJson(readerIn, EffectObject.class);
        ResourceLocation registryName = new ResourceLocation(contentPackIn.getNamespace(), effectObject.getRegistryName());
        JsonEffect parsedEffect = new JsonEffect(effectObject.getEffectType(), effectObject.getLiquidColor(), registryName, effectObject.isInstant());

        if (effectObject.getPerformEffect() != null) {
            parsedEffect.setProcess(effectObject.getPerformEffect());
        }

        this.effects.add(parsedEffect);
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList} type of {@link IForgeRegistryEntry}
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<Effect> getObjectsList() {
        return this.effects;
    }
}

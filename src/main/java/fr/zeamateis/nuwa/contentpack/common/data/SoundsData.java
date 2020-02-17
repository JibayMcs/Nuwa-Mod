package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.contentpack.common.json.data.sounds.SoundObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class SoundsData implements IPackData {

    private LinkedList<SoundEvent> soundEvents;

    public SoundsData() {
        this.soundEvents = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return the full entry folder path
     */
    @Override
    public String getEntryFolder() {
        return "objects/sounds/";
    }

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param packManagerIn The {@link PackManager} instance
     * @param contentPackIn The {@link ContentPack} instance
     * @param zipFileIn     The {@link ZipFile} instance
     * @param readerIn      The {@link InputStreamReader} instance
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        SoundObject soundObject = packManagerIn.getGson().fromJson(readerIn, SoundObject.class);
        ResourceLocation registryName = new ResourceLocation(contentPackIn.getNamespace(), soundObject.getRegistryName());
        SoundEvent soundEvent = new SoundEvent(registryName);
        RegistryUtil.forceRegistryName(soundEvent, registryName);
        this.soundEvents.add(soundEvent);
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList} type of {@link net.minecraftforge.registries.IForgeRegistryEntry}
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    @Override
    public LinkedList<SoundEvent> getObjectsList() {
        return soundEvents;
    }

}

package api.contentpack.common.data;

import api.contentpack.common.ContentPack;
import api.contentpack.common.PackManager;
import api.contentpack.common.data.base.IPackData;
import api.contentpack.common.json.datas.sounds.SoundObject;
import api.contentpack.common.minecraft.util.RegistryUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

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
     * @return String
     */
    @Override
    public String getEntryFolder() {
        return "objects/sounds/";
    }

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param packManagerIn
     * @param contentPackIn
     * @param zipFileIn
     * @param readerIn
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        SoundObject soundObject = packManagerIn.getGson().fromJson(readerIn, SoundObject.class);
        ResourceLocation registryName = new ResourceLocation(soundObject.getRegistryName());
        SoundEvent soundEvent = new SoundEvent(registryName);
        RegistryUtil.forceRegistryName(soundEvent, registryName);
        this.soundEvents.add(soundEvent);
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<SoundEvent> getObjectsList() {
        return soundEvents;
    }

    /**
     * Link {@link IPackData#getObjectsList()} to the correct Forge Registry
     *
     * @return IForgeRegistry
     * @see ForgeRegistries
     */
    @Override
    public IForgeRegistry<SoundEvent> getObjectsRegistry() {
        return ForgeRegistries.SOUND_EVENTS;
    }
}

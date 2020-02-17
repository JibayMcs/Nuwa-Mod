package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.blocks.BlockEventObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.registries.BlockEventType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class BlockEventData implements IPackData {

    private LinkedList<BlockEventType> blockEventTypes;

    public BlockEventData() {
        this.blockEventTypes = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return the full entry folder path
     */
    @Override
    public String getEntryFolder() {
        return "objects/events/blocks/";
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
        BlockEventObject blockEventObject = packManagerIn.getGson().fromJson(readerIn, BlockEventObject.class);
        ResourceLocation registryName = new ResourceLocation(contentPackIn.getNamespace(), blockEventObject.getRegistryName());
        this.blockEventTypes.add(new BlockEventType(blockEventObject).setRegistryName(registryName));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList} type of {@link IForgeRegistryEntry}
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<BlockEventType> getObjectsList() {
        return this.blockEventTypes;
    }
}

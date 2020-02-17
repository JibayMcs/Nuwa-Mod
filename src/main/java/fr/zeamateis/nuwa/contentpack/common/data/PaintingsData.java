package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.contentpack.common.json.data.paintings.PaintingObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.entity.items.JsonPainting;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class PaintingsData implements IPackData {

    private LinkedList<PaintingType> paintingTypes;

    public PaintingsData() {
        this.paintingTypes = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return String
     */
    @Override
    public String getEntryFolder() {
        return "objects/paintings/";
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
        PaintingObject paintingObject = packManagerIn.getGson().fromJson(readerIn, PaintingObject.class);
        ResourceLocation registryName = new ResourceLocation(contentPackIn.getNamespace(), paintingObject.getRegistryName());
        this.paintingTypes.add(new JsonPainting(paintingObject.getWidth(), paintingObject.getHeight(), registryName));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<PaintingType> getObjectsList() {
        return this.paintingTypes;
    }
}

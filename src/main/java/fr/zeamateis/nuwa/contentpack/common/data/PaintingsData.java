package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.contentpack.common.json.data.paintings.PaintingObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.entity.items.JsonPainting;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.ResourceLocation;

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
     * @return the full entry folder path
     */
    @Override
    public String getEntryFolder() {
        return "objects/paintings/";
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
        PaintingObject paintingObject = packManagerIn.getGson().fromJson(readerIn, PaintingObject.class);
        ResourceLocation registryName = new ResourceLocation(contentPackIn.getNamespace(), paintingObject.getRegistryName());
        this.paintingTypes.add(new JsonPainting(paintingObject.getWidth(), paintingObject.getHeight(), registryName));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList} type of {@link net.minecraftforge.registries.IForgeRegistryEntry}
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    @Override
    public LinkedList<PaintingType> getObjectsList() {
        return this.paintingTypes;
    }
}

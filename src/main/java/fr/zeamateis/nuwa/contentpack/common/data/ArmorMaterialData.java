package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.contentpack.common.json.data.materials.ArmorMaterialObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.registries.ArmorMaterialType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class ArmorMaterialData implements IPackData {

    private final LinkedList<ArmorMaterialType> armorMaterialTypes;

    public ArmorMaterialData() {
        this.armorMaterialTypes = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return the full entry folder path
     */
    @Override
    public String getEntryFolder() {
        return "objects/materials/armor/";
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
        ArmorMaterialObject armorMaterialObject = packManagerIn.getGson().fromJson(readerIn, ArmorMaterialObject.class);
        ResourceLocation registryName = new ResourceLocation(contentPackIn.getNamespace(), armorMaterialObject.getRegistryName());
        armorMaterialTypes.add(new ArmorMaterialType(armorMaterialObject).setRegistryName(registryName));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList}
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<ArmorMaterialType> getObjectsList() {
        return this.armorMaterialTypes;
    }

}

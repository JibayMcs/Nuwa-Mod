package api.contentpack.common.data;

import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.materials.ArmorMaterialObject;
import fr.zeamateis.nuwa.NuwaMod;
import fr.zeamateis.nuwa.common.registries.ArmorMaterialType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

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
     * @return String
     */
    @Override
    public String getEntryFolder() {
        return "objects/materials/armor/";
    }

    /**
     * Use {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param contentPackIn
     * @param zipFileIn
     * @param readerIn
     */
    @Override
    public void parseData(ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        ArmorMaterialObject armorMaterialObject = PackManager.GSON.fromJson(readerIn, ArmorMaterialObject.class);
        armorMaterialTypes.add(new ArmorMaterialType(armorMaterialObject));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<ArmorMaterialType> getObjectsList() {
        return this.armorMaterialTypes;
    }

    /**
     * Link {@link IPackData#getObjectsList()} to the correct Forge Registry
     *
     * @return IForgeRegistry
     * @see ForgeRegistries
     */
    @Override
    public IForgeRegistry<ArmorMaterialType> getObjectsRegistry() {
        return NuwaMod.Registries.ARMOR_MATERIAL;
    }
}

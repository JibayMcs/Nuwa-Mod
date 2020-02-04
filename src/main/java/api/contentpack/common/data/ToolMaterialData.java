package api.contentpack.common.data;

import api.contentpack.common.ContentPack;
import api.contentpack.common.PackManager;
import api.contentpack.common.data.base.IPackData;
import api.contentpack.common.json.datas.materials.ToolMaterialObject;
import api.contentpack.common.minecraft.registries.ToolMaterialType;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class ToolMaterialData implements IPackData {

    private final LinkedList<ToolMaterialType> toolMaterialTypes;

    public ToolMaterialData() {
        this.toolMaterialTypes = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return String
     */
    @Override
    public String getEntryFolder() {
        return "objects/materials/tools/";
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
        ToolMaterialObject toolMaterialObject = packManagerIn.getGson().fromJson(readerIn, ToolMaterialObject.class);
        toolMaterialTypes.add(new ToolMaterialType(toolMaterialObject));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<ToolMaterialType> getObjectsList() {
        return this.toolMaterialTypes;
    }
}

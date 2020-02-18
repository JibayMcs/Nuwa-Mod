package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.contentpack.common.json.data.materials.ToolMaterialObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.registries.ToolMaterialType;
import net.minecraft.util.ResourceLocation;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

/**
 * Data reading class for json representation of {@link net.minecraft.item.IItemTier} (ToolMaterial) objects
 *
 * @author ZeAmateis
 */
public class ToolMaterialData implements IPackData {

    private final LinkedList<ToolMaterialType> toolMaterialTypes;

    public ToolMaterialData() {
        this.toolMaterialTypes = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return the full entry folder path
     */
    @Override
    public String getEntryFolder() {
        return "objects/materials/tools/";
    }

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack
     *
     * @param packManagerIn The {@link PackManager} instance
     * @param contentPackIn The {@link ContentPack} instance
     * @param readerIn      The {@link InputStreamReader} instance
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, InputStreamReader readerIn) {
        ToolMaterialObject toolMaterialObject = packManagerIn.getGson().fromJson(readerIn, ToolMaterialObject.class);
        ResourceLocation registryName = new ResourceLocation(contentPackIn.getNamespace(), toolMaterialObject.getRegistryName());
        toolMaterialTypes.add(new ToolMaterialType(toolMaterialObject).setRegistryName(registryName));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList} type of {@link net.minecraftforge.registries.IForgeRegistryEntry}
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    @Override
    public LinkedList<ToolMaterialType> getObjectsList() {
        return this.toolMaterialTypes;
    }
}

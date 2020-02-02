package api.contentpack.common.data;

import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.containers.ContainersObject;
import api.contentpack.common.minecraft.registries.ContainerType;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class ContainersData implements IPackData {

    private final LinkedList<ContainerType> containerTypes;

    public ContainersData(LinkedList<ContainerType> containerTypes) {
        this.containerTypes = containerTypes;
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return String
     */
    @Override
    public String getEntryFolder() {
        return "objects/containers/";
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
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        ContainersObject containersObject = PackManager.GSON.fromJson(readerIn, ContainersObject.class);
        containerTypes.add(new ContainerType(containersObject));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<ContainerType> getObjectsList() {
        return containerTypes;
    }

    /**
     * Link {@link IPackData#getObjectsList()} to the correct Forge Registry
     *
     * @return IForgeRegistry
     * @see ForgeRegistries
     */
    @Override
    public IForgeRegistry<ContainerType> getObjectsRegistry() {
        return NuwaMod.Registries.CONTAINER;
    }
}

package api.contentpack.common;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public interface IPackData {

    String getEntryName();

    void parseData(ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn);

    LinkedList<? extends IForgeRegistryEntry> getObjectsList();

    IForgeRegistry getRegistry();

}

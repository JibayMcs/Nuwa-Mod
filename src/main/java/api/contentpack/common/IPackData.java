package api.contentpack.common;

import net.minecraftforge.registries.ForgeRegistryEntry;

import java.io.InputStreamReader;
import java.util.LinkedList;

public interface IPackData {

    String getEntryName();

    void parseData(InputStreamReader readerIn);

    LinkedList<? extends ForgeRegistryEntry> getObjectsList();
}

package api.contentpack.common.minecraft.assets;

import api.contentpack.common.ContentPack;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.resources.FilePack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;

import java.io.File;
import java.util.Map;

public class ContentPackFinder implements IPackFinder {

    private final ContentPack contentPack;

    public ContentPackFinder(ContentPack contentPackIn) {
        this.contentPack = contentPackIn;
    }

    @Override
    public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, ResourcePackInfo.IFactory<T> packInfoFactory) {
        File contentPackFile = contentPack.getFile();
        if (contentPackFile != null && contentPackFile.isFile()) {
            T t1 = ResourcePackInfo.createResourcePack(contentPack.getNamespace(), true, () -> new FilePack(contentPackFile) {
                @Override
                public boolean isHidden() {
                    return true;
                }
            }, packInfoFactory, ResourcePackInfo.Priority.TOP);
            if (t1 != null) {
                nameToPackMap.put(contentPack.getNamespace(), t1);
                NuwaMod.getLogger().debug("Added {} content pack assets to resources packs list.", t1.getName());
            }
        }
    }


}
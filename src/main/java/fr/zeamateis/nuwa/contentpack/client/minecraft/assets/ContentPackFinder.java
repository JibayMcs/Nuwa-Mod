package fr.zeamateis.nuwa.contentpack.client.minecraft.assets;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import net.minecraft.resources.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ContentPackFinder implements IPackFinder {

    private final ContentPack contentPack;
    private final PackManager packManager;

    public ContentPackFinder(PackManager packManagerIn, ContentPack contentPackIn) {
        this.packManager = packManagerIn;
        this.contentPack = contentPackIn;
    }

    /**
     * Add Content Pack to the Vanilla Resource Pack system to load assets from it
     *
     * @param nameToPackMap   {@link Map} of the {@link ContentPack} namespace key and the {@link ResourcePackInfo} instance value
     * @param packInfoFactory The {@link net.minecraft.resources.ResourcePackInfo.IFactory} instance
     */
    @Override
    public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, ResourcePackInfo.IFactory<T> packInfoFactory) {
        File contentPackFile = contentPack.getFile();
        if (contentPackFile != null) {
            ResourcePack pack;
            if(contentPack.isZipped()) {
                pack = new FilePack(contentPackFile) {
                    @Override
                    public boolean isHidden() {
                        return true;
                    }

                    @Override
                    public void close() {
                        super.close();
                    }

                    @Override
                    protected void finalize() throws Throwable {
                        super.finalize();
                    }
                };
            } else {
                pack = new FolderPack(contentPackFile) {
                    @Override
                    public boolean isHidden() {
                        return true;
                    }

                    @Override
                    public void close() {
                        try {
                            super.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    protected void finalize() throws Throwable {
                        super.finalize();
                    }
                };
            }
            T t1 = ResourcePackInfo.createResourcePack(contentPack.getNamespace(), true, () -> pack, packInfoFactory, ResourcePackInfo.Priority.TOP);
            if (t1 != null) {
                nameToPackMap.put(contentPack.getNamespace(), t1);
                packManager.getLogger().info("Added {} content pack assets to resources packs list.", t1.getName());
            }
        }
    }

}
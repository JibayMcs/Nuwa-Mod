package api.contentpack;

import api.contentpack.json.PackInfoObject;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ContentPack {

    private final File contentPackFile;
    private final PackInfoObject packInfoObject;
    private final long zipFileSize;
    private NativeImage packIcon;

    public ContentPack(File contentPackFileIn, PackInfoObject packInfoObject, long zipFileSize) {
        this.contentPackFile = contentPackFileIn;
        this.packInfoObject = packInfoObject;
        this.zipFileSize = zipFileSize;
    }

    public ContentPack(InputStream stream, File contentPackFileIn, PackInfoObject packInfoObject, long zipFileSize) {
        this(contentPackFileIn, packInfoObject, zipFileSize);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            try {
                packIcon = NativeImage.read(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public String getPackName() {
        return this.packInfoObject.getPackName();
    }

    public String getNamespace() {
        return this.packInfoObject.getNamespace();
    }

    public String getVersion() {
        return this.packInfoObject.getVersion();
    }

    public List<String> getDescription() {
        return this.packInfoObject.getDescription();
    }

    public List<String> getAuthors() {
        return this.packInfoObject.getAuthors();
    }

    public List<String> getCredits() {
        return this.packInfoObject.getCredits();
    }

    public NativeImage getPackIcon() {
        return packIcon;
    }

    private void setPackIcon(NativeImage packIconIn) {
        this.packIcon = packIconIn;
    }

    public long getZipFileSize() {
        return zipFileSize;
    }

    public File getFile() {
        return contentPackFile;
    }

    public String getLicense() {
        return this.packInfoObject.getLicense();
    }

    public PackInfoObject getPackInfo() {
        return packInfoObject;
    }

    @Override
    public String toString() {
        return "ContentPack{" +
                "contentPackFile=" + contentPackFile +
                ", packInfoObject=" + packInfoObject +
                ", zipFileSize=" + zipFileSize +
                ", packIcon=" + packIcon +
                '}';
    }
}

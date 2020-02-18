package api.contentpack;

import api.contentpack.io.DiskFile;
import api.contentpack.io.IFile;
import api.contentpack.io.ZipEntryFile;
import api.contentpack.json.PackInfoObject;
import com.google.common.collect.Lists;
import fr.zeamateis.nuwa.NuwaMod;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Representation of a Content Pack, with majors informations
 *
 * @author ZeAmateis
 */
public class ContentPack {

    private final File contentPackFile;
    private final PackInfoObject packInfoObject;
    private final long zipFileSize;
    private final Path basePath;
    private NativeImage packIcon;
    private ZipFile zipFile;
    private boolean isZipped;

    /**
     * Collection of sub files inside this content pack
     */
    private Collection<IFile> subFiles;

    /**
     * Creates a zipped pack
     * @param contentPackFileIn
     * @param packInfoObject
     * @param zipFileSize
     */
    public ContentPack(File contentPackFileIn, PackInfoObject packInfoObject, long zipFileSize) {
        this.contentPackFile = contentPackFileIn;
        this.packInfoObject = packInfoObject;
        this.zipFileSize = zipFileSize;
        this.basePath = null;
        isZipped = true;
    }

    /**
     * Creates a zipped pack, with the given icon
     * @param iconStream
     * @param contentPackFileIn
     * @param packInfoObject
     * @param zipFileSize
     */
    public ContentPack(InputStream iconStream, File contentPackFileIn, PackInfoObject packInfoObject, long zipFileSize) {
        this(contentPackFileIn, packInfoObject, zipFileSize);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            try {
                packIcon = NativeImage.read(iconStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Creates a folder-based pack
     */
    public ContentPack(Path contentPackFolder, PackInfoObject packInfoObject) {
        this.contentPackFile = contentPackFolder.toFile();
        this.basePath = contentPackFolder;
        this.packInfoObject = packInfoObject;
        this.zipFileSize = 0L;
        this.isZipped = false;
    }

    /**
     * Creates a folder-based pack, with the given icon
     */
    public ContentPack(InputStream iconStream, Path contentPackFolder, PackInfoObject packInfoObject) {
        this(contentPackFolder, packInfoObject);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            try {
                packIcon = NativeImage.read(iconStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private ZipFile getBackingZip() throws IOException {
        if(zipFile == null) {
            if(this.isZipped()) {
                zipFile = new ZipFile(contentPackFile);
            } else {
                throw new IOException("Tried to get backing zip of non-zip content pack!");
            }
        }
        return zipFile;
    }

    /**
     * Returns the list of all files inside this content pack
     * @return
     */
    public Collection<IFile> getFiles() {
        if(subFiles == null) {
            if(isZipped()) {
                subFiles = extractZipFiles();
            } else {
                subFiles = walkFolders();
            }
            NuwaMod.LOGGER.warn("Read files from content pack {}: {}", getPackInfo().getPackName(), subFiles.stream().map(it -> it.getName()).collect(Collectors.joining(",")));
        }
        return subFiles;
    }

    private Collection<IFile> walkFolders() {
        LinkedList<IFile> files = new LinkedList<>();
        try {
            Files.walkFileTree(basePath, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    files.add(new DiskFile(dir.toFile(), basePath.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    files.add(new DiskFile(file.toFile(), basePath.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    private Collection<IFile> extractZipFiles() {
        try {
            ZipFile zip = getBackingZip();
            Enumeration<? extends ZipEntry> entries = zip.entries();
            LinkedList<IFile> files = new LinkedList<>();
            while(entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                ZipEntryFile file = new ZipEntryFile(zip, entry);
                files.add(file);
            }
            return files;
        } catch (Exception e) {
            NuwaMod.LOGGER.error("Failed to load sub files for content pack: "+toString(), e);
        }
        return Lists.newArrayList();
    }

    /**
     * Returns a sub file with the given name (can go downwards in a file hierarchy, ie assets/mymod/textures/logo.png is valid)
     * @param name
     * @return
     */
    public IFile getSubFile(String name) {
        return getFiles().stream().filter(it -> it.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
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

    public boolean isZipped() {
        return this.isZipped;
    }

    @Override
    public String toString() {
        return "ContentPack{" +
                "contentPackFile=" + contentPackFile +
                ", packInfoObject=" + packInfoObject +
                ", isZipped=" + isZipped +
                ", zipFileSize=" + zipFileSize +
                ", packIcon=" + packIcon +
                '}';
    }
}

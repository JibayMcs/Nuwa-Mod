package fr.zeamateis.nuwa.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 *
 * @author jglrxavpok
 */
public class DiskFile implements IFile {

    private File file;
    private Path relativePath;

    public DiskFile(File file, Path relativePath) {
        this.file = file;
        this.relativePath = relativePath;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String getName() {
        return relativePath.toString().replace(File.separator, "/");
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }
}

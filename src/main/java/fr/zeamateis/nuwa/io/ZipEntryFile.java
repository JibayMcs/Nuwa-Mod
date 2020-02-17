package fr.zeamateis.nuwa.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author jglrxavpok
 */
public class ZipEntryFile implements IFile {
    private final ZipFile archive;
    private final ZipEntry entry;

    public ZipEntryFile(ZipFile archive, ZipEntry entry) {
        this.archive = archive;
        this.entry = entry;
    }

    @Override
    public String getName() {
        return entry.getName();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return archive.getInputStream(entry);
    }
}
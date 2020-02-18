package api.contentpack.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents an abstract file, either a file on the disk, or inside a zip file
 * @author jglrxavpok
 */
public interface IFile {

    /**
     * Returns the simple name of the file
     * @return
     */
    String getName();

    /**
     * Returns a **new** InputStream to read from this file
     * @return
     * @throws IOException if an error occurred
     */
    InputStream getInputStream() throws IOException;

}

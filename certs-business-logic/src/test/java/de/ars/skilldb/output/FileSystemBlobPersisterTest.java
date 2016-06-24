/**
 *
 */
package de.ars.skilldb.output;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.Test;

/**
 * Testet den {@code FileSystemBlobPersister}.
 *
 */
public class FileSystemBlobPersisterTest {

    private static final String HUMPTY = "Humpty Dumpty sat on a wall,\n"
                          + "Humpty Dumpty had a great fall!\n"
                          + "All the king's horses, and all the king's men,\n"
                          + "couldn't put Humpty together again.";

    /**
     * Testet, ob eine Datei auf das Filesystem geschrieben und wieder gefunden werden kann.
     */
    @Test
    public void testPersistAndRetrieveBlob() {
        File directory = new File("build/blobs/test");
        FileSystemBlobPersister persister = new FileSystemBlobPersister(directory);
        String id = persister.persistBlob(HUMPTY.getBytes());

        byte[] bytes = persister.retrieveBlob(id);

        assertArrayEquals(HUMPTY.getBytes(), bytes);
    }
}

/**
 *
 */
package de.ars.skilldb.output;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.codec.binary.Hex;

import com.google.common.io.Files;

/**
 * Persistiert Blobs im Dateisystem.
 */
public class FileSystemBlobPersister implements BlobPersister {

    private final File directory;

    /**
     * Erzeugt einen neuen {@code FileSystemBlobPersister}.
     *
     * @param directory
     *            das Verzeichnis, in dem die Daten gespeichert werden sollen.
     */
    public FileSystemBlobPersister(final File directory) {
        if (!directory.isDirectory()) {
            boolean mkdirSuccess = directory.mkdirs();
            if (!mkdirSuccess) {
                throw new IllegalStateException("Ordnerstruktur konnte nicht hergesstellt werden: "
                        + directory.getAbsolutePath());
            }
        }
        this.directory = directory;
    }

    @Override
    public String persistBlob(final byte[] data) {
        try {
            String fileHash = hash(data);
            File newFile = new File(directory, fileHash);
            boolean createFileSuccess = newFile.createNewFile();

            if (createFileSuccess) {
                Files.write(compress(data), newFile);
            }

            return fileHash;
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String hash(final byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return Hex.encodeHexString(md.digest(bytes));
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public byte[] retrieveBlob(final String id) {
        try {
            File retrieved = new File(directory, id);
            return decompress(Files.toByteArray(retrieved));
        }
        catch (IOException | DataFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void clear() {
        deleteFolder(directory);
    }

    private void deleteFolder(final File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                }
                else {
                    boolean deleteSuccess = f.delete();
                    if (!deleteSuccess) {
                        throw new IllegalStateException("Konnte Datei nicht löschen: " + f.getAbsolutePath());
                    }
                }
            }
        }
    }

    private byte[] compress(final byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();

        return output;
    }

    private byte[] decompress(final byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();

        return output;
    }

}

/**
 *
 */
package de.ars.skilldb.output;

/**
 * Persistiert byte-Arrays, damit die Datenbank frei bleibt davon.
 */
public interface BlobPersister {

    /**
     * Persistiert ein {@code byte[]}-Array und liefert eine eindeutige ID zu diesem zurück, unter
     * der die Datei später wieder gefunden werden kann.
     *
     * @param data
     *            die abzuspeichernden Daten als {@code byte[]}-Array.
     * @return eine eindeutige ID als {@code String}.
     */
    String persistBlob(byte[] data);

    /**
     * Liefert die unter der angegebenen ID abgelegten Daten als {@code byte[]}-Array zurück.
     *
     * @param id
     *            die Id der Daten.
     * @return die gefundenen Daten.
     * @throws IllegalArgumentException
     *             falls zu der spezifizierten Id keine Daten existieren.
     */
    byte[] retrieveBlob(String id);

    /**
     * Löscht alle vorhandenen Blobs.
     */
    void clear();

}

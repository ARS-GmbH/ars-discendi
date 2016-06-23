/**
 *
 */
package de.ars.skilldb.interactor;

import java.util.Collection;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.Path;

/**
 * Interactor für die {@code Path}s, der die notwendige Business-Logik zur Verfügung stellt.
 */
public interface PathInteractor extends Interactor<Path> {

    /**
     * Findet alle Pfade zu einer Zertifizierung.
     *
     * @param certification
     *            die {@code Certification}, zu der alle Pfade zurückgegeben werden sollen.
     * @return die {@code Path}s zu dieser Zertifizierung.
     */
    Collection<Path> findPathsToCertification(Certification certification);

    /**
     * Speichert mehrere Pfade ab.
     *
     * @param paths
     *            die {@code Path}s, die abgespeichert werden sollen.
     * @return die neuen, gespeicherten {@code Path}s;
     */
    Iterable<Path> save(Iterable<Path> paths);
}

/**
 *
 */
package de.ars.skilldb.returnobjects;

import java.util.Collection;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.util.Ensure;

/**
 * Response-Objekt für die Rückgabe der Zertifizierungen nach einem Update.
 *
 */
public class UpdateResponse {

    private final Collection<Certification> all;
    private final Collection<Certification> updated;
    private final Collection<Certification> newOnes;

    /**
     * Konstruiert ein neues {@link UpdateResponse}-Objekt.
     *
     * @param all
     *            alle Zertifizierungen.
     * @param updated
     *            die aktualisierten Zertifizierungen.
     * @param newOnes
     *            die neuen Zertifizierungen.
     */
    public UpdateResponse(final Collection<Certification> all, final Collection<Certification> updated,
            final Collection<Certification> newOnes) {
        Ensure.that(all, updated, newOnes).isNotNull();
        this.all = all;
        this.updated = updated;
        this.newOnes = newOnes;
    }

    public Collection<Certification> getAll() {
        return all;
    }

    public Collection<Certification> getUpdated() {
        return updated;
    }

    public Collection<Certification> getNewOnes() {
        return newOnes;
    }

}

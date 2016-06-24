/**
 *
 */
package de.ars.skilldb.interactor;

import java.util.Collection;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.PlannedCertification;
import de.ars.skilldb.domain.User;

/**
 * Interactor für die {@code PlannedCertification}s, der die notwendige Business-Logik zur Verfügung
 * stellt.
 */
public interface PlannedCertificationInteractor extends Interactor<PlannedCertification> {

    /**
     * Findet alle geplanten Zertifizierungen zu einem {@code User}.
     *
     * @param user
     *            der {@code User}, zu dem alle geplanten Zertifizierungen gefunden werden solllen.
     * @return alle geplanten Zertifizierungen des {@code User}s.
     */
    Collection<PlannedCertification> findByUser(User user);

    /**
     * Löscht eine geplante Zertifizierung eines Benutzers anhand der Zertifizierungs-Id.
     *
     * @param user
     *            der {@code User}, dessen geplante Zertifizierung gelöscht werden soll.
     * @param certification
     *            die dazugehörige {@code Certification}.
     */
    void deleteByAccomplish(User user, Certification certification);
}

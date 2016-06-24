package de.ars.skilldb.interactor;

import java.util.Collection;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.ProductGroup;
import de.ars.skilldb.returnobjects.CertificationRevision;

/**
 * Interactor für die {@code Certification}s, der die notwendige Business-Logik zur Verfügung
 * stellt.
 */
public interface CertificationInteractor extends Interactor<Certification> {

    /**
     * Lädt alle Zertifizierungen der IBM von deren Internetseite.
     *
     * @return ein {@code ResponseModel}, das alle {code Certification}s beinhaltet, die von der
     *         Webseite ausgelesen wurden. Die Liste kann mit
     *         {@code ResponseModel.getList("allCertifications")} abgerufen werden.
     */
    Collection<Certification> loadCertificationsInitially();

    /**
     * Findet eine {@code Certification} anhand des Namens.
     *
     * @param name
     *            der Name der {@code Certification}.
     * @return die gesuchte {@code Certification}.
     */
    Certification findByName(String name);

    /**
     * Findet eine {@code Certification} anhand der ID.
     *
     * @param id
     *            die ID der {@code Certification}.
     * @return ein {@code CertificationRevision}-Objekt, das die gesuchte {@code Certification}
     *         sowie dessen vorhergehende Version (vor der letzten Änderung) beinhaltet.
     */
    CertificationRevision findOneWithOldCertification(Long id);

    /**
     * Ermittelt, wie viele {@code Certification}s es mit einem bestimmten Teilbegriff (Suchwort)
     * gibt.
     *
     * @param searchQuery
     *            der Suchbegriff, mit dem nach dem Zertifizierungs-Namen gesucht wird.
     * @return die Anzahl der {@code Certification}s mit dem spezifizierten Suchwort.
     */
    long countWithNameContaining(String searchQuery);

    /**
     * Findet {@code Certification}s anhand eines Suchbegriffs. Eine Zertifizierung wird gefunden,
     * wenn der Suchbegriff im Namen auftaucht.
     *
     * @param searchQuery
     *            der Suchbegriff, mit dem nach dem Zertifizierungs-Namen gesucht wird.
     * @param page
     *            die Seite, ab der die Treffer zurückgegeben werden sollen.
     * @param count
     *            die Anzahl der maximalen Treffer, die zurückgegeben werden sollen.
     * @return eine {@code Collection}, das die gesuchten {@code Certification}s beinhaltet.
     */
    Collection<Certification> findByNameContaining(String searchQuery, int page, int count);

    /**
     * Updated alle Zertifizierungen der IBM von deren Internetseite.
     *
     * @return ein {@code ResponseModel}, das alle {code Certification}s beinhaltet, die von der
     *         Webseite ausgelesen wurden. Die Liste kann mit
     *         {@code ResponseModel.getList("certifications")} abgerufen werden.
     */
    Collection<Certification> updateCertifications();

    /**
     * Liefert alle {@code Certification}s zurück, die zuletzt aktualisiert wurden.
     *
     * @return alle zuletzt aktualisierten {@code Certification}s.
     */
    Collection<Certification> findUpdated();

    /**
     * Liefert alle {@code Certification}s zurück, die zuletzt neu hinzugefügt wurden.
     *
     * @return alle zuletzt hinzugefügten {@code Certification}s.
     */
    Collection<Certification> findNewOnes();

    /**
     * Checkt die Constraints, die für eine Zertifizierung gelten.
     *
     * @param certification
     *            die Zertifizierung, die geprüft werden soll.
     */
    void checkCertificationConstraints(Certification certification);

    /**
     * Liefert alle Zertifizierungen zu einer {@code ProductGroup}.
     *
     * @param productGroup
     *            die {@code ProductGroup}, zu der alle möglichen Zertifizierungen gefunden werden
     *            sollen.
     * @return alle Zertifizierungen der spezifizierten {@code ProductGroup}.
     */
    Collection<Certification> findByProductGroup(ProductGroup productGroup);
}

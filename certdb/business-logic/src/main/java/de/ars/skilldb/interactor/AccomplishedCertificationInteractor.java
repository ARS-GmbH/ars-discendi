/**
 *
 */
package de.ars.skilldb.interactor;

import java.util.List;

import de.ars.skilldb.domain.*;
import de.ars.skilldb.domain.enums.CertKind;

/**
 * Interactor für die {@code AccomplishedCertification}s, der die notwendige Business-Logik zur
 * Verfügung stellt.
 */
public interface AccomplishedCertificationInteractor extends Interactor<AccomplishedCertification> {

    /**
     * Findet alle abgeschlossenen Zertifizierungen eines Benutzers.
     *
     * @param user
     *            der Benutzer, zu dem alle abgeschlossenen Zertifizierungen gefunden werden sollen.
     * @return alle abgeschlossenen Zertifizierungen eines Benutzers.
     */
    List<AccomplishedCertification> findByUser(User user);

    /**
     * Findet alle abgeschlossenen Zertifizierungen zu einer bestimmten Zertifizierung.
     *
     * @param certification
     *            die Zertifizierung, zu der alle abgeschlossenen Zertifizierungen gefunden werden
     *            sollen.
     * @return alle abgeschlossenen Zertifizierungen zu dieser Zertifizierung.
     */
    List<AccomplishedCertification> findByCertification(Certification certification);

    /**
     * Findet alle abgeschlossenen Zertifizierungen zu einem Benutzer, die in Kürze ablaufen.
     *
     * @param user
     *            der Benutzer, zu dem die in Kürze ablaufenden, abgeschlossenen Zertifizierungen
     *            gefunden werden sollen.
     * @return alle in Kürze ablaufendne, abgeschlossenen Zertifizierungen des spezifizierten
     *         Benutzers.
     */
    List<AccomplishedCertification> findByUserExpiringSoon(User user);

    /**
     * Findet alle abgeschlossenen Zertifizierungen, die in einer bestimmten Produktgruppe abgelegt
     * wurden und einen bestimmten Typ (Sales oder Technical) haben.
     *
     * @param productGroup
     *            die {@code ProductGroup}, für die die gesuchte Zertifizierung gültig sein soll.
     * @param certKind
     *            der Typ der Zertifizierung (Sales, Technical)
     * @return alle abgeschlossenen Zertifizierungen der Produktgruppe mit angegebenem Typ.
     */
    List<AccomplishedCertification> findByProductGroupAndKind(ProductGroup productGroup, CertKind certKind);

    /**
     * Speichert eine neue {@code AccomplishedCertification} mit Dokument-Datei in der Datenbank ab.
     *
     * @param accomplishedCertification
     *            die zu speichernde {@code AccomplishedCertification}.
     * @param file
     *            das Dokument, das dieser {@code AccomplishedCertification} angehängt sein soll.
     * @return die gespeicherte {@code AccomplishedCertification}.
     */
    AccomplishedCertification save(AccomplishedCertification accomplishedCertification, byte[] file);

    /**
     * Holt das Dokument der abgeschlossenen Zertifizierung mit der spezifizierten Id als
     * byte-Array.
     *
     * @param id
     *            die Id der abgeschlossenen Zertifizierung ({@code AccomplishedCertification}).
     * @return das Dokument als byte-Array.
     */
    byte[] getAccomplishedDocument(Long id);

}

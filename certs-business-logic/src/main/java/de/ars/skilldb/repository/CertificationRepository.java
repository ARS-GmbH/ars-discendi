package de.ars.skilldb.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.ProductGroup;
import de.ars.skilldb.domain.enums.CertKind;

/**
 * Spring-Data Repository für die Klasse {@code Certification}.
 */
public interface CertificationRepository extends PagingAndSortingRepository<Certification, Long> {

    /**
     * Findet eine {@code Certification} anhand ihres Namens.
     * 
     * @param name
     *            der Name der gesuchten {@code Certification}.
     * @return die gesuchte {@code Certification}.
     */
    Certification findByName(String name);

    /**
     * Findet eine {@code Certification} anhand des Typs (Sales, Technical) und gleichzeitig anhand
     * der Produktgruppe ({@link ProductGroup}).
     * 
     * @param kind
     *            der Typ der {@code Certification}.
     * @param productGroup
     *            die {@code ProductGroup}, zu der die gesuchte Zertifizierung gehören soll.
     * @return die gesuchten {@code Certification}s.
     */
    @Query("Start productGroup=node({1}) "
            + "Match (a:AccomplishedCertification)-[]-(c:Certification)-[]-(productGroup) where c.kind = Upper({0})"
            + "AND c.status in ['ACTIVE', 'NEW_ACTIVE', 'EXPIRING'] " + "Return c;")
    Collection<Certification> findByKindAndProductGroup(CertKind kind, ProductGroup productGroup);

    /**
     * Findet eine {@code Certification} anhand ihres Certification Code.
     * 
     * @param certificationCode
     *            der Certification Code der gesuchten {@code Certification}.
     * @return die gesuchte {@code Certification}.
     */
    Collection<Certification> findByCertificationCode(String certificationCode);

    /**
     * Findet eine {@code Certification} anhand eines Suchbegriffs. Die Zertifizierung wird
     * gefunden, wenn ihr Name den Suchbegriff enthält.
     * 
     * @param searchQuery
     *            der Suchbegriff, mit dem nach einer Zertifizierung gesucht werden soll.
     * @param pageable
     *            das {@code Pageable}-Objekt, mit dem die Anzahl der Objekte und die Start-Page
     *            ausgewählt wird.
     * @return eine {@code List}, die alle gefundenen {@code Certification}s enthält.
     */
    List<Certification> findByNameContaining(String searchQuery, Pageable pageable);

    /**
     * Findet eine {@code Certification} anhand ihres Certification Code und gleichzeitig anhand der
     * Version. Dies identifiziert eine {@code Certification} eindeutig.
     * 
     * @param certificationCode
     *            der Certification Code der gesuchten {@code Certification}.
     * @param version
     *            die Version der gesuchten {@code Certification}.
     * @return die gesuchte {@code Certification}.
     */
    Certification findByCertificationCodeAndVersion(String certificationCode, Integer version);

    /**
     * Liefert alle Zertifizierung der spezifizierten {@code ProductGroup} zurück.
     * 
     * @param productGroup
     *            die {@code ProductGroup}, zu der die gesuchten Zertifizierungen gehören.
     * 
     * @return alle Zertifizierung der spezifizierten {@code ProductGroup}.
     */
    @Query("Start productGroup=node({0}) Match (productGroup)<-[:RELEVANT_FOR]-(c:Certification) return c;")
    Iterable<Certification> findByProductGroup(ProductGroup productGroup);

    /**
     * Liefert die Anzahl der Zertifizierungen zurück, die einen bestimmte Suchbegriff enthalten.
     * 
     * @param searchQuery
     *            der Suchbegriff.
     * @return die Anzahl der Zertifizierungen mit dem Suchbegriff.
     */
    @Query("Match (c:Certification) Where c.name =~ ('.*' + {0} + '.*') return count(c);")
    long countWithNameContaining(String searchQuery);
}

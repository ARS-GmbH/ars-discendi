/**
 *
 */
package de.ars.skilldb.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.PlannedCertification;
import de.ars.skilldb.domain.User;

/**
 * Spring-Data Repository für die Klasse {@code PlannedCertification}.
 */
public interface PlannedCertificationRepository extends PagingAndSortingRepository<PlannedCertification, Long> {

    /**
     * Findet alle geplanten Zertifizierungen zu einem {@code User}.
     *
     * @param user
     *            der {@code User}, zu dem alle geplanten Zertifizierungen gefunden werden solllen.
     * @return alle geplanten Zertifizierungen des {@code User}s.
     */
    Iterable<PlannedCertification> findByUser(User user);

    /**
     * Löscht eine geplante Zertifizierung eines Benutzers anhand der Zertifizierungs-Id.
     *
     * @param user
     *            der {@code User}, dessen geplante Zertifizierung gelöscht werden soll.
     * @param certification
     *            die dazugehörige {@code Certification}.
     */
    @Query("Start user=node({0}), cert=node({1}) match (user)-[r]-(pc:PlannedCertification)-[s]-(p:Path)-[]-(cert) delete pc,r,s;")
    void deleteByUserAndCertification(User user, Certification certification);
}

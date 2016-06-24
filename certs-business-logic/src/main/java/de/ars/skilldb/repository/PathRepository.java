package de.ars.skilldb.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.Path;

/**
 * Spring-Data Repository für die Klasse {@code Certification}.
 */
public interface PathRepository extends PagingAndSortingRepository<Path, Long> {

    /**
     * Findet alle Pfade zu einer Zertifizierung.
     *
     * @param certification
     *            die {@code Certification}, zu der alle angehängten Pfade gefunden werden sollen.
     * @return alle Pfade der {@code Certification}.
     */
    @Query("Start cert=node({0}) Match (cert)-[:DESTINATION_TO]-(p:Path) return p order by p.sequenceNumber")
    Set<Path> findAllPathsOfCertification(Certification certification);

    /**
     * Löscht alle Pfade, die mit keiner Zertifizierung mehr verknüpft sind.
     */
    @Query("Match (p:Path) WHERE NOT (p)-[:DESTINATION_TO]->() Match (p)-[r]-() delete p, r;")
    void deleteAllDanglingPaths();

    /**
     * Prüft, ob eine Zertifizierung sich selbst in einem ihrer Pfade als notwendig referenziert.
     *
     * @param cert
     *            die {@code Certification} für die gepfrüft wird, ob sie die Zielzertifizierung
     *            ist.
     * @param path
     *            der {@code Path}, für den die Prüfung stattfindet.
     * @return {@code true} wenn die Zertifizierung das Ziel ist, andernfalls {@code false}.
     */
    @Query("Start cert=node({0}), path=node({1}) optional match (cert)<-[d:DESTINATION_TO]-(path) return d is not null")
    boolean pathDestinatesNecessaryCertification(Certification cert, Path path);
}

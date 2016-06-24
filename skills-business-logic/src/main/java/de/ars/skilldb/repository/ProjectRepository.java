/**
 *
 */
package de.ars.skilldb.repository;

import java.util.Collection;

import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.Project;

/**
 * Repository für Projekte.
 */
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

    /**
     * Liefert das Projekt mit dem übergebenen Namen zurück.
     * Groß- und Kleinschreibung wird dabei nicht beachtet.
     * @param name Name des Projekts
     * @return Projekt
     */
    @Query("MATCH (p:Project) WHERE p.name =~ ('(?i)' + {0}) RETURN p")
    Project findByNameCaseInsensitive(String name);

    /**
     * Sucht ein Projekt anhand des Namens bei dem die ID nicht der
     * übergebenen ID entspricht.
     * @param id ID, die das Projekt identifiziert, das bei der Suche nicht berücksichtigt werden soll
     * @param name Name nach dem gesucht werden soll
     * @return Projekt
     */
    @Query("MATCH (p:Project) WHERE p.name =~ ('(?i)' + {1}) AND NOT id(p)={0} RETURN p")
    Project findByNameCaseInsensitiveWithoutId(Long id, String name);

    /**
     * Sucht alle Projekte eines Kunden.
     * @param customerId ID des Kunden, dessen Projekte gefunden werden sollen
     * @param sort Sortierung der Ergebnisse
     * @return Projekte des Kunden
     */
    Collection<Project> findByCustomer(Long customerId, Sort sort);
}

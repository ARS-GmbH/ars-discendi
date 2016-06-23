/**
 *
 */
package de.ars.skilldb.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.Project;
import de.ars.skilldb.domain.ProjectParticipation;
import de.ars.skilldb.domain.User;

/**
 * Repository für Projektarbeiten.
 */
public interface ProjectParticipationRepository extends PagingAndSortingRepository<ProjectParticipation, Long> {

    /**
     * Liefert alle Projektarbeiten eines Mitarbeiters zurück.
     * @param user Mitarbeiter, dessen Projektarbeiten gefunden werden sollen
     * @return alle Projektarbeiten des Benutzers
     */
    @Query("START user=NODE({0}) MATCH (p:ProjectParticipation) WHERE (user)-[:DID]->(p) RETURN p")
    Collection<ProjectParticipation> findAllProjectParticipationsByUser(User user);

    /**
     * Liefert eine Projektarbeit anhand des Mitarbeiters und des Projekts.
     * @param project Projekt, in dem die gesuchte Projektarbeit durchgeführt wurde
     * @param user Mitarbeiter, der die Projektarbeit durchgeführt hat
     * @return Projektarbeit
     */
    @Query("START project=NODE({0}), user=NODE({1}) MATCH (p:ProjectParticipation) WHERE (project)<-[:BELONGS_TO]-(p)<-[:DID]-(user) RETURN p")
    ProjectParticipation findByProjectAndUser(Project project, User user);

    /**
     * Liefert eine Projektarbeit anhand des Mitarbeiters und des Projekts, wobei die Projektarbeit mit der übergebenen ID nicht berücksichtigt wird.
     * @param project Projekt, in dem die gesuchte Projektarbeit durchgeführt wurde
     * @param user Mitarbeiter, der die Projektarbeit durchgeführt hat
     * @param id Der Knoten mit dieser ID wird bei der Suche nicht berücksichtigt
     * @return Projektarbeit
     */
    @Query("START project=NODE({0}), user=NODE({1}) MATCH (p:ProjectParticipation) WHERE (user)-[:DID]->(p)-[:BELONGS_TO]->(project) AND NOT id(p)={2} RETURN p")
    ProjectParticipation findByProjectAndUserWithoutId(Project project, User user, Long id);

    /**
     * Liefert alle Wissensgebiete der Skills, die in einer Projektarbeit vorkommen, zurück.
     * @param projectParticipation Projektarbeit, deren Wissensgebiete gefunden werden sollen
     * @return alle Wissensgebiete, die in der Projektarbeit vorkommen
     */
    @Query("START p=NODE({0}) MATCH (p)-[:PROVES]->(s)-[:BASE_KNOWLEDGE]->(k) RETURN k")
    Set<Knowledge> findAllKnowledges(ProjectParticipation projectParticipation);
}

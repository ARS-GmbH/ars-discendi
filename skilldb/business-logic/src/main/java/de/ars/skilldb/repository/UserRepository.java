package de.ars.skilldb.repository;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.User;

/**
 * Repository für User.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    /**
     * Findet einen Benutzer anhand des Benutzernamens.
     * @param userName Name des gesuchten Benutzers
     * @return Benutzer-Objekt. Null, wenn kein Benutzer gefunden wurde.
     */
    User findByUserName(String userName);

    /**
     * Liefert alle Benutzer zurück, die dem übergebenen Benutzer unterstellt sind und eine Skill-Freigabe beantragt haben.
     * @param user Benutzer, der die Freigabe erteilen soll
     * @return Mitarbeiter, deren Skills freigegeben werden sollen
     */
    @Query("START user=node({0}) MATCH (user)<-[:HAS_MANAGER]-(u:User)-[:CAN]->(s:Skill) WHERE s.status='SUBMITTED' RETURN DISTINCT u")
    Collection<User> findUsersWithSubmittedSkills(User user);




}
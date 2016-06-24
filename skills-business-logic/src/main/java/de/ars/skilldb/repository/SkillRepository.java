package de.ars.skilldb.repository;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.User;

/**
 * Repository für Skills.
 */
public interface SkillRepository extends PagingAndSortingRepository<Skill, Long> {

    /**
     * Liefert den Skill eines Mitarbeiters zurück, der mit dem gesuchten Wissensgebiet (case-insensitiv) verknüpft ist.
     * @param knowledgeName Name des gesuchten Wissensgebiets
     * @param userName Name Mitarbeiters, dessen Skill gesucht wird
     * @return Skill
     */
    @Query("MATCH (k:Knowledge)-[]-(s:Skill)-[]-(u:User) WHERE k.name =~ ('(?i)' + {0}) AND u.userName = {1} RETURN s")
    Skill findByKnowledgeName(String knowledgeName, String userName);

    /**
     * Liefert alle freigegebenen Skills eines Mitarbeiters nach Name des Wissensgebiets alphabetisch soritert zurück.
     * @param user Benutzer dessen Skills gefunden werden sollen
     * @return Skills alphabetisch nach Name des Wissensgebiets sortiert
     */
    @Query("START user=node({0}) MATCH (k:Knowledge)<-[:BASE_KNOWLEDGE]-(s:Skill)<-[:CAN]-(user), (s)-[:HAS_LEVEL]->(l:SkillLevel) RETURN s ORDER BY UPPER(k.name) ASC")
    Collection<Skill> findAllSortByNameAsc(User user);

    /**
     * Liefert alle Skills zurück, die zum gesuchten Wissensgebiet gefunden
     * wurden und gleichzeitig mindestens das übergebene Level besitzen.
     * @param knowledge Wissensgebiet der gesuchten Skills
     * @param skillLevel Mindestlevel der gesuchten Skills
     * @return Skills
     */
    @Query("MATCH (s:Skill)-[]-(k:Knowledge), (s)-[:HAS_LEVEL]-(l:SkillLevel) WHERE k.name={0} AND l.internalValue >= {1} RETURN s ORDER BY l.internalValue ASC")
    Collection<Skill> findByKnowledgeNameAndSkillLevel(String knowledge, int skillLevel);

    /**
     * Sucht alle Skills mit dem Status "SUBMITTED" des übergebenen Users.
     * @param user Benutzer, dessen Skills durchsucht werden
     * @return alle freizugebenden Skills des Benutzers
     */
    @Query("START u=node({0}) MATCH (u)-[:CAN]->(s:Skill)-[:SUBMITTED_LEVEL]->(sl) WHERE s.status='SUBMITTED' RETURN s")
    Collection<Skill> findSubmittedSkills(User user);
}

/**
 *
 */
package de.ars.skilldb.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.SkillLevel;

/**
 * Repository für Skill-Level.
 */
public interface SkillLevelRepository extends PagingAndSortingRepository<SkillLevel, Long> {

    /**
     * Liefert das anhand des internen Werts gesuchte Skill-Level zurück.
     * @param internalValue interner Wert des Skill-Levels
     * @return das gesuchte Skill-Level
     */
    SkillLevel findByInternalValue(int internalValue);

    /**
     * Liefert das Skill-Level zurück, das zum eingegebenen Namen passt.
     * Groß- und Kleinschreibung wird dabei nicht beachtet.
     * @param name Name des Skill-Levels
     * @return Skill-Level
     */
    @Query("MATCH (l:SkillLevel) WHERE l.name =~ ('(?i)' + {0}) RETURN l")
    SkillLevel findByNameCaseInsensitive(String name);

    /**
     * Sucht ein Skill-Level anhand des internen Werts.
     * Das Level mit der übergebenen ID wird bei der Suche ausgeschlossen.
     *
     * @param id ID, die das Skill-Level identifiziert, das bei der Suche nicht berücksichtigt werden soll
     * @param internalValue interner Wert nach dem gesucht werden soll
     * @return Skill-Level
     */
    @Query("MATCH (l:SkillLevel) WHERE l.internalValue={1} AND NOT id(l)={0} RETURN l")
    SkillLevel findByInternalValueWithoutId(Long id, Integer internalValue);

    /**
     * Sucht ein Skill-Level anhand des Namens bei dem die ID nicht der
     * übergebenen ID entspricht.
     * @param id ID, die das Skill-Level identifiziert, das bei der Suche nicht berücksichtigt werden soll
     * @param name Name nach dem gesucht werden soll
     * @return Skill-Level
     */
    @Query("MATCH (l:SkillLevel) WHERE l.name =~ ('(?i)' + {1}) AND NOT id(l)={0} RETURN l")
    SkillLevel findByNameCaseInsensitiveWithoutId(Long id, String name);
}

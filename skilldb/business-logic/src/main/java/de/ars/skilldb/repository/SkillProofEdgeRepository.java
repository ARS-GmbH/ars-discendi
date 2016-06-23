/**
 *
 */
package de.ars.skilldb.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.Proof;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillProofEdge;

/**
 * Repository für Nachweis-Kanten.
 */
public interface SkillProofEdgeRepository extends PagingAndSortingRepository<SkillProofEdge, Long> {

    /**
     * Liefert alle Nachweise eines Skills zurück.
     * @param skill Skill, dessen Nachweise zurückgeliefert werden sollen.
     * @return Alle Nachweise des Skills
     */
    @Query("Start skill=node({0}) Match (p)-[:PROVES]->(skill) return p")
    Collection<Proof> findBySkill(Skill skill);

    /**
     * Liefert alle Nachweiskanten eines Skills zurück.
     * @param skill Skill, dessen Nachweiskanten zurückgegeben werden sollen
     * @return Nachweiskanten
     */
    @Query("Start skill=node({0}) Match ()-[p:PROVES]->(skill) return p")
    Set<SkillProofEdge> findEdgesBySkill(Skill skill);
}

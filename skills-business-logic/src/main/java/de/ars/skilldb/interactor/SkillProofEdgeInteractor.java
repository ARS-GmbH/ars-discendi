/**
 *
 */
package de.ars.skilldb.interactor;

import java.util.Collection;
import java.util.Set;

import de.ars.skilldb.domain.Proof;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillProofEdge;

/**
 * Interactor für Nachweiskanten.
 */
public interface SkillProofEdgeInteractor extends Interactor<SkillProofEdge> {

    Collection<Proof> findBySkill(Skill skill);

    Set<SkillProofEdge> findEdgesBySkill(Skill skill);

}

/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import de.ars.skilldb.domain.Proof;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillProofEdge;
import de.ars.skilldb.interactor.SkillProofEdgeInteractor;
import de.ars.skilldb.repository.SkillProofEdgeRepository;

/**
 * Implementierung eines Interactors für Projektarbeiten.
 */
public class SkillProofEdgeInteractorImpl extends AbstractInteractor<SkillProofEdge> implements SkillProofEdgeInteractor {

    private SkillProofEdgeRepository skillProofs;

    /**
     * Erzeugt ein neues Objekt der Klasse SkillProofEdgeInteractorImpl.
     *
     * @param skillProofEdgeRepository
     *            Repository für den Datenbankzugriff
     */
    @Autowired
    public SkillProofEdgeInteractorImpl(final SkillProofEdgeRepository skillProofEdgeRepository) {
        super(SkillProofEdge.class, skillProofEdgeRepository);
        skillProofs = skillProofEdgeRepository;
    }

    @Override
    public Collection<Proof> findBySkill(final Skill skill) {
        return skillProofs.findBySkill(skill);
    }

    @Override
    public Set<SkillProofEdge> findEdgesBySkill(final Skill skill) {
        return skillProofs.findEdgesBySkill(skill);
    }


}

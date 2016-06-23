/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.domain.enums.SkillStatus;
import de.ars.skilldb.interactor.SkillInteractor;
import de.ars.skilldb.repository.SkillRepository;
import de.ars.skilldb.util.Ensure;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Implementierung eines Interactors für Skills.
 */
public class SkillInteractorImpl extends AbstractInteractor<Skill> implements SkillInteractor {

    private final SkillRepository skillRepository;

    /**
     * Erzeugt ein Objekt der Klasse.
     *
     * @param skillRepository
     *            Repository für den Datenbankzugriff
     */
    @Autowired
    protected SkillInteractorImpl(final SkillRepository skillRepository) {
        super(Skill.class, skillRepository);
        this.skillRepository = skillRepository;
    }

    @Override
    public Collection<Skill> findUserSkills(final User user) {
        Ensure.that(user).isNotNull("The user may not be null.");

        return skillRepository.findAllSortByNameAsc(user);
    }

    @Override
    public Collection<Skill> findSubmittedSkills(final User user) {
        Ensure.that(user).isNotNull("The user may not be null");

        return skillRepository.findSubmittedSkills(user);
    }

    @Override
    public Collection<Skill> findByKnowledgeNameAndSkillLevel(final String knowledge, final int skillLevel) {
        Ensure.that(knowledge).isNotBlank("Knowledge may not be blank.");

        return skillRepository.findByKnowledgeNameAndSkillLevel(knowledge, skillLevel);
    }

    @Override
    @Transactional
    public Skill add(final Skill skill) {
        Ensure.that(skill).isNotNull("The skill to add may not be null.");
        Ensure.that(skill.getUser()).isNotNull("The user of the skill to add may not be null");

        checkSkill(skill);
        skill.setStatus(SkillStatus.SUBMITTED);
        Skill existingSkill = skillRepository.findByKnowledgeName(skill.getKnowledge().getName(), skill.getUser()
                .getUserName());
        if (existingSkill != null) {
            throw new IllegalUserArgumentsException("knowledge",
                    "Es existiert bereits ein Skill zum Wissensgebiet %s.", existingSkill.getKnowledge().getName());
        }

        skill.setCreated(new Date());
        return skillRepository.save(skill);
    }

    @Override
    @Transactional
    public Skill update(final Skill skill) throws IllegalArgumentException {
        Ensure.that(skill).isNotNull("Skill may not be null.");
        Ensure.that(skill.getUser()).isNotNull("User of the skill may not be null.");

        checkSkill(skill);

        Skill existingSkill = skillRepository.findOne(skill.getId());
        if (existingSkill == null) {
            skill.setCreated(new Date());
            return skillRepository.save(skill);
        }

        checkUserAndKnowledgeNotChanged(skill, existingSkill);
        changeSkillLevel(skill, existingSkill);

        skill.setLastModified(new Date());
        return skillRepository.save(skill);
    }

    private void changeSkillLevel(final Skill newSkill, final Skill existingSkill) {
        if (!newSkill.getSubmittedSkillLevel().equals(existingSkill.getSkillLevel())) {
            Set<SkillLevel> previousLevel = existingSkill.getPreviousSkillLevel();
            if (existingSkill.getSkillLevel() != null) {
                previousLevel.add(existingSkill.getSkillLevel());
            }
            newSkill.setPreviousSkillLevel(previousLevel);
            newSkill.setStatus(SkillStatus.SUBMITTED);
        }
    }

    private void checkUserAndKnowledgeNotChanged(final Skill newSkill, final Skill oldSkill) {
        if (!newSkill.getUser().equals(oldSkill.getUser())) {
            throw new IllegalArgumentException("Changing the user of the skill is not allowed.");
        }
        if (!newSkill.getKnowledge().equals(oldSkill.getKnowledge())) {
            throw new IllegalUserArgumentsException("knowledge", "Das Wissensgebiet darf nicht geändert werden.");
        }
    }

    private void checkSkill(final Skill skill) {
        Map<String, String> errorMap = new HashMap<String, String>();
        if (skill.getKnowledge() == null) {
            errorMap.put("knowledge", "Das Wissensgebiet darf nicht leer sein.");
        }
        if (skill.getSubmittedSkillLevel() == null) {
            errorMap.put("skillLevel", "Das Skill-Level darf nicht leer sein.");
        }
        if (!errorMap.isEmpty()) {
            throw new IllegalUserArgumentsException(errorMap);
        }
    }

    @Override
    public void approve(final Skill skill) {
        Ensure.that(skill).isNotNull();

        SkillLevel submittedSkillLevel = skill.getSubmittedSkillLevel();
        skill.setSkillLevel(submittedSkillLevel);
        skill.setSubmittedSkillLevel(null);
        skill.setStatus(SkillStatus.APPROVED);

        skillRepository.save(skill);
    }

}

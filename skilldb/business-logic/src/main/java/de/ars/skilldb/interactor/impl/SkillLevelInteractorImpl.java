/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.interactor.SkillLevelInteractor;
import de.ars.skilldb.repository.SkillLevelRepository;
import de.ars.skilldb.util.Ensure;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Implementierung eines Interactors für Skill-Level.
 */
public class SkillLevelInteractorImpl extends AbstractInteractor<SkillLevel> implements SkillLevelInteractor {

    private final SkillLevelRepository skillLevelRepository;

    /**
     * Erzeugt ein neues Objekt der Klasse SkillLevelInteractorImpl.
     *
     * @param skillLevelRepository
     *            Repository für den Datenbankzugriff
     */
    @Autowired
    public SkillLevelInteractorImpl(final SkillLevelRepository skillLevelRepository) {
        super(SkillLevel.class, skillLevelRepository);
        this.skillLevelRepository = skillLevelRepository;
    }

    @Override
    public SkillLevel findByInternalValue(final int internalValue) {
        Map<String, String> errorMap = new HashMap<String, String>();
        errorMap = checkInternalValue(errorMap, internalValue);
        if (!errorMap.isEmpty()) {
            throw new IllegalUserArgumentsException(errorMap);
        }

        return skillLevelRepository.findByInternalValue(internalValue);
    }

    @Override
    @Transactional
    public SkillLevel add(final SkillLevel skillLevel) {
        checkSkillLevel(skillLevel);

        SkillLevel existingInternalValue = skillLevelRepository.findByInternalValue(skillLevel.getInternalValue());
        SkillLevel existingName = skillLevelRepository.findByNameCaseInsensitive(skillLevel.getName());
        checkExisting(skillLevel, existingInternalValue, existingName);

        skillLevel.setCreated(new Date());
        return skillLevelRepository.save(skillLevel);
    }

    @Override
    @Transactional
    public SkillLevel update(final SkillLevel skillLevel) {
        checkSkillLevel(skillLevel);

        SkillLevel existingInternalValue = skillLevelRepository.findByInternalValueWithoutId(skillLevel.getId(), skillLevel.getInternalValue());
        SkillLevel existingName = skillLevelRepository.findByNameCaseInsensitiveWithoutId(skillLevel.getId(), skillLevel.getName());
        checkExisting(skillLevel, existingInternalValue, existingName);

        skillLevel.setLastModified(new Date());
        return skillLevelRepository.save(skillLevel);
    }

    private void checkSkillLevel(final SkillLevel skillLevel) {
            Ensure.that(skillLevel).isNotNull("Skill-level must not be null.");

            Map<String, String> errorMap = new HashMap<String, String>();

            errorMap = checkInternalValue(errorMap, skillLevel.getInternalValue());
            errorMap = checkName(errorMap, skillLevel.getName());

            if (!errorMap.isEmpty()) {
                throw new IllegalUserArgumentsException(errorMap);
            }
    }

    private Map<String, String> checkInternalValue(final Map<String, String> errorMap, final int internalValue) {
        if (internalValue < 0 || internalValue > 100) {
            errorMap.put("internalValue", "Der interne Wert muss zwischen 0 und 100 liegen.");
        }
        return errorMap;
    }

    private Map<String, String> checkName(final Map<String, String> errorMap, final String name) {
        if (name.trim().isEmpty()) {
            errorMap.put("name", "Der Name darf nicht leer sein.");
        }
        return errorMap;
    }

    private void checkExisting(final SkillLevel skillLevel, final SkillLevel existingInternalValue, final SkillLevel existingName) {
        Map<String, String> errorMap = new HashMap<String, String>();

        if (existingInternalValue != null) {
            errorMap.put("internalValue", String.format("Es existiert bereits ein Skill-Level mit dem internen Wert %s.", skillLevel.getInternalValue()));
        }

        if (existingName != null) {
            errorMap.put("name", String.format("Es existiert bereits ein Skill-Level mit dem Namen %s.", skillLevel.getName()));
        }

        if (!errorMap.isEmpty()) {
            throw new IllegalUserArgumentsException(errorMap);
        }
    }
}

package de.ars.skilldb.repository;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.SkillLevel;

/**
 * Testklasse für das SkillLevel-Repository.
 */
public class SkillLevelRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private SkillLevelRepository skillLevelRepository;

    /**
     * Testet das Finden von SkillLevel anhand des Namens.
     */
    @Test
    @Transactional
    public void testFindByInternalValue() {
        int internalValue = 100;
        SkillLevel expected = generateSkillLevel(internalValue);
        SkillLevel other = generateSkillLevel(10);

        expected.setName("expected Level");

        skillLevelRepository.save(other);
        skillLevelRepository.save(expected);

        SkillLevel actual = skillLevelRepository.findByInternalValue(internalValue);

        assertEquals("Result-SkillLevel is not equal.", expected, actual);
    }

    /**
     * Testet das Finden eines Skill-Level anhand des internen Werts, wobei eine bestimmte ID bei der Suche nicht berücksichtigt wird.
     */
    @Test
    @Transactional
    public void testFindByInternalValueWithoutIdMatch() {
        int internalValue = 50;
        SkillLevel skillLevel = generateSkillLevel(internalValue);
        SkillLevel savedSkill = skillLevelRepository.save(skillLevel);

        SkillLevel resultOtherId = skillLevelRepository.findByInternalValueWithoutId(1000L, internalValue);
        SkillLevel resultNull = skillLevelRepository.findByInternalValueWithoutId(savedSkill.getId(), internalValue);

        assertEquals("Skill-Level is not equal.", savedSkill, resultOtherId);
        assertNull("Skill-Level is not null!", resultNull);
    }

    /**
     * Testet das Finden eines Skill-Level anhand des Namens, wobei eine
     * bestimmte ID bei der Suche nicht berücksichtigt wird.
     */
    @Test
    @Transactional
    public void testFindByNameWithoutIdMatch() {
        SkillLevel skillLevel = generateSkillLevel(50);
        String name = "TestLevel";
        skillLevel.setName(name);
        SkillLevel savedSkill = skillLevelRepository.save(skillLevel);

        SkillLevel resultMatch = skillLevelRepository.findOne(savedSkill.getId());
        SkillLevel resultOtherIdCaseInsensitive = skillLevelRepository.findByNameCaseInsensitiveWithoutId(1000L, name.toLowerCase(Locale.GERMAN));

        assertEquals("Skill-Level is not equal.", savedSkill, resultMatch);
        assertEquals("Skill-Level is not equal.", savedSkill, resultOtherIdCaseInsensitive);

    }

    /**
     * Testet das Finden eines Skill-Level anhand des Namens, wobei eine
     * bestimmte ID bei der Suche nicht berücksichtigt wird.
     * Im Testfall soll kein Skill-Level gefunden werden.
     */
    @Test
    @Transactional
    public void testFindByNameWithoutIdNoMatch() {
        SkillLevel skillLevel = generateSkillLevel(50);
        String name = "TestLevel";
        skillLevel.setName(name);
        SkillLevel savedSkill = skillLevelRepository.save(skillLevel);

        SkillLevel resultNull = skillLevelRepository.findByNameCaseInsensitiveWithoutId(savedSkill.getId(), name);
        SkillLevel resultNullCaseInsensitive = skillLevelRepository.findByNameCaseInsensitiveWithoutId(savedSkill.getId(), name.toLowerCase(Locale.GERMAN));

        assertNull("Skill-Level is not null!", resultNull);
        assertNull("Skill-Level is not null!", resultNullCaseInsensitive);
    }

    private SkillLevel generateSkillLevel(final int internalValue) {
      SkillLevel skillLevel = new SkillLevel();
      skillLevel.setInternalValue(internalValue);
      skillLevel.setName("Wissen");
      skillLevel.setDescription("Erlerntes in unveränderter Weise erkennen,...");
      skillLevel.setTags("erkennen, ...");
      return skillLevel;
    }
}
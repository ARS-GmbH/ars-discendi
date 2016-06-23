package de.ars.skilldb.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.domain.User;

/**
 * Testklasse für das Skill-Repository.
 */
public class SkillRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private KnowledgeRepository knowledgeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkillLevelRepository skillLevelRepository;

    private static final String KNOWLEDGE_NAME = "Java";
    private static final int SKILL_LEVEL = 50;
    private static final String USER_NAME = "alfred.alfredo";

    /**
     * Testet das Finden eines Skills anhand des Wissensgebiets und des Users.
     */
    @Test
    @Transactional
    public void testFindByKnowledgeName() {
        Skill skill = generateSkill(KNOWLEDGE_NAME, SKILL_LEVEL, USER_NAME);

        Skill expected = skillRepository.save(skill);

        Skill actual = skillRepository.findByKnowledgeName(KNOWLEDGE_NAME, USER_NAME);

        assertEquals("Result-Skill is not equal.", expected, actual);
    }

    /**
     * Testet das Finden aller Skills eines Mitarbeiters alphabetisch sortiert
     * nach Name des Wissensgebiets, wenn kein Skill gefunden wurde.
     */
    @Test
    @Transactional
    public void testFindAllSortByNameAscNoMatch() {
        Collection<Skill> expected = new ArrayList<Skill>();
        User user = generateUser(USER_NAME);

        Collection<Skill> actual = skillRepository.findAllSortByNameAsc(user);

        assertEquals("Search-Result is not equal.", expected, actual);
    }

    /**
     * Testet das Finden aller Skills zu einem Wissensgebiet und einem
     * Mindestlevel des Skills.
     */
    @Test
    @Transactional
    public void testFindByKnowledgeNameAndSkillLevel() {
        Skill match1 = generateSkill(KNOWLEDGE_NAME, SKILL_LEVEL, USER_NAME);
        Skill expected1 = skillRepository.save(match1);

        Skill match2 = generateSkill(KNOWLEDGE_NAME, 75, USER_NAME);
        Skill expected2 = skillRepository.save(match2);

        Skill noMatch1 = generateSkill(KNOWLEDGE_NAME, 0, USER_NAME);
        skillRepository.save(noMatch1);

        Skill noMatch2 = generateSkill("JavaScript", SKILL_LEVEL, USER_NAME);
        skillRepository.save(noMatch2);

        Collection<Skill> expected = new ArrayList<Skill>();
        expected.add(expected1);
        expected.add(expected2);

        Collection<Skill> actual = skillRepository.findByKnowledgeNameAndSkillLevel(KNOWLEDGE_NAME, 50);

        assertEquals("Found skill-collection is not equal.", expected, actual);
    }

    private Skill generateSkill(final String knowledgeName, final int skillLevel, final String userName) {
        Skill skill = new Skill();
        skill.setKnowledge(generateKnowledge(knowledgeName));
        skill.setSkillLevel(generateSkillLevel(skillLevel));
        skill.setUser(generateUser(userName));
        return skill;
    }

    private Knowledge generateKnowledge(final String knowledgeName) {
        Knowledge knowledge = new Knowledge();
        knowledge.setName(knowledgeName);
        knowledge.setDescription("PhoneGap halt.");
        knowledgeRepository.save(knowledge);
        return knowledge;
    }

    private SkillLevel generateSkillLevel(final int internalValue) {
      SkillLevel skillLevel = new SkillLevel();
      skillLevel.setInternalValue(internalValue);
      skillLevel.setName("Anwendung");
      skillLevel.setDescription("Erlernte Strukturen in ähnlichen Situationen anwenden");
      skillLevel.setTags("abschätzen, anknüpfen,...");
      skillLevelRepository.save(skillLevel);
      return skillLevel;
    }

    private User generateUser(final String userName) {
        User user = new User();
        user.setFirstName("Alfred");
        user.setLastName("Alfredo");
        user.setUserName(userName);
        userRepository.save(user);
        return user;
    }
}
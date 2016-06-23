/**
 *
 */
package de.ars.skilldb.interactor.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.SkillInteractor;
import de.ars.skilldb.repository.SkillRepository;
import de.ars.skilldb.util.AssertionFailedException;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Testklasse zum Skill-Interactor.
 *
 */
public class SkillInteractorImplTest {

    private SkillInteractor interactor;
    private SkillRepository skillRepository;
    private static final String KNOWLEDGE_NAME = "Java";
    private static final String USERNAME = "alfred.alfredo";
    private static final int SKILL_LEVEL = 50;

    /**
     * Initiales erzeugen von Objekten, die in den meisten Testfällen benötigt werden.
     */
    @Before
    public void initObjects() {
        skillRepository = Mockito.mock(SkillRepository.class);
        interactor = new SkillInteractorImpl(skillRepository);
        ReflectionTestUtils.setField(interactor, "skillRepository", skillRepository);
    }

    /**
     * Testet, das Finden aller Skills eines Users.
     */
    @Test
    public void testFindUserSkillsSortedByKnowledgeNameAsc() {
        Collection<Skill> userSkills = new ArrayList<Skill>();
        userSkills.add(generateSkill());

        Skill other = generateSkill();
        Knowledge knowledge = new Knowledge();
        knowledge.setName("CSS");
        other.setKnowledge(knowledge);
        userSkills.add(other);

        User user = generateUser();
        Mockito.when(skillRepository.findAllSortByNameAsc(user)).thenReturn(userSkills);

        Collection<Skill> actual = interactor.findUserSkills(user);

        assertEquals("Response is not equal.", userSkills, actual);
    }

    /**
     * Testet, ob beim Finden aller Skills eines Users eine Exception geworfen
     * wird, wenn der User null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testFindUserSkillsSortedByKnowledgeNameAscUserNull() {
        interactor.findUserSkills(null);
    }

    /**
     * Testet das Finden aller Skills anhand eines Knowledgenamens und eines
     * Mindestlevels des Skills.
     */
    @Test
    public void testFindByKnowledgeNameAndSkillLevel() {
        Collection<Skill> expected = new ArrayList<Skill>();
        expected.add(generateSkill());
        Mockito.when(skillRepository.findByKnowledgeNameAndSkillLevel(KNOWLEDGE_NAME, SKILL_LEVEL)).thenReturn(expected);

        Collection<Skill> actual = interactor.findByKnowledgeNameAndSkillLevel(KNOWLEDGE_NAME, SKILL_LEVEL);

        assertEquals("Response is not equal.", expected, actual);
    }

    /**
     * Testet das Finden aller Skills anhand eines Knowledge-Namens und eines
     * Mindestlevels des Skills, wenn das Wissensgebiet leer ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testFindByKnowledgeNameAndSkillLevelKnowledge() {
        interactor.findByKnowledgeNameAndSkillLevel("", SKILL_LEVEL);
    }

    /**
     * Testet das Finden aller Skills anhand eines Knowledge-Namens und eines
     * Mindestlevels des Skills, wenn die Suche keinen Treffer liefert.
     */
    @Test
    public void testFindByKnowledgeNameAndSkillLevelNoMatch() {
        Mockito.when(skillRepository.findByKnowledgeNameAndSkillLevel(KNOWLEDGE_NAME, SKILL_LEVEL)).thenReturn(null);

        Collection<Skill> actual = interactor.findByKnowledgeNameAndSkillLevel(KNOWLEDGE_NAME, SKILL_LEVEL);

        assertNull("Response is not equal.", actual);
    }

    /**
     * Testet das Anlegen eines neuen Skills.
     */
    @Test
    public void testAdd() {
        Skill skill = generateSkill();
        Mockito.when(skillRepository.findByKnowledgeName(KNOWLEDGE_NAME, USERNAME)).thenReturn(null);
        Mockito.when(skillRepository.save(skill)).thenReturn(skill);

        Skill actual = interactor.add(skill);

        assertEquals("Response is not equal.", skill, actual);
    }

    /**
     * Testet das Anlegen eines neuen Skills wenn der Skill null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testAddNull() {
        interactor.add(null);
    }

    /**
     * Testet das Anlegen eines neuen Skills wenn der User null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testAddUserNull() {
        Skill skill = generateSkill();
        skill.setUser(null);

        interactor.add(skill);
    }

    /**
     * Testet das Anlegen eines neuen Skills wenn das Wissensgebiet null ist.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddKnowledgeNull() {
        Mockito.when(skillRepository.findByKnowledgeName(KNOWLEDGE_NAME, USERNAME)).thenReturn(null);
        Skill skill = generateSkill();
        skill.setKnowledge(null);

        interactor.add(skill);
    }

    /**
     * Testet das Anlegen eines neuen Skills wenn das beantragte Skill-Level null ist.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddSkillLevelNull() {
        Skill skill = generateSkill();
        skill.setSubmittedSkillLevel(null);

        interactor.add(skill);
    }

    /**
     * Testet das Anlegen eines neuen Skills wenn dieser bereits existiert.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddExisting() {
        Skill skill = generateSkill();
        Mockito.when(skillRepository.findByKnowledgeName(KNOWLEDGE_NAME, USERNAME)).thenReturn(skill);

        interactor.add(skill);
    }

    /**
     * Testet das Updaten eines Skills.
     */
    @Test
    public void testUpdate() {
        Skill oldSkill = generateSkill();

        Skill newSkill = generateSkill();
        newSkill.setSkillLevel(generateSkillLevel(30));
        newSkill.setId(1L);

        Skill expected = generateSkill();
        Set<SkillLevel> prevLevel = new HashSet<SkillLevel>();
        prevLevel.add(oldSkill.getSkillLevel());
        expected.setPreviousSkillLevel(prevLevel);
        expected.setSkillLevel(generateSkillLevel(30));

        Mockito.when(skillRepository.findOne(Matchers.anyLong())).thenReturn(oldSkill);
        Mockito.when(skillRepository.save(newSkill)).thenReturn(expected);

        Skill actual = interactor.update(newSkill);

        assertEquals("Skill is not equal.", expected, actual);
    }

    /**
     * Testet das Updaten eines Skills wenn der übergebene Skill null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testUpdateNull() {
        interactor.update(null);
    }

    /**
     * Testet das Updaten eines Skills wenn der User null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testUpdateUserNull() {
        Skill skill = generateSkill();
        skill.setUser(null);
        interactor.update(skill);
    }

    /**
     * Testet das Updaten eines Skills wenn das Wissensgebiet null ist.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testUpdateKnowledgeNull() {
        Skill skill = generateSkill();
        skill.setKnowledge(null);
        interactor.update(skill);
    }

    /**
     * Testet das Updaten eines Skills wenn das beantragte Skill-Level null ist.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testUpdateSkillLevelNull() {
        Skill skill = generateSkill();
        skill.setSubmittedSkillLevel(null);
        interactor.update(skill);
    }

    /**
     * Testet das Updaten eines Skills wenn kein Wissensgebiet existiert das geändert werden kann.
     */
    @Test
    public void testUpdateExistingSkillNull() {
        Skill skill = generateSkill();
        skill.setId(1L);
        Mockito.when(skillRepository.findOne(Matchers.anyLong())).thenReturn(null);
        Mockito.when(skillRepository.save(skill)).thenReturn(skill);

        Skill actual = interactor.update(skill);

        assertEquals("Skill is not equal.", skill, actual);
    }

    /**
     * Testet das Updaten eines Skills wenn der Benutzer geändert wird.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testUpdateSkillUserChanged() {
        Skill skill = generateSkill();
        skill.setId(1L);

        Skill skillOtherUser = generateSkill();
        User otherUser = generateUser();
        otherUser.setUserName("other.Name");
        skillOtherUser.setUser(otherUser);

        Mockito.when(skillRepository.findOne(Matchers.anyLong())).thenReturn(skillOtherUser);

        interactor.update(skill);
    }

    /**
     * Testet das Updaten eines Skills wenn das Wissensgebiet geändert wird.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testUpdateKnowledgeChanged() {
        Skill skill = generateSkill();
        skill.setId(1L);

        Skill skillOtherKnowledge = generateSkill();
        Knowledge otherKnowledge = generateKnowledge();
        otherKnowledge.setName("otherName");
        skillOtherKnowledge.setKnowledge(otherKnowledge);

        Mockito.when(skillRepository.findOne(Matchers.anyLong())).thenReturn(skillOtherKnowledge);

        interactor.update(skill);
    }

    /**
     * Testet das Updaten eines Skills wenn sich das Skill-Level nicht ändert.
     */
    @Test
    public void testUpdateSkillLevelNotChanged() {
        Skill skill = generateSkill();
        skill.setId(1L);

        Mockito.when(skillRepository.findOne(Matchers.anyLong())).thenReturn(skill);
        Mockito.when(skillRepository.save(skill)).thenReturn(skill);

        Skill actual = interactor.update(skill);

        assertEquals("Skill is not equal.", skill, actual);
    }

    private Skill generateSkill() {
        Skill skill = new Skill();
        skill.setKnowledge(generateKnowledge());
        skill.setSubmittedSkillLevel(generateSkillLevel(10));
        skill.setUser(generateUser());
        return skill;
    }

    private Knowledge generateKnowledge() {
        Knowledge knowledge = new Knowledge();
        knowledge.setName(KNOWLEDGE_NAME);
        knowledge.setDescription("Java halt.");
        return knowledge;
    }

    private SkillLevel generateSkillLevel(final int internalValue) {
      SkillLevel skillLevel = new SkillLevel();
      skillLevel.setInternalValue(internalValue);
      skillLevel.setName("Wissen");
      skillLevel.setDescription("Erlerntes in unveränderter Weise erkennen,...");
      skillLevel.setTags("erkennen, ...");
      return skillLevel;
    }

    private User generateUser() {
        User user = new User();
        user.setFirstName("Alfred");
        user.setLastName("Alfredo");
        user.setUserName(USERNAME);
        return user;
    }

}

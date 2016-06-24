/**
 *
 */
package de.ars.skilldb.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.KnowledgeInteractor;
import de.ars.skilldb.interactor.SkillInteractor;
import de.ars.skilldb.interactor.SkillLevelInteractor;
import de.ars.skilldb.interactor.UserInteractor;

/**
 * Testet die Klasse SearchController.
 */
public class SearchControllerTest {

    private SearchController controller;
    private ModelMap model;
    private SkillInteractor skillInteractor;
    private KnowledgeInteractor knowledgeInteractor;
    private SkillLevelInteractor skillLevelInteractor;
    private UserInteractor userInteractor;
    private static final String USERNAME = "alfred.alfredo";
    private static final String KNOWLEDGE_NAME = "Java";
    private static final String LEVEL = "10";
    private static final String NO_SKILLS_FOUND = "Es wurden keine Skills gefunden.";
    private static final String BASE_URL = "/search/";
    private static final String USER = BASE_URL + "user";
    private static final String SKILLS = BASE_URL + "skills";
    private Collection<Knowledge> allKnowledges;
    private Collection<SkillLevel> allSkillLevel;
    private Collection<User> allUsers;


    /**
     * Erstellt alle Objekte, die in den meisten Tests benötigt werden.
     */
    @Before
    public void initObjects() {
        controller = new SearchController();
        model = Mockito.mock(ModelMap.class);
        skillInteractor = Mockito.mock(SkillInteractor.class);
        knowledgeInteractor = Mockito.mock(KnowledgeInteractor.class);
        skillLevelInteractor = Mockito.mock(SkillLevelInteractor.class);
        userInteractor = Mockito.mock(UserInteractor.class);
        ReflectionTestUtils.setField(controller, "skillInteractor", skillInteractor);
        ReflectionTestUtils.setField(controller, "knowledgeInteractor", knowledgeInteractor);
        ReflectionTestUtils.setField(controller, "skillLevelInteractor", skillLevelInteractor);
        ReflectionTestUtils.setField(controller, "userInteractor", userInteractor);
        generateAllKnowledges();
        generateAllSkillLevel();
        generateAllUsers();
    }

    /**
     * Testet das Befüllen der Suche nach Mitarbeitern anhand eines Wissensgebiets und Skill-Levels.
     */
    @Test
    public void testSearchForUsersByKnowledgeOpenSearch() {
        String knowledge = "";
        String level = "";

        String actual = controller.searchForUsersByKnowledge(model, knowledge, level);

        checkSetAllKnowledgesToModel(knowledge);
        checkSetAllSkillLevelToModel(level);
        checkVisibilityHidden();
        assertEquals("Returned view is not equal.", USER, actual);
    }

    /**
     * Testet die Suche nach Benutzern anhand eines Wissensgebiets und eines Mindestlevels.
     */
    @Test
    public void testSearchForUsersByKnowledge() {
        Collection<Skill> foundSkills = generateAllSkills();
        Mockito.when(skillInteractor.findByKnowledgeNameAndSkillLevel(Matchers.anyString(), Matchers.anyInt())).thenReturn(foundSkills);
        String actual = controller.searchForUsersByKnowledge(model, KNOWLEDGE_NAME, LEVEL);

        checkSetAllKnowledgesToModel(KNOWLEDGE_NAME);
        checkSetAllSkillLevelToModel(LEVEL);
        Mockito.verify(model, Mockito.times(1)).addAttribute("skills", foundSkills);
        assertEquals("Returned view is not equal.", USER, actual);
    }

    /**
     * Testet die Suche nach Benutzern anhand eines Wissensgebiets und eines
     * Mindestlevels wenn das Level leer ist.
     */
    @Test
    public void testSearchForUsersByKnowledgeLevelEmpty() {
        Collection<Skill> foundSkills = generateAllSkills();
        Mockito.when(skillInteractor.findByKnowledgeNameAndSkillLevel(Matchers.anyString(), Matchers.anyInt())).thenReturn(foundSkills);
        String actual = controller.searchForUsersByKnowledge(model, KNOWLEDGE_NAME, "");

        checkSetAllKnowledgesToModel(KNOWLEDGE_NAME);
        checkSetAllSkillLevelToModel("");
        Mockito.verify(model, Mockito.times(1)).addAttribute("skills", foundSkills);
        assertEquals("Returned view is not equal.", USER, actual);
    }

    /**
     * Testet die Suche nach Benutzern anhand eines Wissensgebiets und eines
     * Mindestlevels wenn das Level einen Buchstaben enthält.
     */
    @Test
    public void testSearchForUsersByKnowledgeLevelIsLetter() {
        String actual = controller.searchForUsersByKnowledge(model, KNOWLEDGE_NAME, "d");

        Mockito.verify(model, Mockito.times(1)).addAttribute("error", "Das Skill-Level muss eine Zahl oder leer sein.");
        checkVisibilityHidden();
        assertEquals("Returned view is not equal.", USER, actual);
    }

    /**
     * Testet die Suche nach Benutzern anhand eines Wissensgebiets und eines
     * Mindestlevels wenn die Suche keinen Treffer ergab.
     */
    @Test
    public void testSearchForUsersByKnowledgeLevelNoMatch() {
        Collection<Skill> emptyList = new ArrayList<Skill>();
        Mockito.when(skillInteractor.findByKnowledgeNameAndSkillLevel(Matchers.anyString(), Matchers.anyInt())).thenReturn(emptyList);

        String actual = controller.searchForUsersByKnowledge(model, KNOWLEDGE_NAME, LEVEL);

        checkSetAllKnowledgesToModel(KNOWLEDGE_NAME);
        checkSetAllSkillLevelToModel(LEVEL);
        Mockito.verify(model, Mockito.times(1)).addAttribute("error", NO_SKILLS_FOUND);
        checkVisibilityHidden();
        assertEquals("Returned view is not equal.", USER, actual);
    }


    /**
     * Testet das Befüllen der View für die Suche nach Skills eines Benutzers,
     * wenn die UserID leer ist.
     */
    @Test
    public void testSearchForSkillsByUserNoUserId() {
        String actual = controller.searchForSkillsByUser(model, "");

        checkSetAllUsers("");
        assertEquals("Returned view is not equal.", SKILLS, actual);
    }

    /**
     * Testet das Befüllen der View für die Suche nach Skills eines Benutzers,
     * wenn die ID nicht vom Datentyp Long ist.
     */
    @Test
    public void testSearchForSkillsByUserIdNotALong() {
        String id = "kein Long";

        String actual = controller.searchForSkillsByUser(model, id);

        checkSetAllUsers(id);
        Mockito.verify(model, Mockito.times(1)).addAttribute("error", "Die übergebene ID ist nicht valide.");
        checkVisibilityHidden();
        assertEquals("Returned view is not equal.", SKILLS, actual);
    }

    /**
     * Testet das Befüllen der View für die Suche nach Skills eines Benutzers,
     * wenn dieser noch keinen Skill angelegt hat.
     */
    @Test
    public void testSearchForSkillsByUserNoSkillsExisting() {
        User searchedUser = generateUser(1L);
        Collection<Skill> allSkills = new ArrayList<Skill>();

        Mockito.when(userInteractor.findOne(Matchers.anyLong())).thenReturn(searchedUser);
        Mockito.when(skillInteractor.findUserSkills(searchedUser)).thenReturn(allSkills);

        String actual = controller.searchForSkillsByUser(model, searchedUser.getId().toString());

        checkSetAllUsers(searchedUser.getId().toString());
        Mockito.verify(model, Mockito.times(1)).addAttribute("error", NO_SKILLS_FOUND);
        checkVisibilityHidden();
        assertEquals("Returned view is not equal.", SKILLS, actual);
    }

    private Skill generateSkill() {
        Skill skill = new Skill();
        skill.setKnowledge(generateKnowledge());
        skill.setSkillLevel(generateSkillLevel());
        skill.setUser(generateUser(1L));
        return skill;
    }

    private Knowledge generateKnowledge() {
        Knowledge knowledge = new Knowledge();
        knowledge.setName(KNOWLEDGE_NAME);
        knowledge.setDescription("Java halt.");
        return knowledge;
    }

    private SkillLevel generateSkillLevel() {
      SkillLevel skillLevel = new SkillLevel();
      skillLevel.setInternalValue(10);
      skillLevel.setName("Wissen");
      skillLevel.setDescription("Erlerntes in unveränderter Weise erkennen,...");
      skillLevel.setTags("erkennen, ...");
      return skillLevel;
    }

    private User generateUser(final Long id) {
        User user = new User();
        user.setFirstName("Alfred");
        user.setLastName("Alfredo");
        user.setUserName(USERNAME);
        user.setId(id);
        return user;
    }

    private void generateAllSkillLevel() {
        allSkillLevel = new ArrayList<SkillLevel>();
        allSkillLevel.add(generateSkillLevel());
        Sort sort = new Sort(Sort.Direction.ASC, "internalValue");

        Mockito.when(skillLevelInteractor.findAll(sort)).thenReturn(allSkillLevel);
    }

    private Collection<Skill> generateAllSkills() {
        Collection<Skill> allSkills = new ArrayList<Skill>();
        Skill skill = generateSkill();
        allSkills.add(skill);
        return allSkills;
    }

    private void generateAllUsers() {
        allUsers = new ArrayList<User>();
        allUsers.add(generateUser(1L));
        Sort sort = new Sort(Sort.Direction.ASC, "lastName");
        Mockito.when(userInteractor.findAll(sort)).thenReturn(allUsers);
    }

    private void generateAllKnowledges() {
        allKnowledges = new ArrayList<Knowledge>();
        allKnowledges.add(generateKnowledge());
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        Mockito.when(knowledgeInteractor.findAll(sort)).thenReturn(allKnowledges);
    }

    private void checkSetAllSkillLevelToModel(final String selectedLevel) {
        Mockito.verify(model, Mockito.times(1)).addAttribute("allSkillLevel", allSkillLevel);
        Mockito.verify(model, Mockito.times(1)).addAttribute("selectedLevel", selectedLevel);
    }

    private void checkSetAllKnowledgesToModel(final String selectedKnowledge) {
        Mockito.verify(model, Mockito.times(1)).addAttribute("allKnowledges", allKnowledges);
        Mockito.verify(model, Mockito.times(1)).addAttribute("selectedKnowledge", selectedKnowledge);
    }

    private void checkVisibilityHidden() {
        Mockito.verify(model, Mockito.times(1)).addAttribute("tableVisibility", "hidden");
    }

    private void checkSetAllUsers(final String currentUserId) {
        Mockito.verify(model, Mockito.times(1)).addAttribute("allUsers", allUsers);
        Mockito.verify(model, Mockito.times(1)).addAttribute("selectedUser", currentUserId);
    }
}

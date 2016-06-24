/**
 *
 */
package de.ars.skilldb.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.KnowledgeInteractor;
import de.ars.skilldb.interactor.SkillInteractor;
import de.ars.skilldb.interactor.SkillLevelInteractor;
import de.ars.skilldb.interactor.UserInteractor;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Testet die Klasse SkillController.
 */
public class SkillControllerTest {

    private SkillController controller;
    private ModelMap model;
    private SkillInteractor skillInteractor;
    private UserInteractor userInteractor;
    private KnowledgeInteractor knowledgeInteractor;
    private SkillLevelInteractor skillLevelInteractor;
    private BindingResult result;
    private RedirectAttributes redirectAttributes;
    private static final String USERNAME = "alfred.alfredo";
    private static final String KNOWLEDGE_NAME = "Java";
    private static final String BASE_URL = "/skills/";
    private static final String LIST_ALL_SKILLS = BASE_URL + "list";
    private static final String CREATE_SKILL = BASE_URL + "create";
    private static final String EDIT_SKILL = BASE_URL + "edit";
    private static final String REDIRECT = "redirect:";
    private static final String ERROR_MESSAGE = "Es existiert bereits ein Wissensgebiet mit dem Namen test.";
    private static final Long USER_ID = 1L;
    private Collection<Knowledge> allKnowledges;
    private Collection<SkillLevel> allSkillLevel;

    /**
     * Erstellt alle Objekte, die in den meisten Tests benötigt werden.
     */
    @Before
    public void initObjects() {
        controller = new SkillController();
        model = Mockito.mock(ModelMap.class);
        skillInteractor = Mockito.mock(SkillInteractor.class);
        userInteractor = Mockito.mock(UserInteractor.class);
        knowledgeInteractor = Mockito.mock(KnowledgeInteractor.class);
        skillLevelInteractor = Mockito.mock(SkillLevelInteractor.class);
        ReflectionTestUtils.setField(controller, "skillInteractor", skillInteractor);
        ReflectionTestUtils.setField(controller, "userInteractor", userInteractor);
        ReflectionTestUtils.setField(controller, "knowledgeInteractor", knowledgeInteractor);
        ReflectionTestUtils.setField(controller, "skillLevelInteractor", skillLevelInteractor);
        result = Mockito.mock(BindingResult.class);
        redirectAttributes = Mockito.mock(RedirectAttributes.class);
        generateAllKnowledges();
        generateAllSkillLevel();
    }

    /**
     * Testet das Updaten eines Skills.
     */
    @Test
    public void testUpdate() {
        Skill skill = generateSkill();
        Mockito.when(result.hasErrors()).thenReturn(false);
        Mockito.when(skillInteractor.update(skill)).thenReturn(skill);

        String actual = controller.update(USER_ID, skill, result, model, redirectAttributes);

        checkSuccessFlashMessage(skill.getKnowledge().getName() + " wurde erfolgreich geändert.");
        assertEquals("Returned view is not equal.", REDIRECT + "/users/" + USER_ID + BASE_URL, actual);
    }

    /**
     * Testet das Updaten eines Skills wenn das BindingResult-Objekt einen
     * Fehler entählt.
     */
    @Test
    public void testUpdateResultError() {
        Skill skill = generateSkill();
        Mockito.when(result.hasErrors()).thenReturn(true);

        String actual = controller.update(USER_ID, skill, result, model, redirectAttributes);

        checkSetSkillToModel(skill);
        checkSetAllKnowledgesToModel();
        checkSetAllSkillLevelToModel();
        checkSetKnowledgeDisabledToModel();
        assertEquals("Returned view is not equal.", EDIT_SKILL, actual);
    }

    /**
     * Testet das Updaten eines Skills wenn eine {@link IllegalUserArgumentsException}
     * gefangen wird.
     */
    @Test
    public void testUpdateIllegalUserArgumentsException() {
        Skill skill = generateSkill();
        Mockito.when(result.hasErrors()).thenReturn(false);
        Mockito.when(skillInteractor.update(skill)).thenThrow(new IllegalUserArgumentsException("knowledge", ERROR_MESSAGE));

        String actual = controller.update(USER_ID, skill, result, model, redirectAttributes);

        checkSetAllKnowledgesToModel();
        checkSetAllSkillLevelToModel();
        checkSetSkillToModel(skill);
        checkSetKnowledgeDisabledToModel();
        checkErrorMessageModel(ERROR_MESSAGE + "<br/>");
        Mockito.verify(model, Mockito.times(1)).addAttribute("knowledgeError", true);
        assertEquals("Returned view is not equal.", EDIT_SKILL, actual);
    }

    private Skill generateSkill() {
        Skill skill = new Skill();
        skill.setKnowledge(generateKnowledge());
        skill.setSkillLevel(generateSkillLevel());
        skill.setUser(generateUser());
        skill.setCreated(new Date());
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

    private User generateUser() {
        User user = new User();
        user.setFirstName("Alfred");
        user.setLastName("Alfredo");
        user.setUserName(USERNAME);
        return user;
    }

    private void generateAllSkillLevel() {
        allSkillLevel = new ArrayList<SkillLevel>();
        allSkillLevel.add(generateSkillLevel());
        Sort sort = new Sort(Sort.Direction.ASC, "internalValue");

        Mockito.when(skillLevelInteractor.findAll(sort)).thenReturn(allSkillLevel);
    }

    private void generateAllKnowledges() {
        allKnowledges = new ArrayList<Knowledge>();
        allKnowledges.add(generateKnowledge());
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        Mockito.when(knowledgeInteractor.findAll(sort)).thenReturn(allKnowledges);
    }

    private void checkSetAllSkillLevelToModel() {
        Mockito.verify(model, Mockito.times(1)).addAttribute("allSkillLevel", allSkillLevel);
    }

    private void checkSetAllKnowledgesToModel() {
        Mockito.verify(model, Mockito.times(1)).addAttribute("allKnowledges", allKnowledges);
    }

    private void checkSetSkillToModel(final Skill skill) {
        Mockito.verify(model, Mockito.times(1)).addAttribute("skill", skill);
    }

    private void checkSetKnowledgeDisabledToModel() {
        Mockito.verify(model, Mockito.times(1)).addAttribute("knowledgeDisabled", true);
    }

    private void checkErrorMessageModel(final String message) {
        Mockito.verify(model, Mockito.times(1)).addAttribute("errorMessage", message);
    }

    private void checkErrorFlashMessage(final String message) {
        Mockito.verify(redirectAttributes, Mockito.times(1)).addFlashAttribute("errorMessage", message);
    }

    private void checkSuccessFlashMessage(final String message) {
        Mockito.verify(redirectAttributes, Mockito.times(1)).addFlashAttribute("successMessage", message);
    }

}

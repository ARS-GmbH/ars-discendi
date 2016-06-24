/**
 *
 */
package de.ars.skilldb.controller;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.interactor.SkillLevelInteractor;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Testet die Klasse SkillLevelController.
 */
public class SkillLevelControllerTest {

    private SkillLevelController controller;
    private SkillLevelInteractor skillLevelInteractor;
    private ModelMap model;
    private BindingResult result;
    private RedirectAttributes redirectAttributes;

    private static final String BASE_URL = "/skilllevel/";
    private static final String CREATE_SKILL_LEVEL = BASE_URL + "create";
    private static final String LIST_ALL_SKILL_LEVEL = BASE_URL + "list";
    private static final String EDIT_SKILL_LEVEL = BASE_URL + "edit";
    private static final String INFO = BASE_URL + "info";
    private static final String REDIRECT = "redirect:";

    private static final String NAME_ERROR = "Es existiert bereits ein Skill-Level mit dem Namen test";

    /**
     * Initialisiert die Objekte.
     */
    @Before
    public void initObjects() {
        controller = new SkillLevelController();
        model = Mockito.mock(ModelMap.class);
        skillLevelInteractor = Mockito.mock(SkillLevelInteractor.class);
        ReflectionTestUtils.setField(controller, "skillLevelInteractor", skillLevelInteractor);
        result = Mockito.mock(BindingResult.class);
        redirectAttributes = Mockito.mock(RedirectAttributes.class);
    }

    /**
     * Testet, ob die Methode zum Auflisten aller Skill-Level die View richtig
     * befüllt.
     */
    @Test
    public void testGetAll() {
        Collection<SkillLevel> allSkillLevel = new ArrayList<SkillLevel>();
        allSkillLevel.add(generateSkillLevel(10));
        allSkillLevel.add(generateSkillLevel(30));
        Sort sort = new Sort(Sort.Direction.ASC, "internalValue");
        Mockito.when(skillLevelInteractor.findAll(sort)).thenReturn(allSkillLevel);

        String actual = controller.getAll(model);

        Mockito.verify(model, Mockito.times(1)).addAttribute("allSkillLevel", allSkillLevel);
        assertEquals("Returned view is not equal.", LIST_ALL_SKILL_LEVEL, actual);
    }

    /**
     * Testet, ob die Methode zum Befüllen der Info-Tabelle die View richtig
     * befüllt.
     */
    @Test
    public void testGetInfo() {
        ArrayList<SkillLevel> allLevel = new ArrayList<SkillLevel>();
        allLevel.add(generateSkillLevel(10));
        Sort sort = new Sort(Sort.Direction.ASC, "internalValue");
        Mockito.when(skillLevelInteractor.findAll(sort)).thenReturn(allLevel);

        String actual = controller.getInfo(model);

        Mockito.verify(model).addAttribute("allSkillLevel", allLevel);
        assertEquals("Returned view is not equal.", INFO, actual);
    }

    /**
     * Testet das Befüllen der View zum Anlegen eines Skill-Levels mit einem leeren Skill-Level.
     */
    @Test
    public void testCreate() {
        String actual = controller.create(model);

        Mockito.verify(model, Mockito.times(1)).addAttribute("skillLevel", new SkillLevel());
        assertEquals("Returned view is not equal.", CREATE_SKILL_LEVEL, actual);
    }

    /**
     * Testet das Hinzufügen eines neuen Skill-Levels.
     */
    @Test
    public void testAdd() {
        SkillLevel skillLevel = generateSkillLevel(10);

        Mockito.when(result.hasErrors()).thenReturn(false);
        Mockito.when(skillLevelInteractor.add(skillLevel)).thenReturn(skillLevel);

        String actual = controller.add(skillLevel, result, model, redirectAttributes);

        Mockito.verify(redirectAttributes, Mockito.times(1)).addFlashAttribute("successMessage",
                skillLevel.getInternalValue() + " " + skillLevel.getName() + " wurde erfolgreich angelegt.");
        assertEquals("Returned view is not equal.", REDIRECT + BASE_URL, actual);
    }

    /**
     * Testet das Hinzufügen eines neuen Skill-Levels wenn beim DataBinding ein
     * Fehler auftritt.
     */
    @Test
    public void testAddResultError() {
        SkillLevel skillLevel = generateSkillLevel(10);
        Mockito.when(result.hasErrors()).thenReturn(true);

        String actual = controller.add(skillLevel, result, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("skillLevel", skillLevel);
        assertEquals("Returned view is not equal.", CREATE_SKILL_LEVEL, actual);
    }

    /**
     * Testet das Hinzufügen eines neuen Skill-Levels, wenn eine
     * IllegalUserArgumentsException gefangen wird.
     */
    @Test
    public void testAddIllegalUserArgumentsException() {
        SkillLevel skillLevel = generateSkillLevel(10);
        Mockito.when(result.hasErrors()).thenReturn(false);
        Mockito.when(skillLevelInteractor.add(skillLevel)).thenThrow(new IllegalUserArgumentsException(generateErrorMap()));

        String actual = controller.add(skillLevel, result, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("skillLevel", skillLevel);
        Mockito.verify(model, Mockito.times(1)).addAttribute("errorMessage", NAME_ERROR + "<br/>");
        Mockito.verify(model, Mockito.times(1)).addAttribute("nameError", true);
        assertEquals("Returned view is not equal.", CREATE_SKILL_LEVEL, actual);
    }

    /**
     * Testet das Befüllen des Formulars zum Ändern eines Skill-Levels.
     */
    @Test
    public void testEdit() {
        SkillLevel skillLevel = generateSkillLevel(10);
        Mockito.when(skillLevelInteractor.findOne(Matchers.anyLong())).thenReturn(skillLevel);
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy kk:mm", Locale.GERMAN);

        String actual = controller.edit(1L, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("skillLevel", skillLevel);
        Mockito.verify(model, Mockito.times(1)).addAttribute("created", formatter.format(skillLevel.getCreated()));
        assertEquals("Returned view is not equal.", EDIT_SKILL_LEVEL, actual);
    }

    /**
     * Testet das Befüllen des Formulars zum Ändern eines Skill-Levels wenn das
     * Datum für lastModified bereits gesetzt ist.
     */
    @Test
    public void testEditLastModified() {
        SkillLevel skillLevel = generateSkillLevel(10);
        skillLevel.setLastModified(new Date());
        Mockito.when(skillLevelInteractor.findOne(Matchers.anyLong())).thenReturn(skillLevel);
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy kk:mm", Locale.GERMAN);

        String actual = controller.edit(1L, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("skillLevel", skillLevel);
        Mockito.verify(model, Mockito.times(1)).addAttribute("created", formatter.format(skillLevel.getCreated()));
        Mockito.verify(model, Mockito.times(1)).addAttribute("lastModified", formatter.format(skillLevel.getLastModified()));
        assertEquals("Returned view is not equal.", EDIT_SKILL_LEVEL, actual);
    }

    /**
     * Testet das Befüllen des Formulars zum Ändern eines Skill-Levels wenn das zu ändernde Skill-Level nicht existiert.
     */
    @Test
    public void testEditNotExisting() {
        Long id = 1L;
        Mockito.when(skillLevelInteractor.findOne(Matchers.anyLong())).thenReturn(null);

        String actual = controller.edit(id, model, redirectAttributes);

        Mockito.verify(redirectAttributes, Mockito.times(1)).addFlashAttribute("errorMessage", "Es existiert kein Skill-Level mit der ID " + id);
        assertEquals("Returned view is not equal.", REDIRECT + BASE_URL, actual);
    }

    /**
     * Testet das Ändern eines Skill-Levels.
     */
    @Test
    public void testUpdate() {
        SkillLevel skillLevel = generateSkillLevel(10);
        skillLevel.setId(1L);
        Mockito.when(result.hasErrors()).thenReturn(false);
        Mockito.when(skillLevelInteractor.update(skillLevel)).thenReturn(skillLevel);

        String actual = controller.update(skillLevel, result, model, redirectAttributes);

        Mockito.verify(redirectAttributes, Mockito.times(1)).addFlashAttribute("successMessage",
                skillLevel.getInternalValue() + " " + skillLevel.getName() + " wurde erfolgreich geändert.");
        assertEquals("Returned view is not equal.", REDIRECT + BASE_URL, actual);
    }

    /**
     * Testet das Ändern eines Skill-Levels wenn beim DataBinding ein Fehler auftritt.
     */
    @Test
    public void testUpdateResultError() {
        SkillLevel skillLevel = generateSkillLevel(10);
        Mockito.when(result.hasErrors()).thenReturn(true);

        String actual = controller.update(skillLevel, result, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("skillLevel", skillLevel);
        assertEquals("Returned view is not equal.", EDIT_SKILL_LEVEL, actual);
    }

    /**
     * Testet das Hinzufügen eines neuen Skill-Levels, wenn eine
     * IllegalUserArgumentsException gefangen wird.
     */
    @Test
    public void testUpdateIllegalUserArgumentsException() {
        SkillLevel skillLevel = generateSkillLevel(10);
        Mockito.when(result.hasErrors()).thenReturn(false);
        Mockito.when(skillLevelInteractor.update(skillLevel)).thenThrow(new IllegalUserArgumentsException(generateErrorMap()));

        String actual = controller.update(skillLevel, result, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("skillLevel", skillLevel);
        Mockito.verify(model, Mockito.times(1)).addAttribute("errorMessage", NAME_ERROR + "<br/>");
        Mockito.verify(model, Mockito.times(1)).addAttribute("nameError", true);
        assertEquals("Returned view is not equal.", EDIT_SKILL_LEVEL, actual);
    }

    private SkillLevel generateSkillLevel(final int internalValue) {
        SkillLevel skillLevel = new SkillLevel();
        skillLevel.setInternalValue(internalValue);
        skillLevel.setName("Name des Wissensgebiets");
        skillLevel.setDescription("Erlerntes...");
        skillLevel.setTags("Tags ...");
        skillLevel.setCreated(new Date());
        return skillLevel;
    }

    private Map<String, String> generateErrorMap() {
        Map<String, String> errorMap = new HashMap<String, String>();
        errorMap.put("name", NAME_ERROR);
        return errorMap;
    }
}

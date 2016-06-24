package de.ars.skilldb.controller;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.KnowledgeCategory;
import de.ars.skilldb.interactor.KnowledgeCategoryInteractor;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Testet die Klasse KnowledgeCategoryController.
 *
 */
public class KnowledgeCategoryControllerTest {

    private static final String EXISTING_NAME_ERROR = "Der Name existiert bereits.";
    private KnowledgeCategoryController controller;
    private ModelMap model;
    private KnowledgeCategoryInteractor knowledgeCategoryInteractor;
    private BindingResult result;
    private RedirectAttributes redirectAttributes;
    private Set<KnowledgeCategory> allCategories;
    private static final String CATEGORY_NAME = "TestName";
    private static final String BASE_URL = "/knowledges/categories/";
    private static final String CREATE_CATEGORY = BASE_URL + "create";
    private static final String EDIT_CATEGORY = BASE_URL + "edit";
    private static final String REDIRECT = "redirect:";

    /**
     * Erstellt alle Objekte, die in den meisten Tests benötigt werden.
     */
    @Before
    public void initObjects() {
        controller = new KnowledgeCategoryController();
        model = Mockito.mock(ModelMap.class);
        knowledgeCategoryInteractor = Mockito.mock(KnowledgeCategoryInteractor.class);
        ReflectionTestUtils.setField(controller, "knowledgeCategoryInteractor", knowledgeCategoryInteractor);
        result = Mockito.mock(BindingResult.class);
        redirectAttributes = Mockito.mock(RedirectAttributes.class);
        allCategories = generateAllCategories();
        Mockito.when(knowledgeCategoryInteractor.findAllWithoutOne(Matchers.anyLong())).thenReturn(allCategories);
    }

    /**
     * Testet das Befüllen des Formulars zum Erstellen einer
     * Wissensgebiets-Kategorie.
     */
    @Test
    public void testCreate() {

        String resultView = controller.create(model);

        KnowledgeCategory category = new KnowledgeCategory();
        Mockito.verify(model, Mockito.times(1)).addAttribute("category", category);
        assertEquals("View is not equal.", CREATE_CATEGORY, resultView);
    }

    /**
     * Testet das Hinzufügen einer neuen Wissensgebiets-Kategorie.
     */
    @Test
    public void testAdd() {
        KnowledgeCategory category = generateCategory(CATEGORY_NAME);
        Mockito.when(result.hasErrors()).thenReturn(false);

        Mockito.when(knowledgeCategoryInteractor.add(category)).thenReturn(category);

        String resultView = controller.add(category, result, model, redirectAttributes);

        Mockito.verify(knowledgeCategoryInteractor, Mockito.times(1)).add(category);
        Mockito.verify(redirectAttributes, Mockito.times(1)).addFlashAttribute("successMessage",
                category.getName() + " wurde erfolgreich angelegt.");
        assertEquals("View is not equal.", REDIRECT + BASE_URL, resultView);
    }

    /**
     * Testet das Hinzufügen einer neuen Wissensgebiets-Kategorie, wenn die
     * Kategorie einen Binding-Error enthält.
     */
    @Test
    public void testAddResultErrors() {
        KnowledgeCategory category = generateCategory(CATEGORY_NAME);
        Mockito.when(result.hasErrors()).thenReturn(true);

        String resultView = controller.add(category, result, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("category", category);
        assertEquals("View is not equal.", CREATE_CATEGORY, resultView);
    }

    /**
     * Testet das Hinzufügen einer neuen Wissensgebiets-Kategorie, wenn eine
     * IllegalUserArgumentsException gefangen wird.
     */
    @Test
    public void testAddIllegalUserArgumentsException() {
        KnowledgeCategory category = generateCategory(CATEGORY_NAME);
        Mockito.when(knowledgeCategoryInteractor.add(category)).thenThrow(
                new IllegalUserArgumentsException("name", EXISTING_NAME_ERROR));

        String resultView = controller.add(category, result, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("category", category);
        Mockito.verify(model, Mockito.times(1)).addAttribute("errorMessage", EXISTING_NAME_ERROR + "<br/>");
        assertEquals("View is not equal.", CREATE_CATEGORY, resultView);
    }

    /**
     * Testet das Befüllen der View zum Ändern einer Wissensgebiets-Kategorie, wenn die zu ändernde
     * Wissensgebiets-Kategorie nicht gefunden wurde.
     */
    @Test
    public void testEditNotFound() {
        Long id = 100L;
        Mockito.when(knowledgeCategoryInteractor.findOne(Matchers.anyLong())).thenReturn(null);

        String actual = controller.edit(id, model, redirectAttributes);

        Mockito.verify(redirectAttributes, Mockito.times(1)).addFlashAttribute(
                "errorMessage",
                "Es existiert keine Wissensgebiets-Kategorie zur angegebenen ID."
                        + " Das Ändern ist deshalb nicht möglich.");
        assertEquals("View is not equal.", REDIRECT + BASE_URL, actual);
    }

    /**
     * Testet das Updaten einer Wissensgebiets-Kategorie.
     */
    @Test
    public void testUpdate() {
        KnowledgeCategory category = generateCategory(CATEGORY_NAME);

        Mockito.when(result.hasErrors()).thenReturn(false);
        Mockito.when(knowledgeCategoryInteractor.update(category)).thenReturn(category);

        String acutalView = controller.update(category, result, model, redirectAttributes);

        Mockito.verify(knowledgeCategoryInteractor, Mockito.times(1)).update(category);
        Mockito.verify(redirectAttributes, Mockito.times(1)).addFlashAttribute("successMessage",
                "Die Wissensgebiets-Kategorie wurde erfolgreich geändert.");
        assertEquals("Returned view is not equal.", REDIRECT + BASE_URL, acutalView);
    }

    /**
     * Testet das Updaten einer Wissensgebiets-Kategorie, wenn die
     * Kategorie einen Binding-Error enthält.
     */
    @Test
    public void testUpdateResultError() {
        KnowledgeCategory category = generateCategory(CATEGORY_NAME);

        Mockito.when(result.hasErrors()).thenReturn(true);

        String acutalView = controller.update(category, result, model, redirectAttributes);

        Mockito.verify(knowledgeCategoryInteractor, Mockito.times(0)).update(category);
        Mockito.verify(model, Mockito.times(1)).addAttribute("category", category);
        checkFillLists();
        checkAddHistory(category);
        assertEquals("Returned view is not equal.", EDIT_CATEGORY, acutalView);
    }

    /**
     * Testet das Updaten einer Wissensgebiets-Kategorie, wenn eine
     * IllegalUserArgumentsException gefangen wird.
     */
     @Test
     public void testUpdateIllegalUserArgumentsException() {
        KnowledgeCategory category = generateCategory(CATEGORY_NAME);
        Mockito.when(result.hasErrors()).thenReturn(false);
        Mockito.when(knowledgeCategoryInteractor.update(category)).thenThrow(new IllegalUserArgumentsException("name", EXISTING_NAME_ERROR));

        String acutalView = controller.update(category, result, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("category", category);
        checkFillLists();
        Mockito.verify(model, Mockito.times(1)).addAttribute("errorMessage", EXISTING_NAME_ERROR + "<br/>");
        checkAddHistory(category);
        assertEquals("Returned view is not equal.", EDIT_CATEGORY, acutalView);
     }

    private Set<KnowledgeCategory> generateAllCategories() {
        Set<KnowledgeCategory> categories = new HashSet<KnowledgeCategory>();
        categories.add(generateCategory(CATEGORY_NAME));
        categories.add(generateCategory("other"));
        return categories;
    }

    private KnowledgeCategory generateCategory(final String name) {
        KnowledgeCategory category = new KnowledgeCategory();
        category.setName(name);
        category.setDescription("Description");
        category.setParents(generateParents());
        category.setCreated(new Date());

        return category;
    }

    private Set<KnowledgeCategory> generateParents() {
        Set<KnowledgeCategory> parents = new HashSet<KnowledgeCategory>();
        KnowledgeCategory category = new KnowledgeCategory();
        category.setName("parent");
        parents.add(category);
        return parents;
    }

    private void checkFillLists() {
        Mockito.verify(model, Mockito.times(1)).addAttribute("parents", allCategories);
    }

    private void checkAddHistory(final KnowledgeCategory category) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy kk:mm", Locale.GERMAN);
        Mockito.verify(model, Mockito.times(1)).addAttribute("created", formatter.format(category.getCreated()));
        if (category.getLastModified() != null) {
            Mockito.verify(model, Mockito.times(1)).addAttribute("lastModified", formatter.format(category.getLastModified()));
        }
    }

}

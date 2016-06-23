package de.ars.skilldb.controller;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.KnowledgeCategory;
import de.ars.skilldb.interactor.KnowledgeCategoryInteractor;
import de.ars.skilldb.interactor.KnowledgeInteractor;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Testet die Klasse KnowledgeController.
 *
 */
public class KnowledgeControllerTest {

    private KnowledgeController controller;
    private ModelMap model;
    private KnowledgeInteractor knowledgeInteractor;
    private KnowledgeCategoryInteractor knowledgeCategoryInteractor;
    private BindingResult result;
    private RedirectAttributes redirectAttributes;
    private static final String KNOWLEDGE_NAME = "TestName";
    private static final String BASE_URL = "/knowledges/";
    private static final String CREATE_KNOWLEDGE = BASE_URL + "create";
    private static final String LIST_ALL_KNOWLEDGES = BASE_URL + "list";
    private static final String EDIT_KNOWLEDGE = BASE_URL + "edit";
    private static final String REDIRECT = "redirect:";

    /**
     * Erstellt alle Objekte, die in den meisten Tests benötigt werden.
     */
    @Before
    public void initObjects() {
        controller = new KnowledgeController();
        model = Mockito.mock(ModelMap.class);
        knowledgeInteractor = Mockito.mock(KnowledgeInteractor.class);
        ReflectionTestUtils.setField(controller, "knowledgeInteractor", knowledgeInteractor);
        knowledgeCategoryInteractor = Mockito.mock(KnowledgeCategoryInteractor.class);
        ReflectionTestUtils.setField(controller, "knowledgeCategoryInteractor", knowledgeCategoryInteractor);
        result = Mockito.mock(BindingResult.class);
        redirectAttributes = Mockito.mock(RedirectAttributes.class);
    }

    /**
     * Testet, ob beim Anzeigen aller Wissensgebiete und Kategorien die richtige
     * View geöffnet wird.
     */
    @Test
    public void testShowKnowledgeList() {
        String view = controller.getAll(model);
        assertEquals("The returned view is not equal.", LIST_ALL_KNOWLEDGES,  view);
    }

    /**
     * Testet das Finden aller Wissensgebiete.
     */
    @Test
    public void testGetAll() {
//        Collection<Knowledge> allKnowledges = generateAllKnowledges();
//        Sort sort = new Sort(Sort.Direction.ASC, "name");
//        Mockito.when(knowledgeInteractor.findAll(sort)).thenReturn(allKnowledges);
//
//        String view = controller.getAll(model);
//
//        Mockito.verify(model, Mockito.times(1)).addAttribute("knowledges", allKnowledges);
//        assertEquals("The returned view is not equal.", LIST_ALL_KNOWLEDGES,  view);
    }

    /**
     * Testet das Befüllen des Formulars zum Erstellen eines neuen Wissensgebiets.
     */
    @Test
    public void testCreate() {
        Collection<KnowledgeCategory> allCategories = allCategories();

        String resultView = controller.create(model);

        Mockito.verify(model, Mockito.times(1)).addAttribute("categories", allCategories);
        assertEquals("View is not equal.", CREATE_KNOWLEDGE, resultView);
    }

    /**
     * Testet das Hinzufügen eines neuen Wissensgebietes.
     */
    @Test
    public void testAdd() {
        Knowledge knowledge = generateKnowledge(KNOWLEDGE_NAME);
        Mockito.when(result.hasErrors()).thenReturn(false);

        Mockito.when(knowledgeInteractor.add(knowledge)).thenReturn(knowledge);

        String resultView = controller.add(knowledge, result, model, redirectAttributes);

        Mockito.verify(knowledgeInteractor, Mockito.times(1)).add(knowledge);
        Mockito.verify(redirectAttributes, Mockito.times(1)).addFlashAttribute("successMessage", knowledge.getName() + " wurde erfolgreich angelegt.");
        assertEquals("View is not equal.", REDIRECT + BASE_URL, resultView);
    }

    /**
     * Testet das Hinzufügen eines neuen Wissensgebietes, wenn das BindingResult
     * einen Fehler enthält.
     */
    @Test
    public void testAddResultErrors() {
        Knowledge knowledge = generateKnowledge(KNOWLEDGE_NAME);
        Mockito.when(result.hasErrors()).thenReturn(true);
        Collection<KnowledgeCategory> allCategories = allCategories();


        String resultView = controller.add(knowledge, result, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("knowledge", knowledge);
        Mockito.verify(model, Mockito.times(1)).addAttribute("categories", allCategories);
        assertEquals("View is not equal.", CREATE_KNOWLEDGE, resultView);
    }

    /**
     * Testet das Hinzufügen eines neuen Wissensgebietes, wenn eine
     * IllegalArgumentException gefangen wird.
     */
    @Test
    public void testAddIllegalUserArgumentsException() {
        Knowledge knowledge = generateKnowledge(KNOWLEDGE_NAME);
        Mockito.when(knowledgeInteractor.add(knowledge)).thenThrow(new IllegalUserArgumentsException("name", "Das Wissensgebiet existiert bereits."));
        Collection<KnowledgeCategory> allCategories = allCategories();

        String resultView = controller.add(knowledge, result, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("knowledge", knowledge);
        Mockito.verify(model, Mockito.times(1)).addAttribute("errorMessage", "Das Wissensgebiet existiert bereits.<br/>");
        Mockito.verify(model, Mockito.times(1)).addAttribute("nameError", true);
        Mockito.verify(model, Mockito.times(1)).addAttribute("categories", allCategories);
        assertEquals("View is not equal.", CREATE_KNOWLEDGE, resultView);
    }

    /**
     * Testet das Befüllen der View zum Editieren eines Wissensgebiets, wenn das
     * zu editierende Wissensgebiet nicht gefunden wurde.
     */
    @Test
    public void testEditNotFound() {
        Long id = 100L;
        Mockito.when(knowledgeInteractor.findOne(Matchers.anyLong())).thenReturn(null);

        String actual = controller.edit(id, model, redirectAttributes);

        Mockito.verify(redirectAttributes, Mockito.times(1)).addFlashAttribute("errorMessage", "Es existiert kein Wissensgebiet zur angegebenen ID."
                + " Das Ändern ist deshalb nicht möglich.");
        assertEquals("View is not equal.", REDIRECT + BASE_URL, actual);
    }

    /**
     * Testet das Updaten eines Wissensgebiets.
     */
    @Test
    public void testUpdate() {
        Knowledge knowledge = generateKnowledge(KNOWLEDGE_NAME);
        Mockito.when(knowledgeInteractor.update(knowledge)).thenReturn(knowledge);

        Mockito.when(result.hasErrors()).thenReturn(false);

        String acutalView = controller.update(knowledge, result, model, redirectAttributes);

        Mockito.verify(knowledgeInteractor, Mockito.times(1)).update(knowledge);
        Mockito.verify(redirectAttributes, Mockito.times(1)).addFlashAttribute("successMessage",
                "Das Wissensgebiet " + KNOWLEDGE_NAME + " wurde erfolgreich geändert.");
        assertEquals("Returned view is not equal.", REDIRECT + BASE_URL, acutalView);
    }

    /**
     * Testet das Updaten eines Wissensgebiets, wenn das BindingResult einen
     * Fehler enthält.
     */
    @Test
    public void testUpdateResultError() {
        Knowledge knowledge = generateKnowledge(KNOWLEDGE_NAME);
        Collection<KnowledgeCategory> allCategories = allCategories();

        Mockito.when(result.hasErrors()).thenReturn(true);

        String acutalView = controller.update(knowledge, result, model, redirectAttributes);

        Mockito.verify(knowledgeInteractor, Mockito.times(0)).update(knowledge);
        Mockito.verify(model, Mockito.times(1)).addAttribute("knowledge", knowledge);
        Mockito.verify(model, Mockito.times(1)).addAttribute("categories", allCategories);
        checkAddHistory(knowledge);
        assertEquals("Returned view is not equal.", EDIT_KNOWLEDGE, acutalView);
    }

    /**
     * Testet das Updaten eines Wissensgebiets, wenn eine
     * IllegalArgumentException gefangen wird.
     */
    @Test
    public void testUpdateIllegalUserArgumentsException() {
        Knowledge knowledge = generateKnowledge(KNOWLEDGE_NAME);
        Collection<KnowledgeCategory> allCategories = allCategories();
        Mockito.when(result.hasErrors()).thenReturn(false);

        Mockito.when(knowledgeInteractor.update(knowledge)).thenThrow(new IllegalUserArgumentsException("name", "Das Wissensgebiet existiert bereits."));

        String acutalView = controller.update(knowledge, result, model, redirectAttributes);

        Mockito.verify(model, Mockito.times(1)).addAttribute("knowledge", knowledge);
        Mockito.verify(model, Mockito.times(1)).addAttribute("categories", allCategories);
        Mockito.verify(model, Mockito.times(1)).addAttribute("errorMessage",
                "Das Wissensgebiet existiert bereits.<br/>");
        checkAddHistory(knowledge);
        Mockito.verify(model, Mockito.times(1)).addAttribute("nameError", true);
        assertEquals("Returned view is not equal.", EDIT_KNOWLEDGE, acutalView);
    }

    private Set<KnowledgeCategory> allCategories() {
        Set<KnowledgeCategory> allCategories = generateCategories();
        Mockito.when(knowledgeCategoryInteractor.findAll()).thenReturn(allCategories);
        return allCategories;
    }

    private Knowledge generateKnowledge(final String name) {
        Knowledge knowledge = new Knowledge();
        knowledge.setName(name);
        knowledge.setDescription("Description");
        knowledge.setCategories(generateCategories());
        knowledge.setCreated(new Date());

        return knowledge;
    }

    private Set<KnowledgeCategory> generateCategories() {
        Set<KnowledgeCategory> parents = new HashSet<KnowledgeCategory>();
        parents.add(generateCategory("parent1"));
        parents.add(generateCategory("parent2"));

        return parents;
    }

    private KnowledgeCategory generateCategory(final String name) {
        KnowledgeCategory category = new KnowledgeCategory();
        category.setName(name);
        category.setDescription("Description");

        return category;
    }

    private Collection<Knowledge> generateAllKnowledges() {
        Collection<Knowledge> allKnowledges = new ArrayList<Knowledge>();
        allKnowledges.add(generateKnowledge(KNOWLEDGE_NAME));
        allKnowledges.add(generateKnowledge("other"));
        return allKnowledges;
    }

    private void checkAddHistory(final Knowledge knowledge) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy kk:mm", Locale.GERMAN);
        Mockito.verify(model, Mockito.times(1)).addAttribute("created", formatter.format(knowledge.getCreated()));
        if (knowledge.getLastModified() != null) {
            Mockito.verify(model, Mockito.times(1)).addAttribute("lastModified", formatter.format(knowledge.getLastModified()));
        }
    }

}

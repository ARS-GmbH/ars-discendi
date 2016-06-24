package de.ars.skilldb.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.KnowledgeCategory;
import de.ars.skilldb.interactor.KnowledgeCategoryInteractor;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Controller zur Verarbeitung der Anfragen vom Benutzer.
 *
 */
@Controller
@RequestMapping("/knowledges/categories")
@SessionAttributes("category")
public class KnowledgeCategoryController extends AbstractController {

    private static final String BASE_URL = "/knowledges/";
    private static final String CREATE_CATEGORY = BASE_URL + "categories/create";
    private static final String EDIT_CATEGORY = BASE_URL + "categories/edit";

    @Autowired
    private KnowledgeCategoryInteractor knowledgeCategoryInteractor;


    /**
     * Erstellt die Inhalte für das Formular zur Eingabe einer neuen Wissensgebiets-Kategorie.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping("/create")
    public String create(final ModelMap model) {
        model.addAttribute("category", new KnowledgeCategory());

        addPossibleParents(model, null);

        return CREATE_CATEGORY;
    }

    /**
     * Erstellt eine Wissensgebiets-Kategorie anhand der validen
     * Benutzereingaben und übergibt diese dem KnowledgeInteractor.
     *
     * @param category
     *            Anzulegende Kategorie
     * @param result
     *            Validiert die Benutzereingaben
     * @param model
     *            Model für die View
     * @param redirectAttributes
     *            dadurch werden Ausgaben an den Benutzer trotz redirect im
     *            Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("category") final KnowledgeCategory category, final BindingResult result,
            final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("category", category);
            addPossibleParents(model, null);
            markErrorFields(result.getFieldErrors(), model);
            return CREATE_CATEGORY;
        }

        try {
            knowledgeCategoryInteractor.add(category);
            addSuccessFlashMessage(category.getName() + " wurde erfolgreich angelegt.", redirectAttributes);
            return redirectTo(BASE_URL);
        }
        catch (IllegalUserArgumentsException e) {
            model.addAttribute("category", category);
            model.addAttribute("errorMessage", e.getErrorDescription());
            addPossibleParents(model, null);
            markErrorFields(e.getAttributeMap(), model);
            return CREATE_CATEGORY;
        }
    }

    /**
     * Befüllt das Formular zum Bearbeiten mit den Daten der zu bearbeitenden Wissensgebiets-Kategorie.
     * @param categoryId ID der Kategorie
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public String edit(@PathVariable final Long categoryId, final ModelMap model, final RedirectAttributes redirectAttributes) {
        KnowledgeCategory category = knowledgeCategoryInteractor.findOne(categoryId);

        if (category == null) {
            addErrorFlashMessage("Es existiert keine Wissensgebiets-Kategorie zur angegebenen ID."
                    + " Das Ändern ist deshalb nicht möglich.", redirectAttributes);
            return redirectTo(BASE_URL);
        }

        Set<KnowledgeCategory> parent = knowledgeCategoryInteractor.fetchFullData(category.getParents());
        category.setParents(parent);
        category.setId(categoryId);

        model.addAttribute("category", category);
        addPossibleParents(model, category);
        addHistory(category.getCreated(), category.getLastModified(), model);

        return EDIT_CATEGORY;
    }

    /**
     * Nimmt die geänderte Wissensgebiets-Kategorie entgegen und übergibt sie dem KnowledgeCategoryInteractor, wenn sie valide ist.
     * @param category Wissensgebiet
     * @param model Model für die View
     * @param result Validierungsergebnis
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("category") final KnowledgeCategory category,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("category", category);
            addPossibleParents(model, category);
            markErrorFields(result.getFieldErrors(), model);
            addHistory(category.getCreated(), category.getLastModified(), model);
            return EDIT_CATEGORY;
        }

        try {
            knowledgeCategoryInteractor.update(category);
            addSuccessFlashMessage("Die Wissensgebiets-Kategorie wurde erfolgreich geändert.", redirectAttributes);
            return redirectTo(BASE_URL);
        }
        catch (IllegalUserArgumentsException exception) {
            model.addAttribute("category", category);
            addPossibleParents(model, category);
            model.addAttribute("errorMessage", exception.getErrorDescription());
            markErrorFields(exception.getAttributeMap(), model);
            addHistory(category.getCreated(), category.getLastModified(), model);
            return EDIT_CATEGORY;
        }
    }

    private void addPossibleParents(final ModelMap model, final KnowledgeCategory category) {
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        if (category == null) {
            model.addAttribute("parents", knowledgeCategoryInteractor.findAll(sort));
        }
        else {
            model.addAttribute("parents", knowledgeCategoryInteractor.findAllWithoutOne(category.getId()));
        }
    }
}
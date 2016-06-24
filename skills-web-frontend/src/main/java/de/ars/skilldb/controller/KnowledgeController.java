package de.ars.skilldb.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.KnowledgeCategory;
import de.ars.skilldb.interactor.KnowledgeCategoryInteractor;
import de.ars.skilldb.interactor.KnowledgeInteractor;
import de.ars.skilldb.util.IllegalUserArgumentsException;
import de.ars.skilldb.view.model.KnowledgeNode;

/**
 * Controller zur Verarbeitung der Anfragen vom Benutzer.
 *
 */
@Controller
@RequestMapping("knowledges")
@SessionAttributes("knowledge")
public class KnowledgeController extends AbstractController {

    private static final String BASE_URL = "/knowledges/";
    private static final String CREATE_KNOWLEDGE = BASE_URL + "create";
    private static final String LIST_ALL_KNOWLEDGES = BASE_URL + "list";
    private static final String EDIT_KNOWLEDGE = BASE_URL + "edit";

    @Autowired
    private KnowledgeInteractor knowledgeInteractor;

    @Autowired
    private KnowledgeCategoryInteractor knowledgeCategoryInteractor;

    /**
     * Öffnet die View zum Anzeigen aller Wissensgebiete und Kategorien.
     *
     * @param model
     *            Model für die View
     * @return logischer Viewname
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAll(final ModelMap model) {
        return LIST_ALL_KNOWLEDGES;
    }


    /**
     * Holt alle Kindknoten-Knoten zur übergebenen ID aus der Datenbank.
     *
     * @param id
     *            ID des Knotens, dessen Kinder aus der Datenbank geholt werden
     *            sollen
     * @return alle Kindknoten
     */
    @RequestMapping(value = "/ajax", method = RequestMethod.GET)
    @ResponseBody
    public Set<KnowledgeNode> getChildNodes(final String id) {
        KnowledgeCategory root;
        if ("#".equals(id)) {
            root = knowledgeCategoryInteractor.findOne(3L);
        }
        else {
            root = knowledgeCategoryInteractor.findOne(Long.parseLong(id));
        }
        Set<KnowledgeCategory> childrenCategories = knowledgeCategoryInteractor.fetchFullData(root.getChildrenCategories());
        Set<Knowledge> childrenKnowledges = knowledgeInteractor.fetchFullData(root.getChildrenKnowledges());

        root.setChildrenCategories(childrenCategories);
        root.setChildrenKnowledges(childrenKnowledges);

        Set<KnowledgeNode> childrenNodes = new HashSet<KnowledgeNode>();
        for (KnowledgeCategory child : childrenCategories) {
            childrenNodes.add(new KnowledgeNode(child));
        }
        for (Knowledge child : childrenKnowledges) {
            childrenNodes.add(new KnowledgeNode(child));
        }
        return childrenNodes;
    }

    /**
     * Erstellt die Inhalte für das Formular zur Eingabe eines neuen Wissensgebiets.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping("/create")
    public String create(final ModelMap model) {
        model.addAttribute("knowledge", new Knowledge());
        setPossibleCategories(model);

        return CREATE_KNOWLEDGE;
    }

    /**
     * Erstellt ein Wissensgebiet anhand der validen Benutzereingaben und übergibt dieses dem KnowledgeInteractor.
     * @param knowledge Anzulegendes Wissensgebiet
     * @param result Validiert die Benutzereingaben
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("knowledge") final Knowledge knowledge, final BindingResult result,
            final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("knowledge", knowledge);
            markErrorFields(result.getFieldErrors(), model);
            setPossibleCategories(model);
            return CREATE_KNOWLEDGE;
        }

        try {
            knowledgeInteractor.add(knowledge);
            addSuccessFlashMessage(knowledge.getName() + " wurde erfolgreich angelegt.", redirectAttributes);
            return redirectTo(BASE_URL);
        }
        catch (IllegalUserArgumentsException exception) {
            model.addAttribute("knowledge", knowledge);
            model.addAttribute("errorMessage", exception.getErrorDescription());
            markErrorFields(exception.getAttributeMap(), model);
            setPossibleCategories(model);
            return CREATE_KNOWLEDGE;
        }
    }

    /**
     * Befüllt das Formular zum Bearbeiten mit den Daten des zu bearbeitenden Wissensgebiets.
     * @param knowledgeId ID des Wissensgebiets
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{knowledgeId}", method = RequestMethod.GET)
    public String edit(@PathVariable final Long knowledgeId, final ModelMap model, final RedirectAttributes redirectAttributes) {
        Knowledge knowledge = knowledgeInteractor.findOne(knowledgeId);

        if (knowledge == null) {
            addErrorFlashMessage("Es existiert kein Wissensgebiet zur angegebenen ID."
                    + " Das Ändern ist deshalb nicht möglich.", redirectAttributes);
            return redirectTo(BASE_URL);
        }
        Set<KnowledgeCategory> categories = knowledgeCategoryInteractor.fetchFullData(knowledge.getCategories());

        knowledge.setCategories(categories);

        model.addAttribute("knowledge", knowledge);
        setPossibleCategories(model);
        addHistory(knowledge.getCreated(), knowledge.getLastModified(), model);

        return EDIT_KNOWLEDGE;
    }

    /**
     * Nimmt das geänderte Wissensgebiet entgegen und übergibt es dem KnowledgeInteractor, wenn es valide ist.
     * @param knowledge Wissensgebiet
     * @param model Model für die View
     * @param result Validierungsergebnis
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{knowledgeId}", method = RequestMethod.POST)
    public String update(@Valid final Knowledge knowledge,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            markErrorFields(result.getFieldErrors(), model);
            model.addAttribute("knowledge", knowledge);
            setPossibleCategories(model);
            addHistory(knowledge.getCreated(), knowledge.getLastModified(), model);
            return EDIT_KNOWLEDGE;
        }

        try {
            Knowledge savedKnowledge = knowledgeInteractor.update(knowledge);
            addSuccessFlashMessage("Das Wissensgebiet " + savedKnowledge.getName() + " wurde erfolgreich geändert.", redirectAttributes);
            return redirectTo(BASE_URL);
        }
        catch (IllegalUserArgumentsException exception) {
            model.addAttribute("knowledge", knowledge);
            model.addAttribute("errorMessage", exception.getErrorDescription());
            markErrorFields(exception.getAttributeMap(), model);
            setPossibleCategories(model);
            addHistory(knowledge.getCreated(), knowledge.getLastModified(), model);
            return EDIT_KNOWLEDGE;
        }
    }

    private void setPossibleCategories(final ModelMap model) {
        Collection<KnowledgeCategory> allCategories = knowledgeCategoryInteractor.findAll();
        model.addAttribute("categories", allCategories);
    }

}
package de.ars.skilldb.controller;

import java.util.Collection;
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

import de.ars.skilldb.domain.Customer;
import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.Project;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.CustomerInteractor;
import de.ars.skilldb.interactor.KnowledgeInteractor;
import de.ars.skilldb.interactor.ProjectInteractor;
import de.ars.skilldb.interactor.UserInteractor;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Controller, der die Benutzeranfragen zu Projekten entgegen nimmt, weiterreicht und
 * die Antwort für den Benutzer aufbereitet.
 */
@Controller
@RequestMapping("/projects")
@SessionAttributes("project")
public class ProjectController extends AbstractController {


    private static final String BASE_URL = "/projects/";
    private static final String LIST_ALL_PROJECTS = BASE_URL + "list";
    private static final String CREATE_PROJECT = BASE_URL + "create";
    private static final String EDIT_PROJECT = BASE_URL + "edit";

    @Autowired
    private ProjectInteractor projectInteractor;

    @Autowired
    private KnowledgeInteractor knowledgeInteractor;

    @Autowired
    private CustomerInteractor customerInteractor;

    @Autowired
    private UserInteractor userInteractor;

    /**
     * Füllt die View für die Anzeige der exisitierenden Projekte.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAll(final ModelMap model) {
        Sort sort = new Sort(Sort.Direction.ASC, "customer");
        Collection<Project> allProjects = projectInteractor.findAll(sort);

        model.addAttribute("allProjects", allProjects);

        return LIST_ALL_PROJECTS;
    }

    /**
     * Erstellt die Inhalte für das Formular zur Eingabe eines neuen Projekts.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping("/create")
    public String create(final ModelMap model) {
        model.addAttribute("project", new Project());
        fillLists(model);

        return CREATE_PROJECT;
    }

    /**
     * Erstellt ein Projekt anhand der validen Benutzereingaben und übergibt dieses dem ProjectInteractor.
     * @param project Anzulegendes Projekt
     * @param result Validiert die Benutzereingaben
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("project") final Project project,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("project", project);
            markErrorFields(result.getFieldErrors(), model);
            fillLists(model);
            return CREATE_PROJECT;
        }

        try {
            Project savedProject = projectInteractor.add(project);
            String successMessage = String.format("%s wurde erfolgreich angelegt.", savedProject.getName());
            addSuccessFlashMessage(successMessage, redirectAttributes);
            return redirectTo(BASE_URL);
        }
        catch (IllegalUserArgumentsException userArgumentException) {
            model.addAttribute("project", project);
            model.addAttribute("errorMessage", userArgumentException.getErrorDescription());
            markErrorFields(userArgumentException.getAttributeMap(), model);
            fillLists(model);
            return CREATE_PROJECT;
        }
    }

    /**
     * Befüllt das Formular zum Bearbeiten mit den Daten des zu bearbeitenden Projekts.
     * @param projectId ID des Projekts
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public String edit(@PathVariable final Long projectId, final ModelMap model, final RedirectAttributes redirectAttributes) {
        Project project = projectInteractor.findOne(projectId);

        if (project == null) {
            addErrorFlashMessage("Es existiert kein Projekt mit der ID " + projectId, redirectAttributes);
            return redirectTo(BASE_URL);
        }

        Set<Knowledge> knowledges = knowledgeInteractor.fetchFullData(project.getKnowledges());
        project.setKnowledges(knowledges);

        Set<User> users = userInteractor.fetchFullData(project.getUsers());
        project.setUsers(users);

        model.addAttribute("project", project);
        fillLists(model);
        addHistory(project.getCreated(), project.getLastModified(), model);

        return EDIT_PROJECT;
    }

    /**
     * Nimmt das geänderte Projekt entgegen und übergibt es dem ProjectInteractor, wenn es valide ist.
     * @param project Projekt
     * @param model Model für die View
     * @param result Validierungsergebnis
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.POST)
    public String update(@Valid final Project project,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            markErrorFields(result.getFieldErrors(), model);
            model.addAttribute("project", project);
            fillLists(model);
            return EDIT_PROJECT;
        }

        try {
            Project savedProject = projectInteractor.update(project);
            String successMessage = String.format("%s wurde erfolgreich geändert.", savedProject.getName());
            addSuccessFlashMessage(successMessage, redirectAttributes);

            return redirectTo(BASE_URL);
        }
        catch (IllegalUserArgumentsException userArgumentException) {
            model.addAttribute("project", project);
            model.addAttribute("errorMessage", userArgumentException.getErrorDescription());
            markErrorFields(userArgumentException.getAttributeMap(), model);
            fillLists(model);
            return EDIT_PROJECT;
        }
    }

    private void fillLists(final ModelMap model) {
        Collection<Knowledge> knowledges = knowledgeInteractor.findAll();
        model.addAttribute("knowledges", knowledges);
        Collection<Customer> customers = customerInteractor.findAll();
        model.addAttribute("customers", customers);
        Collection<User> users = userInteractor.findAll();
        model.addAttribute("users", users);
    }
}

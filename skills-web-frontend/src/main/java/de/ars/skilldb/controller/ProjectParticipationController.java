package de.ars.skilldb.controller;

import java.util.Collection;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.Project;
import de.ars.skilldb.domain.ProjectParticipation;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillProofEdge;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.ProjectInteractor;
import de.ars.skilldb.interactor.ProjectParticipationInteractor;
import de.ars.skilldb.interactor.SkillInteractor;
import de.ars.skilldb.interactor.SkillProofEdgeInteractor;
import de.ars.skilldb.interactor.UserInteractor;
import de.ars.skilldb.util.IllegalUserArgumentsException;
import de.ars.skilldb.view.model.ProjectParticipationHelper;

/**
 * Controller, der die Benutzeranfragen zu Projektteilnahmen entgegen nimmt, weiterreicht und
 * die Antwort für den Benutzer aufbereitet.
 */
@Controller
@RequestMapping("/users/{userId}/proofs/projectparticipations")
@SessionAttributes("projectParticipationHelper")
public class ProjectParticipationController extends AbstractController {

    private static final String BASE_URL = "/proofs/projectparticipations/";
    private static final String REDIRECT_BASE_URL = "/users/%s" + BASE_URL;
    private static final String LIST_ALL_PROJECTPARTICIPATIONS = BASE_URL + "list";
    private static final String CREATE_PROJECTPARTICIPATION = BASE_URL + "create";
    private static final String EDIT_PROJECTPARTICIPATION = BASE_URL + "edit";

    @Autowired
    private ProjectParticipationInteractor projectParticipationInteractor;

    @Autowired
    private ProjectInteractor projectInteractor;

    @Autowired
    private SkillInteractor skillInteractor;

    @Autowired
    private UserInteractor userInteractor;

    @Autowired
    private SkillProofEdgeInteractor skillProofEdgeInteractor;

    /**
     * Füllt die View für die Anzeige der exisitierenden Projekt-Teilnahmen des Benutzers.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAll(final ModelMap model) {
        User currentUser = getCurrentUser();
        Collection<ProjectParticipation> allProjectParticipations = projectParticipationInteractor.findByUser(currentUser);

        model.addAttribute("user", currentUser);
        model.addAttribute("allProjectParticipations", allProjectParticipations);

        return LIST_ALL_PROJECTPARTICIPATIONS;
    }

    /**
     * Erstellt die Inhalte für das Formular zur Eingabe einer neuen Projektarbeit.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping("/create")
    public String create(final ModelMap model) {
        User user = getCurrentUser();
        model.addAttribute("user", user);

        ProjectParticipation projectParticipation = new ProjectParticipation(user);
        SkillProofEdge edge = new SkillProofEdge();
        edge.setProof(projectParticipation);

        ProjectParticipationHelper helper = new ProjectParticipationHelper();

        helper.setProjectParticipation(projectParticipation);
        helper.getSkillProofEdges().add(edge);

        model.addAttribute("projectParticipationHelper", helper);
        fillLists(user, model);

        return CREATE_PROJECTPARTICIPATION;
    }

    /**
     * Erstellt eine Projektarbeit anhand der validen Benutzereingaben und übergibt dieses dem ProjectParticipationInteractor.
     * @param userId ID des Mitarbeiters
     * @param projectParticipationHelper Anzulegende Projektarbeit inkl. Kanten
     * @param result Validiert die Benutzereingaben
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String add(@PathVariable final Long userId, @Valid @ModelAttribute("projectParticipationHelper") final ProjectParticipationHelper projectParticipationHelper,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {
        User user = getCurrentUser();
        model.addAttribute("user", user);

        if (result.hasErrors()) {
            model.addAttribute("projectParticipationHelper", projectParticipationHelper);
            markErrorFields(result.getFieldErrors(), model);
            fillLists(user, model);
            return CREATE_PROJECTPARTICIPATION;
        }

        try {
            ProjectParticipation convertedProjectParticipation = projectParticipationHelper.convertToProjectParticipation();
            ProjectParticipation savedProjectParticipation = projectParticipationInteractor.add(convertedProjectParticipation);

            setKnowledgesInProject(savedProjectParticipation);

            String successMessage = String.format("%s wurde erfolgreich angelegt.", savedProjectParticipation.getProject().getName());
            addSuccessFlashMessage(successMessage, redirectAttributes);

            return redirectTo(REDIRECT_BASE_URL, userId);
        }
        catch (IllegalUserArgumentsException userArgumentException) {
            model.addAttribute("projectParticipationHelper", projectParticipationHelper);
            model.addAttribute("errorMessage", userArgumentException.getErrorDescription());
            markErrorFields(userArgumentException.getAttributeMap(), model);
            fillLists(user, model);
            return CREATE_PROJECTPARTICIPATION;
        }
    }

    /**
     * Befüllt das Formular zum Bearbeiten mit den Daten der zu bearbeitenden Projektarbeit.
     * @param userId ID des Mitarbeiters
     * @param projectParticipationId ID der Projektarbeit
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{projectParticipationId}", method = RequestMethod.GET)
    public String edit(@PathVariable final Long userId, @PathVariable final Long projectParticipationId, final ModelMap model, final RedirectAttributes redirectAttributes) {
        ProjectParticipation projectParticipation = projectParticipationInteractor.findOne(projectParticipationId);
        model.addAttribute("user", getCurrentUser());

        if (projectParticipation == null) {
            addErrorFlashMessage("Es existiert keine Projektarbeit mit der ID " + projectParticipationId, redirectAttributes);
            return redirectTo(REDIRECT_BASE_URL, userId);
        }

        User user = userInteractor.fetchFullData(projectParticipation.getUser());
        projectParticipation.setUser(user);

        Set<SkillProofEdge> edges = skillProofEdgeInteractor.fetchFullData(projectParticipation.getSkillProofEdges());
        projectParticipation.setSkillProofEdges(edges);
        ProjectParticipationHelper projectParticipationHelper = new ProjectParticipationHelper(projectParticipation);

        if (edges.isEmpty()) {
            SkillProofEdge edge = new SkillProofEdge();
            edge.setProof(projectParticipation);
            projectParticipationHelper.getSkillProofEdges().add(edge);
        }

        model.addAttribute("projectParticipationHelper", projectParticipationHelper);
        fillLists(user, model);
        addHistory(projectParticipation.getCreated(), projectParticipation.getLastModified(), model);

        return EDIT_PROJECTPARTICIPATION;
    }

    /**
     * Nimmt die geänderte Projektarbeit entgegen und übergibt sie dem ProjectParticipationInteractor, wenn sie valide ist.
     * @param userId ID des Mitarbeiters
     * @param projectParticipationHelper Projektarbeit
     * @param model Model für die View
     * @param result Validierungsergebnis
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{projectParticipationId}", method = RequestMethod.POST)
    public String update(@PathVariable final Long userId, @Valid final ProjectParticipationHelper projectParticipationHelper,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {
        User user = getCurrentUser();
        model.addAttribute("user", user);

        if (result.hasErrors()) {
            markErrorFields(result.getFieldErrors(), model);
            model.addAttribute("projectParticipationHelper", projectParticipationHelper);
            fillLists(user, model);
            return EDIT_PROJECTPARTICIPATION;
        }

        try {
            ProjectParticipation projectParticipation = projectParticipationHelper.convertToProjectParticipation();
            ProjectParticipation savedProjectParticipation = projectParticipationInteractor.update(projectParticipation);

            setKnowledgesInProject(savedProjectParticipation);

            String successMessage = String.format("Projektarbeit %s wurde erfolgreich geändert.", savedProjectParticipation.getProject().getName());
            addSuccessFlashMessage(successMessage, redirectAttributes);

            return redirectTo(REDIRECT_BASE_URL, userId);
        }
        catch (IllegalUserArgumentsException userArgumentException) {
            model.addAttribute("projectParticipationHelper", projectParticipationHelper);
            model.addAttribute("errorMessage", userArgumentException.getErrorDescription());
            markErrorFields(userArgumentException.getAttributeMap(), model);
            fillLists(user, model);
            return EDIT_PROJECTPARTICIPATION;
        }
    }

    private void fillLists(final User user, final ModelMap model) {
        Collection<Project> projects = projectInteractor.findAll();
        model.addAttribute("projects", projects);
        Collection<Skill> skills = skillInteractor.findUserSkills(user);
        model.addAttribute("skills", skills);
    }

    private void setKnowledgesInProject(final ProjectParticipation projectParticipation) {
        Set<Knowledge> participationKnowledges = projectParticipationInteractor.findAllKnowledges(projectParticipation);
        Project project = projectParticipation.getProject();
        Set<Knowledge> projectKnowledges = project.getKnowledges();
        projectKnowledges.addAll(participationKnowledges);
        projectInteractor.update(project);
    }
}

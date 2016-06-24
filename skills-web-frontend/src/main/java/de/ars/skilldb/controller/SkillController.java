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

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.domain.SkillProofEdge;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.KnowledgeInteractor;
import de.ars.skilldb.interactor.SkillInteractor;
import de.ars.skilldb.interactor.SkillLevelInteractor;
import de.ars.skilldb.interactor.SkillProofEdgeInteractor;
import de.ars.skilldb.interactor.UserInteractor;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Controller, der die Benutzeranfragen zu Skills entgegenimmt, weiterreicht und
 * die Antwort die den Benutzer aufbereitet.
 */
@Controller
@RequestMapping("/users/{userId}/skills")
@SessionAttributes("skill")
public class SkillController extends AbstractController {

    private static final String REDIRECT_BASE_URL = "/users/%s/skills/";
    private static final String BASE_URL = "/skills/";
    private static final String CREATE_SKILL = BASE_URL + "create";
    private static final String LIST_ALL_SKILLS = BASE_URL + "list";
    private static final String EDIT_SKILL = BASE_URL + "edit";

    @Autowired
    private SkillInteractor skillInteractor;

    @Autowired
    private KnowledgeInteractor knowledgeInteractor;

    @Autowired
    private SkillLevelInteractor skillLevelInteractor;

    @Autowired
    private UserInteractor userInteractor;

    @Autowired
    private SkillProofEdgeInteractor skillProofEdgeInteractor;

    /**
     * Füllt die View für die Anzeige eigener Skills.
     * @param userId ID des Benutzers
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getUserSkills(@PathVariable final Long userId, final ModelMap model) {
        User user = getUser(userId);
        User currentUser = getCurrentUser();

        Collection<Skill> skills = skillInteractor.findUserSkills(user);


       for (Skill skill : skills) {
            Set<SkillProofEdge> edges = skillProofEdgeInteractor.findEdgesBySkill(skill);
            skill.setSkillProofEdges(edges);
        }
        user.setSkills(skills);

        model.addAttribute("user", user);

        if (user.getId().equals(currentUser.getId())) {
            model.addAttribute("isCurrentUser", true);
        }

        if (user.getSkills().isEmpty()) {
            model.addAttribute("isError", true);
            model.addAttribute("errorMessage", user.getFullName() + " hat noch keinen Skill angelegt.");
        }

        return LIST_ALL_SKILLS;
    }

    /**
     * Befüllt die View für das Anlegen eines neuen Skills.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping("/create")
    public String createSkill(final ModelMap model) {
        Skill skill = new Skill();
        model.addAttribute("user", getCurrentUser());

        User user = getCurrentUser();
        skill.setUser(user);

        model.addAttribute("skill", skill);
        setAllKnowledges(model);
        setAllSkillLevel(model);

        return CREATE_SKILL;
    }

    /**
     * Erstellt einen neuen Skill.
     * @param userId ID des Mitarbeiters, zu dem ein Skill erzeugt wird
     * @param skill Anzulegender Skill
     * @param result Validiert die Benutzereingaben
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben and den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String add(@PathVariable final Long userId, @Valid @ModelAttribute("skill") final Skill skill, final BindingResult result,
            final ModelMap model, final RedirectAttributes redirectAttributes) {
        model.addAttribute("user", getCurrentUser());

        if (result.hasErrors()) {
            model.addAttribute("skill", skill);
            markErrorFields(result.getFieldErrors(), model);
            setAllKnowledges(model);
            setAllSkillLevel(model);
            return CREATE_SKILL;
        }

        try {
            Skill savedSkill = skillInteractor.add(skill);
            String successMessage = String.format("%s wurde erfolgreich angelegt.", savedSkill.getKnowledge().getName());
            addSuccessFlashMessage(successMessage, redirectAttributes);
            return redirectTo(String.format(REDIRECT_BASE_URL, userId));
        }
        catch (IllegalUserArgumentsException userArgumentException) {
            model.addAttribute("skill", skill);
            markErrorFields(userArgumentException.getAttributeMap(), model);
            model.addAttribute("errorMessage", userArgumentException.getErrorDescription());
            setAllKnowledges(model);
            setAllSkillLevel(model);
            return CREATE_SKILL;
        }
    }

    /**
     * Befüllt das Formular zum Bearbeiten mit den Daten des zu bearbeitenden Skills.
     * @param userId ID des Mitarbeiters
     * @param skillId ID des Skills
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{skillId}", method = RequestMethod.GET)
    public String edit(@PathVariable final Long userId, @PathVariable final Long skillId, final ModelMap model, final RedirectAttributes redirectAttributes) {
        Skill skill = skillInteractor.findOne(skillId);
        model.addAttribute("user", getCurrentUser());

        if (skill == null) {
            addErrorFlashMessage("In der Datenbank existiert kein Wissensgebiet mit der ID " + skillId, redirectAttributes);
            return redirectTo(String.format(REDIRECT_BASE_URL, userId));
        }

        setAllKnowledges(model);
        setKnowledgeDisabled(model);
        model.addAttribute("skill", skill);

        setAllSkillLevel(model);
        addHistory(skill.getCreated(), skill.getLastModified(), model);

        return EDIT_SKILL;
    }

    private void setKnowledgeDisabled(final ModelMap model) {
        model.addAttribute("knowledgeDisabled", true);
    }

    /**
     * Nimmt den geänderten Skill entgegen und übergibt ihn dem SkillInteractor, wenn der Skill valide ist.
     * @param userId ID des Benutzers, dessen Skill geändert wird
     * @param skill geänderter Skill
     * @param model Model für die View
     * @param result Validierungsergebnis
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{skillId}", method = RequestMethod.POST)
    public String update(@PathVariable final Long userId, @Valid final Skill skill,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

        setAllKnowledges(model);
        setAllSkillLevel(model);

        if (result.hasErrors()) {
            markErrorFields(result.getFieldErrors(), model);
            model.addAttribute("skill", skill);
            setKnowledgeDisabled(model);
            return EDIT_SKILL;
        }

        try {
            Skill updatedSkill = skillInteractor.update(skill);
            String successMessage = String.format("%s wurde erfolgreich geändert.", updatedSkill.getKnowledge().getName());
            addSuccessFlashMessage(successMessage, redirectAttributes);
            return redirectTo(String.format(REDIRECT_BASE_URL, userId));
        }
        catch (IllegalUserArgumentsException userArgumentException) {
            model.addAttribute("skill", skill);
            setKnowledgeDisabled(model);
            model.addAttribute("errorMessage", userArgumentException.getErrorDescription());
            markErrorFields(userArgumentException.getAttributeMap(), model);
            return EDIT_SKILL;
        }
    }

    private void setAllKnowledges(final ModelMap model) {
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        Collection<Knowledge> allKnowledges = knowledgeInteractor.findAll(sort);
        model.addAttribute("allKnowledges", allKnowledges);
    }

    private void setAllSkillLevel(final ModelMap model) {
        Sort sort = new Sort(Sort.Direction.ASC, "internalValue");
        Collection<SkillLevel> allSkillLevel = skillLevelInteractor.findAll(sort);
        model.addAttribute("allSkillLevel", allSkillLevel);
    }

    private User getUser(final Long id) {
        return userInteractor.findOne(id);
    }
}

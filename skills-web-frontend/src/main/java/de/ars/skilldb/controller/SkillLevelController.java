package de.ars.skilldb.controller;

import java.util.Collection;

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

import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.interactor.SkillLevelInteractor;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Controller, der die Benutzeranfragen zu Skill-Leveln entgegenimmt, weiterreicht und
 * die Antwort für den Benutzer aufbereitet.
 */
@Controller
@RequestMapping("/skilllevel")
@SessionAttributes("skillLevel")
public class SkillLevelController extends AbstractController {


    private static final String BASE_URL = "/skilllevel/";
    private static final String LIST_ALL_SKILLS = BASE_URL + "list";
    private static final String INFO = BASE_URL + "info";
    private static final String CREATE_SKILL_LEVEL = BASE_URL + "create";
    private static final String EDIT_SKILL_LEVEL = BASE_URL + "edit";

    @Autowired
    private SkillLevelInteractor skillLevelInteractor;

    /**
     * Füllt die View für die Anzeige der exisitierenden Skill-Level.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAll(final ModelMap model) {
        Sort sort = new Sort(Sort.Direction.ASC, "internalValue");
        Collection<SkillLevel> allSkillLevel = skillLevelInteractor.findAll(sort);

        model.addAttribute("allSkillLevel", allSkillLevel);

        return LIST_ALL_SKILLS;
    }

    /**
     * Füllt die View für die Anzeige der exisitierenden Skill-Level.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String getInfo(final ModelMap model) {
        Sort sort = new Sort(Sort.Direction.ASC, "internalValue");
        Collection<SkillLevel> allSkillLevel = skillLevelInteractor.findAll(sort);

        model.addAttribute("allSkillLevel", allSkillLevel);

        return INFO;
    }

    /**
     * Erstellt die Inhalte für das Formular zur Eingabe eines neuen Skill-Levels.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping("/create")
    public String create(final ModelMap model) {
        model.addAttribute("skillLevel", new SkillLevel());

        return CREATE_SKILL_LEVEL;
    }

    /**
     * Erstellt ein Skill-Level anhand der validen Benutzereingaben und übergibt dieses dem SkillLevelInteractor.
     * @param skillLevel Anzulegendes Skill-Level
     * @param result Validiert die Benutzereingaben
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("skillLevel") final SkillLevel skillLevel,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("skillLevel", skillLevel);
            markErrorFields(result.getFieldErrors(), model);
            return CREATE_SKILL_LEVEL;
        }

        SkillLevel savedSkillLevel;
        try {
            savedSkillLevel = skillLevelInteractor.add(skillLevel);
            String successMessage = String.format("%s %s wurde erfolgreich angelegt.", savedSkillLevel.getInternalValue(), savedSkillLevel.getName());
            addSuccessFlashMessage(successMessage, redirectAttributes);
            return redirectTo(BASE_URL);
        }
        catch (IllegalUserArgumentsException userArgumentException) {
            model.addAttribute("skillLevel", skillLevel);
            model.addAttribute("errorMessage", userArgumentException.getErrorDescription());
            markErrorFields(userArgumentException.getAttributeMap(), model);
            return CREATE_SKILL_LEVEL;
        }
    }

    /**
     * Befüllt das Formular zum Bearbeiten mit den Daten des zu bearbeitenden Skill-Levels.
     * @param skillLevelId ID des Skill-Levels
     * @param model Model für die View
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{skillLevelId}", method = RequestMethod.GET)
    public String edit(@PathVariable final Long skillLevelId, final ModelMap model, final RedirectAttributes redirectAttributes) {
        SkillLevel skillLevel = skillLevelInteractor.findOne(skillLevelId);

        if (skillLevel == null) {
            addErrorFlashMessage("Es existiert kein Skill-Level mit der ID " + skillLevelId, redirectAttributes);
            return redirectTo(BASE_URL);
        }

        model.addAttribute("skillLevel", skillLevel);
        addHistory(skillLevel.getCreated(), skillLevel.getLastModified(), model);

        return EDIT_SKILL_LEVEL;
    }

    /**
     * Nimmt das geänderte Skill-Level entgegen und übergibt es dem SkillLevelInteractor, wenn es valide ist.
     * @param skillLevel Skill-Level
     * @param model Model für die View
     * @param result Validierungsergebnis
     * @param redirectAttributes dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{skillLevelId}", method = RequestMethod.POST)
    public String update(@Valid final SkillLevel skillLevel,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            markErrorFields(result.getFieldErrors(), model);
            model.addAttribute("skillLevel", skillLevel);
            return EDIT_SKILL_LEVEL;
        }

        try {
            SkillLevel savedSkillLevel = skillLevelInteractor.update(skillLevel);
            String successMessage = String.format("%s %s wurde erfolgreich geändert.", savedSkillLevel.getInternalValue(), savedSkillLevel.getName());
            addSuccessFlashMessage(successMessage, redirectAttributes);

            return redirectTo(BASE_URL);
        }
        catch (IllegalUserArgumentsException userArgumentException) {
            model.addAttribute("skillLevel", skillLevel);
            model.addAttribute("errorMessage", userArgumentException.getErrorDescription());
            markErrorFields(userArgumentException.getAttributeMap(), model);
            return EDIT_SKILL_LEVEL;
        }
    }
}

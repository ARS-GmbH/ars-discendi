package de.ars.skilldb.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.KnowledgeInteractor;
import de.ars.skilldb.interactor.SkillInteractor;
import de.ars.skilldb.interactor.SkillLevelInteractor;
import de.ars.skilldb.interactor.UserInteractor;

/**
 * Controller, der die Benutzeranfragen zu Skills entgegenimmt, weiterreicht und
 * die Antwort die den Benutzer aufbereitet.
 */
@Controller
@RequestMapping("search")
public class SearchController extends AbstractController {

    private static final String NO_SKILLS_FOUND = "Es wurden keine Skills gefunden.";

    @Autowired
    private SkillInteractor skillInteractor;

    @Autowired
    private KnowledgeInteractor knowledgeInteractor;

    @Autowired
    private SkillLevelInteractor skillLevelInteractor;

    @Autowired
    private UserInteractor userInteractor;

    private static final String BASE_URL = "/search/";
    private static final String USER = BASE_URL + "user";
    private static final String SKILLS = BASE_URL + "skills";

    /**
     * Füllt die View für die Anzeige der Suche nach Mitarbeitern.
     * @param model Model für die View
     * @param knowledge
     *            Wissensgebiet, das der Benutzer beherrschen soll
     * @param skillLevel
     *            SkillLevel, das der Benutzer zum Wissensgebiet beherschen soll
     * @return logischer Viewname
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String searchForUsersByKnowledge(final ModelMap model, @RequestParam(value = "knowledge", defaultValue = "") final String knowledge,
            @RequestParam(value = "skillLevel", defaultValue = "") final String skillLevel) {
        setAllKnowledges(model, knowledge);
        setAllSkillLevel(model, skillLevel);

        if (knowledge.isEmpty()) {
            setTableVisibilityHidden(model);
            return USER;
        }

        int level;
        try {
            level = checkSkillLevel(skillLevel);
        }
        catch (NumberFormatException exception) {
            model.addAttribute("error", "Das Skill-Level muss eine Zahl oder leer sein.");
            setTableVisibilityHidden(model);
            return USER;
       }

        Collection<Skill> foundSkills = skillInteractor.findByKnowledgeNameAndSkillLevel(knowledge, level);

        for (Skill skill : foundSkills) {
            User user = userInteractor.findOne(skill.getUser().getId());
            skill.setUser(user);
        }

        if (foundSkills.isEmpty()) {
            model.addAttribute("error", NO_SKILLS_FOUND);
            setTableVisibilityHidden(model);
            return USER;
        }

         model.addAttribute("skills", foundSkills);

        return USER;
    }

    /**
     * Füllt die View für die Anzeige der Suche nach Skills.
     * @param model Model für die View
     * @param userId
     *            Mitarbeiter, dessen Skills angezeigt werden sollen
     * @return logischer Viewname
     */
    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    public String searchForSkillsByUser(final ModelMap model, @RequestParam(value = "user", defaultValue = "") final String userId) {
        setAllUsers(model, userId);

        if (userId.isEmpty()) {
            setTableVisibilityHidden(model);
            return SKILLS;
        }

        User searchedUser;
        try {
            searchedUser = userInteractor.findOne(Long.parseLong(userId));
        }
        catch (NumberFormatException exception) {
            model.addAttribute("error", "Die übergebene ID ist nicht valide.");
            setTableVisibilityHidden(model);
            return SKILLS;
       }

        Collection<Skill> userSkills = skillInteractor.findUserSkills(searchedUser);
        searchedUser.setSkills(userSkills);

        if (userSkills.isEmpty()) {
            model.addAttribute("error", NO_SKILLS_FOUND);
            setTableVisibilityHidden(model);
            return SKILLS;
        }

        if (searchedUser.getId().equals(getCurrentUser().getId())) {
            model.addAttribute("isCurrentUser", true);
        }

        model.addAttribute("user", searchedUser);

        return SKILLS;
    }

    private int checkSkillLevel(final String skillLevel) {
        if ("".equals(skillLevel)) {
            return 0;
        }
        else {
            return Integer.parseInt(skillLevel);
        }
    }

    private void setTableVisibilityHidden(final ModelMap model) {
        model.addAttribute("tableVisibility", "hidden");
    }

    private void setAllKnowledges(final ModelMap model, final String knowledge) {
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        Collection<Knowledge> allKnowledges = knowledgeInteractor.findAll(sort);
        model.addAttribute("allKnowledges", allKnowledges);
        model.addAttribute("selectedKnowledge", knowledge);
    }

    private void setAllSkillLevel(final ModelMap model, final String skillLevel) {
        Sort sort = new Sort(Sort.Direction.ASC, "internalValue");
        Collection<SkillLevel> allSkillLevel = skillLevelInteractor.findAll(sort);
        model.addAttribute("allSkillLevel", allSkillLevel);
        model.addAttribute("selectedLevel", skillLevel);
    }

    private void setAllUsers(final ModelMap model, final String userId) {
        Sort sort = new Sort(Sort.Direction.ASC, "lastName");
        Collection<User> allUsers = userInteractor.findAll(sort);
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("selectedUser", userId);
    }
}

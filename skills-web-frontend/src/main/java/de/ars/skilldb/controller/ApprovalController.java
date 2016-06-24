package de.ars.skilldb.controller;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillProofEdge;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.KnowledgeInteractor;
import de.ars.skilldb.interactor.SkillInteractor;
import de.ars.skilldb.interactor.SkillLevelInteractor;
import de.ars.skilldb.interactor.SkillProofEdgeInteractor;
import de.ars.skilldb.interactor.UserInteractor;

/**
 * Controller, der die Benutzeranfragen zu Freigaben entgegenimmt, weiterreicht und
 * die Antwort die den Benutzer aufbereitet.
 */
@Controller
@RequestMapping("/approval/")
public class ApprovalController extends AbstractController {

    private static final String BASE_URL = "/approval/";
    private static final String LIST_ALL_SUBMITTED = BASE_URL + "list";

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
     * Füllt die View für die Anzeige der freizugebenden Skills.
     *
     * @param model
     *            Model für die View
     * @return logischer Viewname
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getUserSkills(final ModelMap model) {
        User user = getCurrentUser();
        Collection<User> users = userInteractor.findUsersWithSubmittedSkills(user);

        for (User singleUser : users) {
            Collection<Skill> skills = skillInteractor.findSubmittedSkills(singleUser);

            for (Skill skill : skills) {
                Set<SkillProofEdge> edges = skillProofEdgeInteractor.findEdgesBySkill(skill);
                skill.setSkillProofEdges(edges);
            }
            singleUser.setSkills(skills);
        }
        model.addAttribute("users", users);

        model.addAttribute("isManager", true);

        if (users.isEmpty()) {
            model.addAttribute("isError", true);
            model.addAttribute("errorMessage", "Es wurden keine Skill-Freigaben beantragt.");
        }

        return LIST_ALL_SUBMITTED;
    }

    /**
     * Gibt einen beantragten Skill frei.
     * @param model Model für die View
     * @param skillId ID des Skills, der freigegeben werden soll
     * @return logischer Viewname
     */
    @RequestMapping(value = "/{skillId}", method = RequestMethod.GET)
    public String approveUserSkill(final ModelMap model, @PathVariable final Long skillId) {
        Skill skillToApprove = skillInteractor.findOne(skillId);
        skillInteractor.approve(skillToApprove);
        return redirectTo(BASE_URL);
    }

}

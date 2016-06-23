package de.ars.skilldb.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.ars.skilldb.domain.ProjectParticipation;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.ProjectInteractor;
import de.ars.skilldb.interactor.ProjectParticipationInteractor;
import de.ars.skilldb.interactor.SkillInteractor;
import de.ars.skilldb.interactor.SkillProofEdgeInteractor;
import de.ars.skilldb.interactor.UserInteractor;

/**
 * Controller, der die Benutzeranfragen zu Nachweisen entgegen nimmt, weiterreicht und
 * die Antwort für den Benutzer aufbereitet.
 */
@Controller
@RequestMapping("/users/{userId}/proofs/")
public class ProofController extends AbstractController {

    private static final String BASE_URL = "/proofs/";
    private static final String LIST_ALL_PROOFS = BASE_URL + "list";

    @Autowired
    private ProjectParticipationInteractor projectParticipationInteractor;

    @Autowired
    private SkillProofEdgeInteractor skillProofEdgeInteractor;

    @Autowired
    private ProjectInteractor projectInteractor;

    @Autowired
    private SkillInteractor skillInteractor;

    @Autowired
    private UserInteractor userInteractor;

    /**
     * Füllt die View für die Anzeige der Nachweise des Benutzers.
     * @param model Model für die View
     * @return logischer Viewname
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAll(final ModelMap model) {
        User currentUser = getCurrentUser();
        Collection<ProjectParticipation> allProjectParticipations = projectParticipationInteractor.findByUser(currentUser);

        model.addAttribute("user", currentUser);
        model.addAttribute("allProjectParticipations", allProjectParticipations);

        return LIST_ALL_PROOFS;
    }

}

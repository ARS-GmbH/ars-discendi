/**
 *
 */
package de.ars.skilldb.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import de.ars.skilldb.controller.util.SearchResult;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.AccomplishedCertificationInteractor;
import de.ars.skilldb.interactor.AccomplishedExamInteractor;
import de.ars.skilldb.interactor.UserInteractor;

/**
 * Controller zur Verarbeitung von (allen) Benutzern.
 */
@Controller
@RequestMapping("/users")
@SessionAttributes("user")
public class UserController extends AbstractController {

    @Autowired
    private UserInteractor userInteractor;

    @Autowired
    private AccomplishedCertificationInteractor accomCertInteractor;

    @Autowired
    private AccomplishedExamInteractor accomExamInteractor;

    /**
     * Liefert alle {@code User}s zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping
    public String listAll(final ModelMap model) {

        Collection<User> users = userInteractor.findAll();
        model.addAttribute("users", users);

        return "users/list";
    }

    /**
     * Zeigt alle Informationen zu einem Benutzer.
     *
     * @param model
     *            das View-Model von Spring.
     * @param id
     *            die ID des zu zeigenden {@code User}s.
     * @return den logischen View-Namen.
     */
    @RequestMapping(value = "/{id}")
    public String show(final ModelMap model, @PathVariable final Long id) {

        User user = userInteractor.findOne(id);
        model.addAttribute("userCerts", accomCertInteractor.fetchFullData(accomCertInteractor.findByUser(user)));
        model.addAttribute("userExams", accomExamInteractor.findAllAccomplishedExams(user));
        model.addAttribute("user", user);

        return "users/show";
    }

    /**
     * Sucht nach einem Benutzer, der den Suchbegriff im Namen trägt. Gibt maximal 5 Ergebnisse
     * zurück.
     *
     * @param model
     *            das View-Model von Spring
     * @param searchQuery
     *            der Suchbegriff.
     * @return eine Liste von Suchergebnissen, die von Spring in JSON umgewandelt wird.
     */
    @RequestMapping(value = "/search")
    @ResponseBody
    public Collection<SearchResult> searchForExams(final ModelMap model, @RequestParam("q") final String searchQuery) {

        Collection<User> foundUsers = userInteractor.findByNameContaining(searchQuery, 0, 5);
        List<SearchResult> searchResults = new ArrayList<>();
        for (User user : foundUsers) {
            SearchResult result = new SearchResult(user.getFirstName() + " " + user.getLastName(), user.getId()
                    .toString());
            searchResults.add(result);
        }

        return searchResults;
    }

}

package de.ars.skilldb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller zur Verarbeitung des Login und Logout.
 */
@Controller
@RequestMapping("/")
public class LoginController extends AbstractController {

    /**
     * Liefert die Login-Seite zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @param error
     *            zeigt an, dass bei der Anmeldung ein Fehler aufgetreten ist.
     * @param logout
     *            zeigt an, wenn die Anmeldeseite aufgrund des Logouts angezeigt wird.
     * @return den logischen View-Namen.
     */
    @RequestMapping("login")
    public String login(@RequestParam(value = "error", required = false) final String error,
                        @RequestParam(value = "logout", required = false) final String logout,
                        final ModelMap model) {

        if (error != null) {
            addErrorMessage("Benutzername oder Passwort ist nicht korrekt!", model);
        }

        if (logout != null) {
            addSuccessMessage("Abmeldung erfolgreich!", model);
        }

        return "login";
    }

    /**
     * Falls ein Angestellter die Basis-URL aufruft, wird zu den Zertifizierungen weiter geleitet.
     *
     * @return einen Redirect zu den Zertifizierungen.
     */
    @RequestMapping
    public String redirectToBase() {
        return redirectTo("certifications");
    }

}

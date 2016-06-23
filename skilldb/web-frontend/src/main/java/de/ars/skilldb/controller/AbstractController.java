package de.ars.skilldb.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.UserInteractor;

/**
 * Basisklasse für alle Controller.
 *
 */
public class AbstractController {

    @Autowired
    private UserInteractor userInteractor;

    private static final String REDIRECT = "redirect:";

    /**
     * Gibt den aktuellen Benutzer aus der Session zurück.
     * @return aktueller Benutzer
     */
    protected User getCurrentUser() {
        //TODO: CurrentUser aus der Session holen.
        Long currentId = 80L;
        return userInteractor.findOne(currentId);
    }

    /**
     * Erzeugt einen neuen Eintrag in die RedirectAttributes für eine Erfolgsmeldung.
     * @param message Nachricht, die ausgegeben werden soll.
     * @param redirectAttributes Dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten.
     */
    protected void addSuccessFlashMessage(final String message, final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", message);
    }

    /**
     * Erzeugt einen neuen Eintrag in die RedirectAttributes für eine Fehlermeldung.
     * @param message - Nachricht, die an den Benutzer ausgegeben wird
     * @param redirectAttributes Dadurch werden Ausgaben an den Benutzer trotz redirect im Model gehalten.
     */
    protected void addErrorFlashMessage(final String message, final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", message);
    }

    /**
     * Erzeugt einen redirect zur gewünschten Seite.
     * @param path Pfad zu dem umgeleitet werden soll.
     * @return redirect mit gewünschtem Pfad.
     */
    protected String redirectTo(final String path) {
        return REDIRECT + path;
    }

    /**
     * Erzeugt einen redirect zur gewünschten Seite.
     * @param formattedPath Pfad zu dem umgeleitet werden soll.
     * @param args Argumente, die im formattierten String verwendet werden
     * @return redirect mit gewünschtem Pfad.
     */
    protected String redirectTo(final String formattedPath, final Object... args) {
        return String.format(REDIRECT + formattedPath, args);
    }


    /**
     * Fügt die History-Informationen der Map hinzu.
     * @param created Datum, an dem das Objekt erzeugt wurde
     * @param lastModified Datum, an dem das Objekt das letzte Mal geändert wurde
     * @param model Model, in dem die Informationen an die View übergeben werden
     */
    protected void addHistory(final Date created, final Date lastModified, final ModelMap model) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy kk:mm", Locale.GERMAN);
        model.addAttribute("created", formatter.format(created));
        if (lastModified != null) {
            model.addAttribute("lastModified", formatter.format(lastModified));
        }
    }

    /**
     * Markiert alle übergebenen Felder in der View mit einem roten Rahmen.
     * @param errors Liste mit allen Feldern, die einen Fehler enthalten
     * @param model Model, in dem die Informationen an die View übergeben werden
     */
    protected void markErrorFields(final List<FieldError> errors, final ModelMap model) {
        for (FieldError error : errors) {
            model.addAttribute(error.getField() + "Error", true);
        }
    }

    /**
     * Markiert alle übergebenen Felder in der View mit einem roten Rahmen.
     * @param errors Liste mit allen Feldern, die einen Fehler enthalten
     * @param model Model, in dem die Informationen an die View übergeben werden
     */
    protected void markErrorFields(final Map<String, String> errors, final ModelMap model) {
        for (Map.Entry<String, String> error : errors.entrySet()) {
            model.addAttribute(error.getKey() + "Error", true);
        }
    }
}

/**
 *
 */
package de.ars.skilldb.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.AccomplishedCertification;
import de.ars.skilldb.domain.PlannedCertification;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.*;

/**
 * Controller zur Verarbeitung von eigenen Zertifizierungen.
 */
@Controller
@RequestMapping("/mycertifications")
public class MyCertificationController extends AbstractController {

    @Autowired
    private AccomplishedCertificationInteractor accomplishedCerts;

    @Autowired
    private PlannedCertificationInteractor plannedCerts;

    @Autowired
    private CertificationInteractor certs;

    @Autowired
    private PathInteractor paths;

    /**
     * Liefert alle eigenen Zertifizierungen zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @param page
     *            die Seite, die zurückgegeben werden soll.
     * @param size
     *            die Anzahl der Elemente pro Seite.
     * @return den logischen View-Namen.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getCertifications(final ModelMap model,
            @RequestParam(value = "page", defaultValue = "1") final int page,
            @RequestParam(value = "size", defaultValue = "20") final int size) {

        User user = getActualLoggedInUser();
        model.addAttribute("accomplishedCertifications",
                accomplishedCerts.fetchFullData(accomplishedCerts.findByUser(user)));
        return "mycertification/list";
    }

    /**
     * Liefert die View zum Erzeugen einer neuen abgelegten Zertifizierung zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @param id
     *            die Id der Zertifizierung, die abgeschlossen werden soll.
     * @return den logischen View-Namen.
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(final ModelMap model, @RequestParam(required = false) final Long id) {
        AccomplishedCertification accomplished = new AccomplishedCertification();
        if (id != null && id >= 0) {
            accomplished.setCertification(certs.findOne(id));
        }
        model.addAttribute("accomplishedCertification", accomplished);

        return "mycertification/form";
    }

    /**
     * Liefert die View zurück, in der alle geplanten Zertifizierungen des aktuellen Benutzers
     * ausgegeben werden.
     *
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping(value = "/planned")
    public String getMyPlannedCertifications(final ModelMap model) {
        Collection<PlannedCertification> planned = plannedCerts.fetchFullData(plannedCerts.findByUser(getActualLoggedInUser()));
        for (PlannedCertification plannedCertification : planned) {
            plannedCertification.setPath(paths.fetchFullData(plannedCertification.getPath()));
        }
        model.addAttribute("myPlannedCertifications", planned);

        return "mycertification/planned/list";
    }

    /**
     * Fügt eine vom Benutzer neu angelegte, abgeschlossene Zertifizierung hinzu.
     *
     * @param accomplishedCertification
     *            die neue abgeschlossene Zertifizierung.
     * @param file
     *            das Dokument, das den Nachweis liefert.
     * @param result
     *            das Ergebnis der Validierung.
     * @param model
     *            das View-Model von Spring.
     * @param redirectAttributes
     *            die {@code RedirectAttributes}, zu denen View-Attribute bei einem Redirect
     *            hinzugefügt werden können.
     * @return den Redirect im Erfolgsfall, andernfalls den logischen View-Namen zur erneuten
     *         Bearbeitung.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute final AccomplishedCertification accomplishedCertification,
            @RequestParam("file") final MultipartFile file, final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes) {

        try {
            checkFileUploadConstraints(file);
            User user = getActualLoggedInUser();
            accomplishedCertification.setUser(user);
            accomplishedCertification.setCarryOutDate(new Date());
            accomplishedCerts.save(accomplishedCertification, file.getBytes());
            plannedCerts.deleteByAccomplish(user, accomplishedCertification.getCertification());
            addSuccessMessage("Die Zertifizierung '" + STRONG + accomplishedCertification.getCertification().getName()
                    + STRONG_END + "' wurde erfolgreich abgelegt.", redirectAttributes);
            return redirectTo("mycertifications");
        }
        catch (IOException e) {
            addErrorMessage("Fehler beim Hochladen der Datei. " + e.getMessage(), redirectAttributes);
            return redirectTo("mycertifications");
        }
        catch (IllegalStateException e) {
            addErrorMessage("Du kannst diese Zertifizierung nicht ablegen, da du hierfür "
                    + "noch nicht alle notwendigen Tests und/oder Zertifizierungen abgelegt hast.", redirectAttributes);
            return redirectTo("mycertifications");
        }
        catch (FileUploadException e) {
            addErrorMessage(e.getMessage(), redirectAttributes);
            return redirectTo("mycertifications/create");
        }
    }

    /**
     * Stellt den Download für das Dokument der abgeschlossenen Zertifizierung mit der übergebenen
     * Id bereit.
     *
     * @param id
     *            die Id der abgeschlossenen Zertifizierung, zu dem das Dokument herunter geladen
     *            werden soll.
     * @return das Dokument.
     */
    @RequestMapping(value = "/{id}/document", produces = "application/pdf")
    @ResponseBody
    public byte[] getAccomplishedDocument(@PathVariable final Long id) {
        return accomplishedCerts.getAccomplishedDocument(id);
    }
}

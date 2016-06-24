package de.ars.skilldb.controller;

import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.AccomplishedExam;
import de.ars.skilldb.domain.PlannedExam;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.AccomplishedExamInteractor;
import de.ars.skilldb.interactor.PlannedExamInteractor;

/**
 * Controller zur Verarbeitung der eigens abgelegten Tests.
 */
@Controller
@RequestMapping("/myexams")
@SessionAttributes({ "exam", "accomplishedExam" })
public class MyExamController extends AbstractController {

    private static final String MY_PLANNED_EXAMS_LIST = "myexams/planned/list";
    private static final String MY_ACCOMPLISHED_EXAMS_LIST = "myexams/accomplished/list";
    private static final String MY_PLANNED_EXAMS = "myexams/planned";

    @Autowired
    private AccomplishedExamInteractor accomplishedExamInteractor;

    @Autowired
    private PlannedExamInteractor plannedExamInteractor;

    /**
     * Liefert die View zur Anzeige der eigens abgelegten Tests zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getMyAccomplishedExams(final ModelMap model) {
        User actualUser = getActualLoggedInUser();

        Collection<AccomplishedExam> accomplished = accomplishedExamInteractor.findAllAccomplishedExams(actualUser);
        model.addAttribute("exams", accomplished);

        return MY_ACCOMPLISHED_EXAMS_LIST;
    }

    /**
     * Liefert die View zur Anzeige der eigenen, für sich geplanten Tests zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping(value = "/planned", method = RequestMethod.GET)
    public String getMyPlannedExams(final ModelMap model) {
        User actualUser = getActualLoggedInUser();

        Collection<PlannedExam> plannedExams = plannedExamInteractor.findAllMyPlannedExams(actualUser);
        model.addAttribute("exams", plannedExams);

        return MY_PLANNED_EXAMS_LIST;
    }

    /**
     * Liefert die View zur Anzeige der eigenen, für sich geplanten Tests zurück.
     *
     * @param id
     *            die Id des geplanten Tests.
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping(value = "/planned/{id}/accomplish", method = RequestMethod.GET)
    public String accomplishExam(@PathVariable final Long id, final ModelMap model) {
        PlannedExam planned = plannedExamInteractor.findOne(id);
        planned = plannedExamInteractor.fetchFullData(planned);
        model.addAttribute("planned", planned);
        AccomplishedExam accomplished = new AccomplishedExam();
        accomplished.setExam(planned.getExam());
        accomplished.setUser(planned.getUser());
        model.addAttribute("accomplishedExam", accomplished);

        return "myexams/planned/form";
    }

    /**
     * Wandelt einen geplanten Test in einen abgeschlossenen Test.
     *
     * @param accomplished
     *            der neue abgeschlossene Test.
     * @param file
     *            das Dokument, das den Nachweis liefert.
     * @param redirectAttributes
     *            die {@code RedirectAttributes}, zu denen View-Attribute bei einem Redirect
     *            hinzugefügt werden können.
     * @return den Redirect im Erfolgsfall, andernfalls den logischen View-Namen zur erneuten
     *         Bearbeitung.
     */
    @RequestMapping(value = "/accomplished", method = RequestMethod.POST)
    public String accomplishExam(@Valid @ModelAttribute("accomplishedExam") final AccomplishedExam accomplished,
            @RequestParam("file") final MultipartFile file, final RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            addErrorMessage("Es wurde keine oder eine leere Datei hochgeladen.", redirectAttributes);
            return redirectTo(MY_PLANNED_EXAMS);
        }
        if (!"application/pdf".equals(file.getContentType())) {
            addErrorMessage("Die Datei muss vom Typ 'PDF' sein.", redirectAttributes);
            return redirectTo(MY_PLANNED_EXAMS);
        }

        try {
            accomplishedExamInteractor.addAccomplishedExam(accomplished, file.getBytes());
        }
        catch (IOException e) {
            addErrorMessage("Fehler beim Hochladen der Datei. " + e.getMessage(), redirectAttributes);
            return redirectTo(MY_PLANNED_EXAMS);
        }

        addSuccessMessage("Der Test '" + STRONG + accomplished.getExam().getTitle() + STRONG_END
                + "' wurde erfolgreich als bestanden abgelegt.", redirectAttributes);
        return redirectTo(MY_PLANNED_EXAMS);
    }

    /**
     * Stellt den Download für das Dokument des abgeschlossenen Tests mit der übergebenen Id bereit.
     *
     * @param id
     *            die Id des abgeschlossenen Tests, zu dem das Dokument herunter geladen werden
     *            soll.
     * @return das Dokument.
     */
    @RequestMapping(value = "/accomplished/{id}/document", produces = "application/pdf")
    @ResponseBody
    public byte[] getAccomplishedDocument(@PathVariable final Long id) {
        return accomplishedExamInteractor.getAccomplishedDocument(id);
    }

}

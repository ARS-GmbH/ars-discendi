package de.ars.skilldb.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.controller.util.SearchResult;
import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.Exam;
import de.ars.skilldb.domain.PlannedExam;
import de.ars.skilldb.interactor.CertificationInteractor;
import de.ars.skilldb.interactor.ExamInteractor;
import de.ars.skilldb.interactor.PlannedExamInteractor;

/**
 * Controller zur Verarbeitung von (allen) Tests.
 */
@Controller
@RequestMapping("/exams")
@SessionAttributes({ "exam", "plannedExam" })
public class ExamController extends AbstractController {

    private static final String EXAMS_PLANNED_CREATE = "exams/planned/create";
    private static final String EXAM_CREATE = "exams/create";
    private static final String EXAM_LIST = "exams/list";
    private static final String EXAMS_PLAN = "exams/plan";

    @Autowired
    private CertificationInteractor certificationInteractor;

    @Autowired
    private ExamInteractor examInteractor;

    @Autowired
    private PlannedExamInteractor plannedExamInteractor;

    /**
     * Liefert alle Tests zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @param page
     *            die Seite, die zurückgegeben werden soll.
     * @param size
     *            die Anzahl der Elemente pro Seite.
     * @return den logischen View-Namen.
     */
    @RequestMapping
    public String listAll(final ModelMap model, @RequestParam(value = "page", defaultValue = "1") final int page,
            @RequestParam(value = "size", defaultValue = "20") final int size) {

        int requestedPageId = page - 1;
        Collection<Exam> response = examInteractor.findAll(requestedPageId, size);
        model.addAttribute("exams", response);
        long total = examInteractor.count();
        addPagerConfigurationTo(model, page, size, total);

        return EXAM_LIST;
    }

    /**
     * Liefert die View zur Neuanlage eines Tests zurück.
     *
     * @param certId
     *            die Id der Zertifizierung
     * @param model
     *            das View-Model von Spring.
     * @param request
     *            der HttpRequest.
     * @return den Redirect im Erfolgsfall, andernfalls den logischen View-Namen zur erneuten
     *         Bearbeitung.
     */
    @RequestMapping("/create")
    public String create(@RequestParam(value = "forcert", required = false, defaultValue = "-1") final long certId,
            final ModelMap model, final HttpServletRequest request) {
        if (certId != -1) {
            Certification certification = certificationInteractor.findOne(certId);
            model.addAttribute("certLink", certification.getLink());
            model.addAttribute("certName", certification.getName());
        }
        model.addAttribute("exam", new Exam());
        model.addAttribute("certificationOptions", new HashSet<Certification>());
        String nextPage = certId == -1L ? "exams" : "certifications/" + certId + "/pathedit?planning=true";
        request.getSession().setAttribute("nextPage", nextPage);

        return EXAM_CREATE;
    }

    /**
     * Fügt einen vom Benutzer neu angelegten Test hinzu.
     *
     * @param exam
     *            die neue {@code Exam}.
     * @param result
     *            das Ergebnis der Validierung.
     * @param model
     *            das View-Model von Spring.
     * @param redirectAttributes
     *            die {@code RedirectAttributes}, zu denen View-Attribute bei einem Redirect
     *            hinzugefügt werden können.
     * @param request
     *            der HttpRequest.
     * @return den Redirect im Erfolgsfall, andernfalls den logischen View-Namen zur erneuten
     *         Bearbeitung.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("exam") final Exam exam, final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request) {

        if (result.hasErrors()) {
            return redirectWithErrors(EXAM_CREATE, result, redirectAttributes);
        }
        examInteractor.save(exam);

        addSuccessMessage("Der Test <strong>'" + exam.getTitle() + WURDE_ERFOLGREICH_ANGELEGT, redirectAttributes);
        return redirectTo((String) request.getSession().getAttribute("nextPage"));
    }

    /**
     * Liefert die View zur Bearbeitung eines Tests zurück.
     *
     * @param id
     *            die ID des zu bearbeitenden Tests.
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final ModelMap model) {
        Exam examToEdit = examInteractor.findOne(id);
        model.addAttribute("exam", examToEdit);
        model.addAttribute("certificationOptions", examToEdit.getCertifications());

        return "exams/edit";
    }

    /**
     * Liefert die View zur Planung eines Tests zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping("/plan")
    public String plan(final ModelMap model) {
        PlannedExam planned = new PlannedExam();
        planned.setPlanner(getActualLoggedInUser().getUserName());
        model.addAttribute("plannedExam", planned);

        return EXAMS_PLANNED_CREATE;
    }

    /**
     * Fügt einen vom Benutzer geplanten Test hinzu.
     *
     * @param plannedExam
     *            die neue {@code PlannedExam}.
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
    @RequestMapping(value = "/plan", method = RequestMethod.POST)
    public String addPlanned(@Valid @ModelAttribute("plannedExam") final PlannedExam plannedExam,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return redirectWithErrors(EXAM_CREATE, result, redirectAttributes);
        }

        try {
            plannedExamInteractor.addPlannedExam(plannedExam);
            addSuccessMessage("Der geplante Test für <strong>'" + plannedExam.getUser().getFirstName() + " "
                    + plannedExam.getUser().getLastName() + WURDE_ERFOLGREICH_ANGELEGT, redirectAttributes);
            return redirectTo(EXAMS_PLAN);
        }
        catch (IllegalStateException e) {
            addErrorMessage("Für den Mitarbeiter ist dieser Test bereits geplant oder abgeschlossen.",
                    redirectAttributes);
            return redirectTo(EXAMS_PLAN);
        }
    }

    /**
     * Sucht nach einem Test, die den Suchbegriff im Namen trägt. Gibt maximal 5 Ergebnisse zurück.
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

        Collection<Exam> foundExams = examInteractor.findByTitleContaining(searchQuery, 0, 5);
        List<SearchResult> searchResults = new ArrayList<>();
        for (Exam exam : foundExams) {
            SearchResult result = new SearchResult(exam.getTitle(), exam.getId().toString());
            searchResults.add(result);
        }

        return searchResults;
    }
}
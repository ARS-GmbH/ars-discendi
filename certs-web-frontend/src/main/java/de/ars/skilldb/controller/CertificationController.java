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

import com.google.common.base.Strings;

import de.ars.skilldb.command.PathListCommand;
import de.ars.skilldb.controller.util.SearchResult;
import de.ars.skilldb.domain.*;
import de.ars.skilldb.domain.enums.CertKind;
import de.ars.skilldb.domain.enums.CertStatus;
import de.ars.skilldb.exception.InvalidModelException;
import de.ars.skilldb.interactor.*;
import de.ars.skilldb.returnobjects.CertificationRevision;

/**
 * Controller zur Verarbeitung von Zertifizierungen.
 */
@Controller
@RequestMapping("/certifications")
@SessionAttributes("cert")
public class CertificationController extends AbstractController {

    private static final String SLASH = "/";
    private static final String BASE_URL = "certifications";
    private static final String EDIT = "/edit";
    private static final String CERTIFICATION_EDIT = BASE_URL + EDIT;
    private static final String CERTIFICATION_SHOW = BASE_URL + "/show";
    private static final String CERTIFICATION_CREATE = BASE_URL + "/create";
    private static final String CERTIFICATION_LIST = BASE_URL + "/list";
    private static final String PLAN_CERTIFICATION = "certifications/planned/create";

    @Autowired
    private CertificationInteractor certInteractor;

    @Autowired
    private BrandInteractor brandInteractor;

    @Autowired
    private ProductGroupInteractor productGroupInteractor;

    @Autowired
    private PathInteractor pathInteractor;

    @Autowired
    private AccomplishedCertificationInteractor accomplishedCertInteractor;

    @Autowired
    private PlannedCertificationInteractor plannedCertificationInteractor;

    /**
     * Liefert alle Zertifizierungen zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @param page
     *            die Seite, die zurückgegeben werden soll.
     * @param size
     *            die Anzahl der Elemente pro Seite.
     * @param query
     *            der Suchbegriff (optional).
     * @return den logischen View-Namen.
     */
    @RequestMapping
    public String showAll(final ModelMap model,
            @RequestParam(value = "page", defaultValue = "1") final int page,
            @RequestParam(value = "size", defaultValue = "20") final int size,
            @RequestParam(value = "q", defaultValue = "") final String query) {

        int requestedPageId = page - 1;
        model.addAttribute("query", query);

        Collection<Certification> response;
        long total;
        if (Strings.isNullOrEmpty(query)) {
            response = certInteractor.findAll(requestedPageId, size);
            total = certInteractor.count();
        }
        else {
            response = certInteractor.findByNameContaining(query, requestedPageId, size);
            total = certInteractor.countWithNameContaining(query);
        }
        Collection<Certification> updated = certInteractor.findUpdated();
        Collection<Certification> newOnes = certInteractor.findNewOnes();
        model.addAttribute("allCertifications", response);
        model.addAttribute("updatedCertifications", updated);
        model.addAttribute("addCertifications", newOnes);

        addPagerConfigurationTo(model, page, size, total);

        return CERTIFICATION_LIST;
    }

    /**
     * Liefert alle Zertifizierungen zurück, die im Unternehmen von Mitarbeitern erworben wurden.
     *
     * @param model
     *            das View-Model von Spring.
     * @param page
     *            die Seite, die zurückgegeben werden soll.
     * @param size
     *            die Anzahl der Elemente pro Seite.
     * @return den logischen View-Namen.
     */
    @RequestMapping("/employees")
    public String showOfEmployees(final ModelMap model,
            @RequestParam(value = "page", defaultValue = "1") final int page,
            @RequestParam(value = "size", defaultValue = "20") final int size) {

        int requestedPageId = page - 1;
        Collection<AccomplishedCertification> allAccomplished = accomplishedCertInteractor
                .fetchFullData(accomplishedCertInteractor.findAll(requestedPageId, size));
        long total = accomplishedCertInteractor.findAll().size();
        addPagerConfigurationTo(model, page, size, total);
        model.addAttribute("accomplishedCertifications", allAccomplished);

        return "certifications/employees-list";
    }

    /**
     * Triggert das initiale Einlesen der IBM-Zertifizierungen von der Webseite.
     *
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping("/load")
    public String loadCertificationsInitially(final ModelMap model) {
        certInteractor.loadCertificationsInitially();

        return redirectTo(BASE_URL);
    }

    /**
     * Triggert das Updaten der IBM-Zertifizierungen von der Webseite.
     *
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping("/update")
    public String updateCertifications(final ModelMap model) {
        certInteractor.updateCertifications();

        return redirectTo(BASE_URL);
    }

    /**
     * Speichert eine Zertifizierung ab.
     *
     * @param cert
     *            die {@code Certification}, die abgespeichert werden soll.
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
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("cert") final Certification cert, final BindingResult result,
            final ModelMap model, final RedirectAttributes redirectAttributes) {

        String certView = cert.getId() == null ? CERTIFICATION_CREATE : CERTIFICATION_EDIT;
        try {
            checkBindingResult(result);
            certInteractor.checkCertificationConstraints(cert);
            Certification saved = certInteractor.save(cert);
            addSuccessMessage("Die Zertifizierung " + STRONG + saved.getName() + STRONG_END + " wurde erfolgreich angelegt!", redirectAttributes);
            return redirectTo(BASE_URL + SLASH + saved.getId());
        }
        catch (InvalidModelException e) {
            prepareCertificationForm(model);
            return formWithErrors(certView, e.getFailures(), model);
        }
    }

    /**
     * Liefert die View zur Neuanlage einer Zertifizierung zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @return den logsichen View-Namen.
     */
    @RequestMapping("/create")
    public String create(final ModelMap model) {
        model.addAttribute("cert", new Certification());
        prepareCertificationForm(model);

        return CERTIFICATION_CREATE;
    }

    /**
     * Liefert die View zur Bearbeitung einer Zertifizierung zurück.
     *
     * @param id
     *            die ID der zu bearbeitenden Zertifizierung.
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable final Long id, final ModelMap model) {
        Certification certToEdit = certInteractor.findOne(id);
        certToEdit = certInteractor.fetchFullData(certToEdit);
        model.addAttribute("cert", certToEdit);
        prepareCertificationForm(model);

        return CERTIFICATION_EDIT;
    }

    /**
     * Liefert die View zur Bearbeitung einer Zertifizierung zurück.
     *
     * @param id
     *            die ID der zu bearbeitenden Zertifizierung.
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping("/{id}")
    public String show(@PathVariable final Long id, final ModelMap model) {
        CertificationRevision revision = certInteractor.findOneWithOldCertification(id);
        Certification certToShow = revision.getActual();
        List<AccomplishedCertification> accomplished = accomplishedCertInteractor
                .fetchFullData(accomplishedCertInteractor.findByCertification(certToShow));
        certToShow = certInteractor.fetchFullData(certToShow);
        model.addAttribute("cert", certToShow);
        model.addAttribute("accomplished", accomplished);

        if (revision.getPreVersion() != null) {
            Certification preVersion = revision.getPreVersion();
            model.addAttribute("compare", preVersion);
            return "certifications/compare";
        }

        return CERTIFICATION_SHOW;
    }

    /**
     * Liefert die View zum Bearbeiten der Zertifizierungspfade zurück.
     *
     * @param id
     *            die Id der Zertifizierung, dessen Pfade bearbeitet werden sollen.
     * @param isPlanning
     *            zeigt an, ob der Benutzer von der Planungsseite für Zertifizierungen kommt.
     * @param model
     *            das View-Model von Spring.
     * @param request
     *            der HttpRequest.
     * @return den logischen View-Namen.
     */
    @RequestMapping("/{id}/pathedit")
    public String editPaths(@PathVariable final Long id,
            @RequestParam(value = "planning", required = false, defaultValue = "false") final boolean isPlanning,
            final ModelMap model, final HttpServletRequest request) {
        Certification certification = certInteractor.findOne(id);
        model.addAttribute("pathListCommand", makePathList(certification));
        model.addAttribute("certification", certification);
        model.addAttribute("certId", certification.getId());

        String redirect = isPlanning ? BASE_URL + SLASH + id + "/plan" : BASE_URL + SLASH + id;
        request.getSession().setAttribute("nextPage", redirect);

        return "certifications/paths/edit";
    }

    /**
     * Speichert die Pfade zu einer Zertifizierung.
     *
     * @param pathListCommand
     *            die neue Liste mit Pfaden zu der {@code Certification}.
     * @param id
     *            die Id der Zertifizierung, dessen Pfade bearbeitet werden sollen.
     * @param result
     *            das Ergebnis der Validierung.
     * @param model
     *            das View-Model von Spring.
     * @param redirectAttributes
     *            die {@code RedirectAttributes}, zu denen View-Attribute bei einem Redirect
     *            hinzugefügt werden können.
     * @param request
     *            der HttpRequest.
     * @return den logischen View-Namen.
     */
    @RequestMapping(value = "/{id}/pathedit", method = RequestMethod.POST)
    public String savePaths(
            @PathVariable final Long id,
            @ModelAttribute("pathListCommand") final PathListCommand pathListCommand, final BindingResult result,
            final ModelMap model, final RedirectAttributes redirectAttributes, final HttpServletRequest request) {
        try {
            Certification certification = certInteractor.findOne(id);
            Certification cert = addPathsToCert(certification, pathListCommand.getPaths());
            Certification response = certInteractor.save(cert);
            addSuccessMessage("Die Pfade zur Zertifizierung '" + STRONG + response.getName()
                    + STRONG_END + "' wurden erfolgreich gespeichert.", redirectAttributes);

            String nextPage = (String) request.getSession().getAttribute("nextPage");
            return redirectTo(nextPage);
        }
        catch (InvalidModelException e) {
            return redirectWithErrors(BASE_URL + SLASH + id + "/pathedit", e.getFailures(), redirectAttributes);
        }
    }

    private Certification addPathsToCert(final Certification cert, final List<Path> pathList) {
        Set<Path> pathSet = new HashSet<>();
        for (int i = 0; i < pathList.size(); i++) {
            Path path = pathList.get(i);
            path.setSequenceNumber(i);
            path.setDestination(cert);
            pathSet.add(path);
        }
        cert.setPaths(pathSet);
        return cert;
    }

    private PathListCommand makePathList(final Certification cert) {
        List<Path> pathList = new ArrayList<>();
        Collection<Path> pathSet = pathInteractor.findPathsToCertification(cert);
        for (Path path : pathSet) {
            pathList.add(path);
        }
        Collections.sort(pathList);
        return new PathListCommand(pathList);
    }

    /**
     * Liefert die View zur Planung einer Zertifizierung zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @param id
     *            die ID der {@code Certification}, die geplant werden soll.
     * @param request
     *            der HttpRequest.
     * @return den logischen View-Namen.
     */
    @RequestMapping("{id}/plan")
    public String plan(final ModelMap model, @PathVariable final Long id, final HttpServletRequest request) {
        model.addAttribute("certId", id);
        PlannedCertification plannedCertification = new PlannedCertification();
        model.addAttribute("plannedCertification", plannedCertification);
        Certification certification = certInteractor.findOne(id);
        initPathMap(model, certification);
        model.addAttribute("certification", certification);

        return PLAN_CERTIFICATION;
    }

    private void initPathMap(final ModelMap model, final Certification certification) {
        Collection<Path> paths = pathInteractor.findPathsToCertification(certification);
        Map<Path, String> pathMap = new HashMap<Path, String>();
        for (Path path : paths) {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append("Pfad ").append(path.getSequenceNumber() + 1)
                                  .append(HTML_LINEBREAK).append("<span>");

            appendCertifications(buffer, path.getNecessaryCertifications());
            appendExams("Notwendige Tests", buffer, path.getNecessaryExams());
            appendExams("Wählbare Tests", buffer, path.getChoosableExams());
            buffer.append("</span>");
            pathMap.put(path, buffer.toString());
        }
        model.addAttribute("paths", pathMap);
    }

    private StringBuffer appendCertifications(final StringBuffer buffer, final Set<Certification> certifications) {
        if (!certifications.isEmpty()) {
            buffer.append("<b>Notwendige Zertifizierungen</b> <br />");
            for (Certification certification : certifications) {
                buffer.append(PARAGRAPH).append(certification.getName()).append(PARAGRAPH_END);
            }
        }
        return buffer;
    }

    private StringBuffer appendExams(final String heading, final StringBuffer buffer, final Set<Exam> exams) {
        if (!exams.isEmpty()) {
            buffer.append("<b>" + heading + "</b> <br />");
            for (Exam exam : exams) {
                buffer.append(PARAGRAPH).append(exam.getTitle()).append(PARAGRAPH_END);
            }
        }
        return buffer;
    }

    /**
     * Fügt eine vom Benutzer geplante Zertifizierung hinzu.
     *
     * @param plannedCertification
     *            die neue {@code PlannedCertification}.
     * @param certId
     *            die Id der Zertifizierung, die geplant werden soll.
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
    @RequestMapping(value = "{certId}/plan", method = RequestMethod.POST)
    public String addPlanned(
            @Valid @ModelAttribute("plannedCertification") final PlannedCertification plannedCertification,
            @PathVariable final Long certId, final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes) {

        Certification certification = certInteractor.findOne(certId);
        if (result.hasErrors()) {
            initPathMap(model, certification);
            return formWithErrors(PLAN_CERTIFICATION, result, model);
        }
        plannedCertification.setPlannerUserName(getActualLoggedInUser().getUserName());
        plannedCertificationInteractor.save(plannedCertification);
        addSuccessMessage("Die Zertifizierung <strong>'" + certification.getName() + "'</strong> wurde für "
                + plannedCertification.getUser().getFirstName() + " " + plannedCertification.getUser().getLastName()
                + " erfolgreich geplant.", redirectAttributes);
        return redirectTo("readiness/");
    }

    /**
     * Sucht nach einer Zertifizierung, die den Suchbegriff im Namen trägt. Gibt maximal 10
     * Ergebnisse zurück.
     *
     * @param model
     *            das View-Model von Spring
     * @param searchQuery
     *            der Suchbegriff.
     * @return eine Liste von Suchergebnissen, die von Spring in JSON umgewandelt wird.
     */
    @RequestMapping(value = "/search")
    @ResponseBody
    public Collection<SearchResult> searchForCertifications(final ModelMap model,
            @RequestParam("q") final String searchQuery) {
        Collection<Certification> foundCerts = certInteractor.findByNameContaining(searchQuery, 0, 5);
        List<SearchResult> searchResults = new ArrayList<>();
        for (Certification certification : foundCerts) {
            SearchResult result = new SearchResult(certification.getName(), certification.getId().toString());
            searchResults.add(result);
        }

        return searchResults;
    }

    private void prepareCertificationForm(final ModelMap model) {
        Collection<Brand> allBrands = brandInteractor.findAll();
        Collection<ProductGroup> productGroups = productGroupInteractor.findAll();

        model.addAttribute("status", CertStatus.class.getEnumConstants());
        model.addAttribute("kinds", CertKind.class.getEnumConstants());
        model.addAttribute("brands", allBrands);
        model.addAttribute("productGroups", productGroups);
        model.addAttribute("cert");
    }

}

package de.ars.skilldb.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.User;
import de.ars.skilldb.exception.InvalidModelException;
import de.ars.skilldb.interactor.UserInteractor;

/**
 * Abstrakte Controller-Klasse zur Definition häufig benötigter Methoden.
 */
public class AbstractController {

    /** Linebreak-Tag in HTML. */
    protected static final String HTML_LINEBREAK = "<br />";

    /** Anzahl der Pager-Buttons. */
    protected static final int PAGING_ITEMS = 6;

    /** Start-Tag für Bold-Schrift. */
    protected static final String STRONG = "<strong>";

    /** End-Tag für Bold-Schrift. */
    protected static final String STRONG_END = "</strong>";

    /** HTML-Tag für einen Absatz. */
    protected static final String PARAGRAPH = "<p>";

    /** HTML-Taq für das Ende eines Absatzes. */
    protected static final String PARAGRAPH_END = "</p>";

    /** Häufig verwendetes Satzende bei erfolgreichem Speichern. */
    protected static final String WURDE_ERFOLGREICH_ANGELEGT = "'</strong> wurde erfolgreich angelegt!";

    @Autowired
    private UserInteractor userInteractor;

    /**
     * Baut die Zeichenkette für einen Redirect zusammen.
     *
     * @param url
     *            die URL als relativer Pfad zum Context-Root, zu der weitergeleitet werden soll.
     * @return den Redirect-String.
     */
    protected String redirectTo(final String url) {
        return String.format("redirect:/%s", url);
    }

    /**
     * Fügt eine Erfolgsnachricht zu den {@code RedirectAttributes} hinzu. Diese werden nach
     * erfolgtem Weiterleiten in der View ausgegeben, wenn diese Erfolgsnachricht nicht leer ist.
     *
     * @param message
     *            die Erfolgsnachricht.
     * @param redirectAttributes
     *            die {@code RedirectAttributes}, zu denen die Erfolgsnachricht hinzugefügt werden
     *            soll.
     */
    protected void addSuccessMessage(final String message, final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", message);
    }

    /**
     * Fügt eine Erfolgsnachricht zur {@code ModelMap} hinzu. Diese wird nach erfolgtem Weiterleiten
     * in der View ausgegeben, wenn diese Erfolgsnachricht nicht leer ist.
     *
     * @param message
     *            die Erfolgsnachricht.
     * @param model
     *            die {@code ModelMap}, zu der die Erfolgsnachricht hinzugefügt werden soll.
     */
    protected void addSuccessMessage(final String message, final ModelMap model) {
        model.addAttribute("successMessage", message);
    }

    /**
     * Fügt eine Fehlernachricht zu den {@code RedirectAttributes} hinzu. Diese werden nach
     * erfolgtem Weiterleiten in der View ausgegeben, wenn diese Fehlernachricht nicht leer ist.
     *
     * @param message
     *            die Fehlernachricht.
     * @param redirectAttributes
     *            die {@code RedirectAttributes}, zu denen die Fehlernachricht hinzugefügt werden
     *            soll.
     */
    protected void addErrorMessage(final String message, final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", message);
    }

    /**
     * Fügt eine Fehlernachricht zur {@code ModelMap} hinzu. Diese wird nach erfolgtem Weiterleiten
     * in der View ausgegeben, wenn diese Fehlernachricht nicht leer ist.
     *
     * @param message
     *            die Fehlernachricht.
     * @param model
     *            die {@code ModelMap}, zu der die Fehlernachricht hinzugefügt werden soll.
     */
    protected void addErrorMessage(final String message, final ModelMap model) {
        model.addAttribute("errorMessage", message);
    }

    /**
     * Führt einen Redirect mit Fehlerinformation durch.
     *
     * @param redirectUrl
     *            die Url als String, zu der weitergeleitet werden soll.
     * @param result
     *            das Ergebnis der Validierung.
     * @param redirectAttributes
     *            die {@code RedirectAttributes}, zu denen die Fehlerinformationen hinzugefügt
     *            werden soll.
     * @return einen Redirect für den Fehlerfall.
     */
    protected String redirectWithErrors(final String redirectUrl, final BindingResult result,
            final RedirectAttributes redirectAttributes) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder message = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            appendSingleFailureMessage(redirectAttributes, message, fieldError.getDefaultMessage(),
                    fieldError.getField());
        }
        addErrorMessage(message.toString(), redirectAttributes);
        return redirectTo(redirectUrl);
    }

    /**
     * Führt einen Redirect mit Fehlerinformation durch.
     *
     * @param redirectUrl
     *            die Url als String, zu der weitergeleitet werden soll.
     * @param failures
     *            eine {@code Map} mit den aufgetretenen Fehlern.
     * @param redirectAttributes
     *            die {@code RedirectAttributes}, zu denen die Fehlerinformationen hinzugefügt
     *            werden soll.
     * @return einen Redirect für den Fehlerfall.
     */
    protected String redirectWithErrors(final String redirectUrl, final Map<String, String> failures,
            final RedirectAttributes redirectAttributes) {
        Set<String> fieldErrors = failures.keySet();
        StringBuilder message = new StringBuilder();
        for (String fieldError : fieldErrors) {
            appendSingleFailureMessage(redirectAttributes, message, failures.get(fieldError), fieldError);
        }
        addErrorMessage(message.toString(), redirectAttributes);
        return redirectTo(redirectUrl);
    }

    private void appendSingleFailureMessage(final RedirectAttributes redirectAttributes, final StringBuilder message,
            final String failureMessage, final String field) {
        message.append(failureMessage).append(HTML_LINEBREAK);
        redirectAttributes.addFlashAttribute(field + "Error", true);
    }

    /**
     * Führt mit Fehlerinformationen zur Form zurück.
     *
     * @param viewName
     *            die Url als String, zu der weitergeleitet werden soll.
     * @param result
     *            das Ergebnis der Validierung.
     * @param model
     *            die {@code ModelMap}, zu denen die Fehlerinformationen hinzugefügt werden soll.
     * @return einen Redirect für den Fehlerfall.
     */
    protected String formWithErrors(final String viewName, final BindingResult result, final ModelMap model) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        String message = "";
        for (FieldError fieldError : fieldErrors) {
            message = message + fieldError.getDefaultMessage() + HTML_LINEBREAK;
            model.addAttribute(fieldError.getField() + "Error", true);
        }
        addErrorMessage(message, model);
        return viewName;
    }

    /**
     * Führt mit Fehlerinformationen zur Form zurück.
     *
     * @param viewName
     *            die Url als String, zu der weitergeleitet werden soll.
     * @param failures
     *            eine {@code Map} mit den aufgetretenen Fehlern.
     * @param model
     *            die {@code ModelMap}, zu denen die Fehlerinformationen hinzugefügt werden soll.
     * @return einen Redirect für den Fehlerfall.
     */
    protected String formWithErrors(final String viewName, final Map<String, String> failures, final ModelMap model) {
        Set<String> fieldErrors = failures.keySet();
        String message = "";
        for (String fieldError : fieldErrors) {
            message = message + failures.get(fieldError) + HTML_LINEBREAK;
            model.addAttribute(fieldError + "Error", true);
        }
        addErrorMessage(message, model);
        return viewName;
    }

    /**
     * Checkt, ob das Binding funktioniert hat und wirft im Fehlerfall eine
     * {@link InvalidModelException}.
     *
     * @param result
     *            das {@link BindingResult}.
     */
    protected void checkBindingResult(final BindingResult result) {
        if (result.hasErrors()) {
            InvalidModelException.Builder builder = new InvalidModelException.Builder().message("Binding failed");
            for (FieldError error : result.getFieldErrors()) {
                builder.withModelError(error.getField(), error.getDefaultMessage());
            }
            builder.throwIt();
        }
    }

    /**
     * Liefert den aktuell angemeldeten Benutzer zurück.
     *
     * @return den den aktuell angemeldeten Benutzer.
     */
    protected User getActualLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return userInteractor.findByUserName(name);
    }

    /**
     * Prüft die Bedinungen eines File-Uploads.
     *
     * @param file
     *            die Datei, die hochgeladen wurde.
     * @throws FileUploadException
     *             wenn die hochgeladene Datei ungültig ist.
     */
    protected void checkFileUploadConstraints(final MultipartFile file) throws FileUploadException {
        if (file.isEmpty()) {
            throw new FileUploadException("Es muss eine Datei angegeben werden!");
        }
        if (!"application/pdf".equals(file.getContentType())) {
            throw new FileUploadException("Die Datei muss vom Typ PDF sein!");
        }
    }

    /**
     * Konfiguriert den Pager für die Seite mit allen benötigten Variablen.
     *
     * @param model
     *            das View-Model von Spring, zu dem die Pager-Konfiguration hinzugefügt wird.
     * @param page
     *            die Nummer der aktuell anzuzeigenden Seite.
     * @param sizePerPage
     *            die Anzahl der Elemente pro Seite.
     * @param total
     *            die Gesamtzahl an Elementen.
     */
    protected void addPagerConfigurationTo(final ModelMap model, final int page, final int sizePerPage, final long total) {
        if (total <= sizePerPage) {
            model.addAttribute("paginationIsNeeded", false);
            return;
        }
        model.addAttribute("paginationIsNeeded", true);

        long pages = calculatePages(sizePerPage, total);
        long threshold;
        if (pages > PAGING_ITEMS) {
            threshold = PAGING_ITEMS / 2;
        }
        else {
            threshold = (pages % 2) == 0 ? pages / 2 : (pages + 1) / 2;
        }

        long startItem = calculateStartItemPosition(page, threshold, pages);

        fillModelWithPagingConfig(model, page, sizePerPage, pages, startItem);
    }

    private long calculatePages(final int sizePerPage, final long total) {
        return (total % sizePerPage) == 0 ? total / sizePerPage : (total / sizePerPage) + 1;
    }

    private long calculateStartItemPosition(final int page, final long threshold, final long pages) {
        long startItem = 0;
        boolean pagerIsNearStart = (page - threshold) <= 0;
        boolean pagerIsNearEnd = (page + threshold) >= pages;

        if (pagerIsNearStart || pages <= PAGING_ITEMS) {
            startItem = 1;
        }
        else if (pagerIsNearEnd) {
            startItem = pages - PAGING_ITEMS;
        }
        else {
            startItem = page - threshold;
        }
        return startItem;
    }

    private void fillModelWithPagingConfig(final ModelMap model, final int page, final int sizePerPage,
            final long pages, final long startItem) {
        model.addAttribute("pagingItems", pages > PAGING_ITEMS ? PAGING_ITEMS : pages - 1);
        model.addAttribute("startItem", startItem);
        model.addAttribute("pagerIsAtStart", page == 1);
        model.addAttribute("pagerIsAtEnd", page == pages);
        model.addAttribute("size", sizePerPage);
        model.addAttribute("page", page);
    }

}

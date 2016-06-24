package de.ars.skilldb.controller.bindings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import de.ars.skilldb.domain.*;

/**
 * Registriert alle Custom-Editoren global.
 */
@ControllerAdvice
public class GlobalBindingInitializer {

    @Autowired
    private CertificationEditor certEditor;

    @Autowired
    private BrandEditor brandEditor;

    @Autowired
    private ProductGroupEditor productGroupEditor;

    @Autowired
    private UserEditor userEditor;

    @Autowired
    private ExamEditor examEditor;

    @Autowired
    private PathEditor pathEditor;

    /**
     * Registriert alle Custom-Editoren global.
     *
     * @param webDataBinder
     *            der {@code WebDataBinder}, zu dem die Editoren hinzugefügt werden.
     * @param request
     *            der {@code WebRequest}.
     */
    @InitBinder
    public void registerCustomEditors(final WebDataBinder webDataBinder, final WebRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        dateFormat.setLenient(false);

        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.registerCustomEditor(Certification.class, certEditor);
        webDataBinder.registerCustomEditor(Brand.class, brandEditor);
        webDataBinder.registerCustomEditor(ProductGroup.class, productGroupEditor);
        webDataBinder.registerCustomEditor(User.class, userEditor);
        webDataBinder.registerCustomEditor(Exam.class, examEditor);
        webDataBinder.registerCustomEditor(Path.class, pathEditor);
    }
}

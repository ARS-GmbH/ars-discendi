package de.ars.skilldb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.ars.skilldb.domain.ProductGroup;
import de.ars.skilldb.domain.enums.CertKind;
import de.ars.skilldb.interactor.AccomplishedCertificationInteractor;
import de.ars.skilldb.interactor.CertificationInteractor;
import de.ars.skilldb.interactor.ProductGroupInteractor;

/**
 * Controller zur Verarbeitung von {@code ProductGroup}s.
 */
@Controller
@RequestMapping("/productgroups")
public class ProductGroupController extends AbstractController {

    private static final String PRODUCTGROUP_EDIT = "productgroup/edit";

    @Autowired
    private ProductGroupInteractor productGroups;

    @Autowired
    private AccomplishedCertificationInteractor accomplishedCerts;

    @Autowired
    private CertificationInteractor certifications;

    /**
     * Liefert die View zur Bearbeitung einer Produktgruppe zurück.
     *
     * @param id
     *            die ID der zu bearbeitenden Produktgruppe.
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable final Long id, final ModelMap model) {
        ProductGroup productGroup = productGroups.findOne(id);
        model.addAttribute("productGroup", productGroup);
        model.addAttribute("accomplishedTechnical", accomplishedCerts.fetchFullData(accomplishedCerts
                .findByProductGroupAndKind(productGroup, CertKind.TECHNICAL)));
        model.addAttribute("accomplishedSales", accomplishedCerts.fetchFullData(accomplishedCerts
                .findByProductGroupAndKind(productGroup, CertKind.SALES)));
        model.addAttribute("certifications", certifications.findByProductGroup(productGroup));
        model.addAttribute("isReady", productGroups.isReady(productGroup));

        return PRODUCTGROUP_EDIT;
    }

    /**
     * Speichert eine vom Benutzer neu angelegte oder bearbeitete Produktgruppe.
     *
     * @param productGroup
     *            die neu angelegte oder bearbeitete {@code ProductGroup}.
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
    public String save(@Valid @ModelAttribute("productGroup") final ProductGroup productGroup,
            final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return formWithErrors(PRODUCTGROUP_EDIT, result, model);
        }

        ProductGroup saved = productGroups.save(productGroup);
        addSuccessMessage("Die Produktgruppe " + STRONG + saved.getName() + STRONG_END
                + " wurde erfolgreich gespeichert!", redirectAttributes);

        return redirectTo("readiness");
    }
}

/**
 *
 */
package de.ars.skilldb.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import de.ars.skilldb.interactor.ProductGroupInteractor;
import de.ars.skilldb.returnobjects.Readiness;

/**
 * Controller zur Verarbeitung von Zertifizierungen.
 */
@Controller
@RequestMapping("/readiness")
public class ReadinessController {

    @Autowired
    private ProductGroupInteractor productGroups;

    /**
     * Liefert das Readiness-Dashboard zurück.
     *
     * @param model
     *            das View-Model von Spring.
     * @return den logischen View-Namen.
     */
    @RequestMapping
    public String getReadinessDashboard(final ModelMap model) {
        Collection<Readiness> allReadinessStatus = productGroups.findAllReadinessStatus();
        model.addAttribute("readinessStatus", allReadinessStatus);

        return "productgroup/readiness";
    }

}

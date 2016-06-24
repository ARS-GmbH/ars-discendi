/**
 *
 */
package de.ars.skilldb.returnobjects;

import java.util.Collection;
import java.util.LinkedList;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.ProductGroup;

/**
 * Repräsentiert den Readiness-Status für eine {@code ProductGroup}.
 */
public class Readiness {

    private ProductGroup productGroup;
    private Collection<Certification> salesCerts;
    private Collection<Certification> technicalCerts;
    private boolean ready;

    /**
     * Konstruiert ein neues {@code Readiness}-Objekt.
     */
    public Readiness() {
        salesCerts = new LinkedList<>();
        technicalCerts = new LinkedList<>();
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(final ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public Collection<Certification> getSalesCerts() {
        return salesCerts;
    }

    public void setSalesCerts(final Collection<Certification> salesCerts) {
        this.salesCerts = salesCerts;
    }

    public Collection<Certification> getTechnicalCerts() {
        return technicalCerts;
    }

    public void setTechnicalCerts(final Collection<Certification> technicalCerts) {
        this.technicalCerts = technicalCerts;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(final boolean ready) {
        this.ready = ready;
    }

}

package de.ars.skilldb.interactor.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.ProductGroup;
import de.ars.skilldb.domain.enums.CertKind;
import de.ars.skilldb.interactor.ProductGroupInteractor;
import de.ars.skilldb.repository.CertificationRepository;
import de.ars.skilldb.repository.ProductGroupRepository;
import de.ars.skilldb.returnobjects.Readiness;
import de.ars.skilldb.util.Ensure;

/**
 * Implementiert den {@code ProductGroupInteractor}.
 *
 */
public class ProductGroupInteractorImpl extends AbstractInteractor<ProductGroup> implements ProductGroupInteractor {

    private final ProductGroupRepository productGroups;

    @Autowired
    private CertificationRepository certifications;

    /**
     * Erzeugt einen neuen {@link ProductGroupInteractorImpl}.
     *
     * @param productGroups
     *            das {@code Repository} für Produktgruppen.
     */
    @Autowired
    public ProductGroupInteractorImpl(final ProductGroupRepository productGroups) {
        super(productGroups);
        this.productGroups = productGroups;
    }

    @Override
    @Transactional
    public ProductGroup fetchFullData(final ProductGroup object) {
        object.setBrands(getTemplate().fetch(object.getBrands()));
        return object;
    }

    @Override
    public Collection<Readiness> findAllReadinessStatus() {
        List<Readiness> readinessStatus = new LinkedList<>();
        Collection<ProductGroup> allProductGroups = findAll();
        for (ProductGroup productGroup : allProductGroups) {
            Readiness status = new Readiness();
            status.setProductGroup(productGroup);
            Collection<Certification> sales = certifications.findByKindAndProductGroup(CertKind.SALES, productGroup);
            Collection<Certification> technicals = certifications.findByKindAndProductGroup(CertKind.TECHNICAL,
                    productGroup);
            status.setSalesCerts(sales);
            status.setTechnicalCerts(technicals);
            status.setReady(evaluateReady(productGroup, sales, technicals));
            readinessStatus.add(status);
        }

        return readinessStatus;
    }

    private boolean evaluateReady(final ProductGroup productGroup, final Collection<Certification> sales,
            final Collection<Certification> technicals) {
        return productGroup.isOpen() || sales.size() >= 1 && technicals.size() >= 2;
    }

    @Override
    public ProductGroup findByName(final String name) {
        Ensure.that(name).isNotEmpty();
        return productGroups.findByName(name);
    }

    @Override
    public boolean isReady(final ProductGroup productGroup) {
        Collection<Certification> sales = certifications.findByKindAndProductGroup(CertKind.SALES, productGroup);
        Collection<Certification> technicals = certifications.findByKindAndProductGroup(CertKind.TECHNICAL,
                productGroup);

        return evaluateReady(productGroup, sales, technicals);
    }

}

package de.ars.skilldb.interactor.impl;

import org.springframework.beans.factory.annotation.Autowired;

import de.ars.skilldb.domain.Brand;
import de.ars.skilldb.interactor.BrandInteractor;
import de.ars.skilldb.repository.BrandRepository;
import de.ars.skilldb.util.Ensure;

/**
 * Implementiert den {@code BrandInteractor}.
 */
public class BrandInteractorImpl extends AbstractInteractor<Brand> implements BrandInteractor {

    private final BrandRepository brands;

    /**
     * Konstruiert einen neuen {@link BrandInteractorImpl}.
     *
     * @param brands
     *            das {@code Repository} für Brands.
     */
    @Autowired
    public BrandInteractorImpl(final BrandRepository brands) {
        super(brands);
        this.brands = brands;
    }

    @Override
    public Brand findByName(final String name) {
        Ensure.that(name).isNotBlank();

        return brands.findByName(name);
    }

}
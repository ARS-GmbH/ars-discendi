package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.Brand;
import de.ars.skilldb.interactor.BrandInteractor;

/**
 * Editor zur Konvertierung von Strings in Domänenobjekte für die Klasse {@code Brand} .
 */
public class BrandEditor extends PropertyEditorSupport {

    private final BrandInteractor brands;

    /**
     * Erzeugt einen neuen {@code BrandEditor}.
     *
     * @param brands
     *            der {@code BrandInteractor}, der zur Konvertierung genutzt wird.
     */
    public BrandEditor(final BrandInteractor brands) {
        super();
        this.brands = brands;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        Brand brand = brands.findByName(text);
        setValue(brand);
    }
}

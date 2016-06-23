package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.ProductGroup;
import de.ars.skilldb.interactor.ProductGroupInteractor;

/**
 * Editor zur Konvertierung von Strings in Domänenobjekte für die Klasse {@code ProductGroup} .
 */
public class ProductGroupEditor extends PropertyEditorSupport {

    private final ProductGroupInteractor productGroups;

    /**
     * Erzeugt einen neuen {@code ProductGroupEditor}.
     *
     * @param productGroups
     *            der {@code ProductGroupInteractor}, der zur Konvertierung genutzt wird.
     */
    public ProductGroupEditor(final ProductGroupInteractor productGroups) {
        super();
        this.productGroups = productGroups;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        ProductGroup productGroup = productGroups.findByName(text);
        setValue(productGroup);
    }
}

package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.Customer;
import de.ars.skilldb.interactor.CustomerInteractor;

/**
 * Klasse zur Konvertierung von Strings in Kunden.
 */
public class CustomerEditor extends PropertyEditorSupport {

    private final CustomerInteractor customerInteractor;

    /**
     * Erzeugt einen neuen Kunden-Editor.
     *
     * @param customerInteractor Interactor, der zur Konvertierung benötigt wird.
     */
    public CustomerEditor(final CustomerInteractor customerInteractor) {
        super();
        this.customerInteractor = customerInteractor;
    }

    @Override
    public void setAsText(final String text) {
        Long id = Long.parseLong(text);
        setValue(customerInteractor.findOne(id));
    }

    @Override
    public String getAsText() {
        return getValue() == null ? "" : ((Customer) getValue()).getName();
    }
}

package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.interactor.CertificationInteractor;

/**
 * Editor zur Konvertierung von Strings in Domänenobjekte für die Klasse {@code Certification} .
 */
public class CertificationEditor extends PropertyEditorSupport {

    private final CertificationInteractor certifications;

    /**
     * Erzeugt einen neuen {@code CertificationEditor}.
     *
     * @param certifications
     *            der {@code CertificationInteractor}, der zur Konvertierung genutzt wird.
     */
    public CertificationEditor(final CertificationInteractor certifications) {
        super();
        this.certifications = certifications;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        try {
            Certification cert = certifications.findByName(text);
            setValue(cert);
        }
        catch (IllegalArgumentException e) {
            setValue(null);
        }
    }

    @Override
    public String getAsText() {
        return getValue() == null ? "" : ((Certification) getValue()).getName();
    }
}
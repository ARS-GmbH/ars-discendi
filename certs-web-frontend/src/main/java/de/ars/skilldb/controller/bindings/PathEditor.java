package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.Path;
import de.ars.skilldb.interactor.PathInteractor;

/**
 * Editor zur Konvertierung von Strings in Domänenobjekte für die Klasse {@code Path} .
 */
public class PathEditor extends PropertyEditorSupport {

    private final PathInteractor paths;

    /**
     * Erzeugt einen neuen {@code PatEditor}.
     *
     * @param paths
     *            der {@code PathInteractor}, der zur Konvertierung genutzt wird.
     */
    public PathEditor(final PathInteractor paths) {
        super();
        this.paths = paths;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        Path path = paths.findOne(Long.parseLong(text));
        setValue(path);
    }
}

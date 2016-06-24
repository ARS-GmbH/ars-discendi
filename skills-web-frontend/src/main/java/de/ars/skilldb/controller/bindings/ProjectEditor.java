package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.Project;
import de.ars.skilldb.interactor.ProjectInteractor;

/**
 * Klasse zur Konvertierung von Strings in Projekte.
 */
public class ProjectEditor extends PropertyEditorSupport {

    private final ProjectInteractor projectInteractor;

    /**
     * Erzeugt einen neuen Projekt-Editor.
     *
     * @param projectInteractor Interactor, der zur Konvertierung benötigt wird.
     */
    public ProjectEditor(final ProjectInteractor projectInteractor) {
        super();
        this.projectInteractor = projectInteractor;
    }

    @Override
    public void setAsText(final String text) {
        Long id = Long.parseLong(text);
        setValue(projectInteractor.findOne(id));
    }

    @Override
    public String getAsText() {
        return getValue() == null ? "" : ((Project) getValue()).getName();
    }
}

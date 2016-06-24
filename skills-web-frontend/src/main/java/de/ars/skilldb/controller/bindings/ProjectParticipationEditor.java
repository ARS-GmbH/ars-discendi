package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.ProjectParticipation;
import de.ars.skilldb.interactor.ProjectParticipationInteractor;

/**
 * Klasse zur Konvertierung von Strings in Projektarbeiten.
 */
public class ProjectParticipationEditor extends PropertyEditorSupport {

    private final ProjectParticipationInteractor projectParticipationInteractor;

    /**
     * Erzeugt einen neuen Projektarbeit-Editor.
     *
     * @param projectParticipationInteractor Interactor, der zur Konvertierung benötigt wird.
     */
    public ProjectParticipationEditor(final ProjectParticipationInteractor projectParticipationInteractor) {
        super();
        this.projectParticipationInteractor = projectParticipationInteractor;
    }

    @Override
    public void setAsText(final String text) {
        Long id = Long.parseLong(text);
        setValue(projectParticipationInteractor.findOne(id));
    }

    @Override
    public String getAsText() {
        return getValue() == null ? "" : ((ProjectParticipation) getValue()).getProject().getName();
    }
}

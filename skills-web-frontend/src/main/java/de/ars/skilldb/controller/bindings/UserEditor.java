package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.UserInteractor;

/**
 * Klasse zur Konvertierung von Strings in User.
 */
public class UserEditor extends PropertyEditorSupport {

    private final UserInteractor userInteractor;

    /**
     * Erzeugt einen neuen User-Editor.
     *
     * @param userInteractor Interactor, der zur Konvertierung benötigt wird.
     */
    public UserEditor(final UserInteractor userInteractor) {
        super();
        this.userInteractor = userInteractor;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        Long id = Long.parseLong(text);
        setValue(userInteractor.findOne(id));
    }

    @Override
    public String getAsText() {
        return getValue() == null ? "" : ((User) getValue()).getFullName();
    }
}

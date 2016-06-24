package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;
import java.util.Collection;

import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.UserInteractor;

/**
 * Editor zur Konvertierung von Strings in Domänenobjekte für die Klasse {@code Brand} .
 */
public class UserEditor extends PropertyEditorSupport {

    private final UserInteractor users;

    /**
     * Erzeugt einen neuen {@code UserEditor}.
     *
     * @param users
     *            der {@code UserInteractor}, der zur Konvertierung genutzt wird.
     */
    public UserEditor(final UserInteractor users) {
        super();
        this.users = users;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        Collection<User> userList = users.findByNameContaining(text, 0, 10);
        if (userList.size() > 1) {
            throw new IllegalArgumentException("More than one user found. Cannot resolve.");
        }
        setValue(userList.iterator().next());
    }
}

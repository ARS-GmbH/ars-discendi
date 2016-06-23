package de.ars.skilldb.interactor;

import java.util.Collection;

import de.ars.skilldb.domain.User;

/**
 * Interactor für die {@code User}s, der die notwendige Business-Logik zur Verfügung stellt.
 */
public interface UserInteractor extends Interactor<User> {

    /**
     * Findet einen {@code User} anhand des vollen Namens.
     *
     * @param username
     *            der volle Name des {@code User}s.
     * @return den gesuchten {@code User}.
     */
    User findByUserName(String username);

    /**
     * Findet {@code User} anhand ihres Vor- oder Nachnamens.
     *
     * @param searchQuery
     *            der Suchbegriff, mit dem nach Benutzern gesucht werden soll.
     * @param page
     *            die Seite, ab der die Treffer zurückgegeben werden sollen.
     * @param count
     *            die Anzahl der maximalen Treffer, die zurückgegeben werden sollen.
     * @return die gesuchten {@code User}.
     */
    Collection<User> findByNameContaining(String searchQuery, int page, int count);
}

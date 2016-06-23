package de.ars.skilldb.interactor;

import java.util.Collection;

import de.ars.skilldb.domain.User;


/**
 * Interactor-Interface für User.
 *
 */
public interface UserInteractor extends Interactor<User> {

    /**
     * Sucht einen Benutzer anhand des Benutzernamens.
     * @param userName Name des gesuchten Benutzers
     * @return Benutzer-Objekt. Null, wenn kein Benutzer gefunden wurde.
     */
    User findByUserName(String userName);

    /**
     * Sucht alle Benutzer, die dem übergebenen Benutzer unterstellt sind und eine Skill-Freigabe beantragt haben.
     * @param user Benutzer, der die Freigabe erteilt
     * @return Benutzer mit freizugebenden Skills, die dem übergebenen Benutzer unterstellt sind
     */
    Collection<User> findUsersWithSubmittedSkills(User user);

}

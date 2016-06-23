/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.UserInteractor;
import de.ars.skilldb.repository.UserRepository;
import de.ars.skilldb.util.Ensure;

/**
 *  Implementierung eines Interactors für User.
 */
public class UserInteractorImpl extends AbstractInteractor<User> implements UserInteractor {

    private final UserRepository userRepository;

    /**
     * Erzeugt eine Instanz der Klasse UserRepositoryImpl.
     * @param userRepository Repository für den Datenbank
     */
    @Autowired
    protected UserInteractorImpl(final UserRepository userRepository) {
        super(User.class, userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public User findByUserName(final String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Collection<User> findUsersWithSubmittedSkills(final User user) {
        Ensure.that(user).isNotNull();
        return userRepository.findUsersWithSubmittedSkills(user);
    }
}

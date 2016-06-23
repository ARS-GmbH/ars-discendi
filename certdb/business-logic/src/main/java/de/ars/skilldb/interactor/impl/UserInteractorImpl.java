package de.ars.skilldb.interactor.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.UserInteractor;
import de.ars.skilldb.repository.UserRepository;
import de.ars.skilldb.util.Ensure;

/**
 * Implementiert den {@code UserInteractor}.
 *
 */
public class UserInteractorImpl extends AbstractInteractor<User> implements UserInteractor {

    private final UserRepository userRepository;

    /**
     * Konstruiert einen neuen {@link UserInteractorImpl}.
     *
     * @param userRepository
     *            das {@link Repository} für Benutzer.
     */
    @Autowired
    public UserInteractorImpl(final UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User fetchFullData(final User object) {
        return object;
    }

    @Override
    public Collection<User> findByNameContaining(final String searchQuery, final int page, final int count) {
        Ensure.that(searchQuery).isNotEmpty();
        Pageable pageable = new PageRequest(page, count);
        return userRepository.findByNameContaining(searchQuery, pageable);
    }

    @Override
    public User findByUserName(final String username) {
        Ensure.that(username).isNotEmpty();
        return userRepository.findByUserName(username);
    }
}

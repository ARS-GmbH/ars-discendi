/**
 *
 */
package de.ars.skilldb.interactor.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.UserInteractor;
import de.ars.skilldb.repository.UserRepository;

/**
 * Testklasse für die Klasse UserInteractorImpl.
 */
public class UserInteractorImplTest {

    private UserInteractor interactor;
    private UserRepository userRepository;

    /**
     * Initiales erzeugen von Objekten, die in den meisten Testfällen benötigt werden.
     */
    @Before
    public void initObjects() {
        userRepository = Mockito.mock(UserRepository.class);
        interactor = new UserInteractorImpl(userRepository);
        ReflectionTestUtils.setField(interactor, "userRepository", userRepository);
    }

    /**
     * Testet das Finden eines Benutzers anhand des Benutzernamens.
     */
    @Test
    public void testFindByUserName() {
        User user = generateUser();
        Mockito.when(userRepository.findByUserName(Matchers.anyString())).thenReturn(user);

        User actual = interactor.findByUserName("user.name");

        assertEquals("User is not equal.", user, actual);
    }

    /**
     * Testet das Finden eines Benutzers anhand des Benutzernamens.
     */
    @Test
    public void testFindUserByNameNoUser() {
        Mockito.when(userRepository.findByUserName(Matchers.anyString())).thenReturn(null);

        User actual = interactor.findByUserName("someUserName");

        assertNull("User is not null.", actual);
    }

    private User generateUser() {
        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setUserName("firstName.lastName");
        return user;
    }

}

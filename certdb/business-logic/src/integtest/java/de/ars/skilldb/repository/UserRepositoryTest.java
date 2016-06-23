package de.ars.skilldb.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.User;
import de.ars.skilldb.testfixtures.AbstractIntegrationTest;

/**
 * Integrationstests für das {@code UserRepository}.
 *
 */
public class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Testet, ob ein {@code User} anhand seines vollen Namens gefunden werden kann.
     */
    @Test
    @Transactional
    public void testFindByFullName() {
        User user = new User();
        user.setUserName("john.doe");
        User expected = userRepository.save(user);

        User actual = userRepository.findByUserName("john.doe");
        assertEquals("The expected user is not equal.", expected, actual);
    }

}

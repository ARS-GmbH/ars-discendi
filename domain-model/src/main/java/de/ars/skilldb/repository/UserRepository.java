package de.ars.skilldb.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.User;

/**
 * Spring-Data Repository für die Klasse {@code User}.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    /**
     * Findet einen {@code User} anhand seines vollqualifizierenden Namens.
     *
     * @param userName
     *            der vollqualifiziernde Name des Benutzers.
     * @return der gesuchte {@code User}.
     */
    User findByUserName(String userName);

    /**
     * Findet {@code User} anhand ihres Vor- oder Nachnamens.
     *
     * @param searchQuery
     *            der Suchbegriff, mit dem nach Benutzern gesucht werden soll.
     * @return die {@code User}, auf die der Suchbegriff passt.
     */
    @Query("Match (user:User) Where (user.firstName + ' ' + user.lastName) =~ " +
    		"('(?i).*' + {0} + '.*') return user")
    List<User> findByNameContaining(String searchQuery, Pageable pageable);
}

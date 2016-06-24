/**
 *
 */
package de.ars.skilldb.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.Customer;

/**
 * Repository für Kunden.
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    /**
     * Liefert den Kunden mit dem übergebenen Namen zurück.
     * Groß- und Kleinschreibung wird dabei nicht beachtet.
     * @param name Name des Kunden
     * @return Kunde
     */
    @Query("MATCH (c:Customer) WHERE c.name =~ ('(?i)' + {0}) RETURN c")
    Customer findByNameCaseInsensitive(String name);

    /**
     * Sucht einen Kunden anhand des Namens bei dem die ID nicht der
     * übergebenen ID entspricht.
     * @param id ID, die den Kunden identifiziert, der bei der Suche nicht berücksichtigt werden soll
     * @param name Name nach dem gesucht werden soll
     * @return Kunde
     */
    @Query("MATCH (c:Customer) WHERE c.name =~ ('(?i)' + {1}) AND NOT id(c)={0} RETURN c")
    Customer findByNameCaseInsensitiveWithoutId(Long id, String name);
}

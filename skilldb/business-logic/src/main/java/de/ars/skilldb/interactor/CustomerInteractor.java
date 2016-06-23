/**
 *
 */
package de.ars.skilldb.interactor;

import de.ars.skilldb.domain.Customer;
import de.ars.skilldb.util.AssertionFailedException;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Interactor für Kunden.
 */
public interface CustomerInteractor extends Interactor<Customer> {

    /**
     * Legt einen neuen Kunden an.
     *
     * @param customer
     *            Anzulegender Kunde
     * @throws AssertionFailedException
     *             wenn der Kunde <tt> null </tt> ist
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>der Name nur whitespace enthält</li>
     *             <li>der Name bereits existiert</li>
     *             </ul>
     * @return der angelegte Kunde
     */
    Customer add(Customer customer);

    /**
     * Ändert die Attribute des in der Datenbank gespeicherten Kunden.
     *
     * @param customer
     *            Kunde, dessen Werte in die Datenbank gespeichert werden
     *            sollen
     * @throws AssertionFailedException
     *             wenn der Kunde <tt> null </tt> ist
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>der Name nur whitespace enthält</li>
     *             <li>der Name bereits existiert</li>
     *             </ul>
     * @return der geänderte Kunde
     */
    Customer update(Customer customer);
}

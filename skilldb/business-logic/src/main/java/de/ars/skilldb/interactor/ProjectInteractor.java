/**
 *
 */
package de.ars.skilldb.interactor;

import java.util.Collection;

import org.springframework.data.domain.Sort;

import de.ars.skilldb.domain.Project;
import de.ars.skilldb.util.AssertionFailedException;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Interactor für Projekte.
 */
public interface ProjectInteractor extends Interactor<Project> {

    /**
     * Legt ein neues Projekt in der Datenbank an.
     *
     * @param project
     *            Anzulegendes Projekt
     * @throws AssertionFailedException
     *             wenn das Projekt <tt> null </tt> ist
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>der Name nur whitespace enthält</li>
     *             <li>der Name des bereits existiert</li>
     *             <li>der Kunde <tt>null</tt> ist</li>
     *             <li>das Startdatum <tt>null</tt> ist</li>
     *             <li>das Enddatum <tt>null</tt> ist</li>
     *             <li>das Startdatum nach dem Enddatum liegt</li>
     *             </ul>
     * @return das angelegte Projekt
     */
    Project add(Project project);

    /**
     * Ändert das in der Datenbank gespeicherte Projekt.
     *
     * @param project
     *            Projekt, dessen Werte in die Datenbank gespeichert werden
     *            sollen
     * @throws AssertionFailedException
     *             wenn das Projekt <tt> null </tt> ist
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>der Name nur whitespace enthält</li>
     *             <li>der Name des bereits existiert</li>
     *             <li>der Kunde <tt>null</tt> ist</li>
     *             <li>das Startdatum <tt>null</tt> ist</li>
     *             <li>das Enddatum <tt>null</tt> ist</li>
     *             </ul>
     * @return das geänderte Projekt
     */
    Project update(Project project);

    /**
     * Liefert alle Projekte eines Kunden zurück.
     * @param customerId ID des Kunden, dessen Projekt gefunden werden sollen
     * @param sort Sortierung der Projekte
     * @return Alle Projekte des Kunden
     */
    Collection<Project> findByCustomer(Long customerId, Sort sort);

}

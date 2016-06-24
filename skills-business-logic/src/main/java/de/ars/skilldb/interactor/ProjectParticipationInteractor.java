/**
 *
 */
package de.ars.skilldb.interactor;

import java.util.Collection;
import java.util.Set;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.ProjectParticipation;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.util.AssertionFailedException;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Interactor für Projekte.
 */
public interface ProjectParticipationInteractor extends Interactor<ProjectParticipation> {

    /**
     * Legt eine neue Projektarbeit an.
     *
     * @param projectParticipation
     *            Anzulegende Projektarbeit
     * @throws AssertionFailedException
     *             wenn
     *             <ul>
     *             <li>die Projektarbeit <tt> null </tt> ist</li>
     *             <li>der Mitarbeiter <tt>null</tt> ist</li>
     *             </ul>
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>das Projekt <tt>null</tt> ist</li>
     *             <li>bereits eine Projektarbeit zu diesem Projekt und diesem Mitarbeiter existiert</li>
     *             <li>die Dauer kleiner oder gleich 0 ist</li>
     *             </ul>
     * @return das angelegte Projekt
     */
    ProjectParticipation add(ProjectParticipation projectParticipation);

    /**
     * Ändert die Attribute der in der Datenbank gespeicherten Projektarbeit.
     *
     * @param projectParticipation
     *            Projektarbeit, dessen Werte in die Datenbank gespeichert werden
     *            sollen
     * @throws AssertionFailedException
     *             wenn
     *             <ul>
     *             <li>die Projektarbeit <tt> null </tt> ist</li>
     *             <li>der Mitarbeiter <tt>null</tt> ist</li>
     *             </ul>
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>das Projekt <tt>null</tt> ist</li>
     *             <li>bereits eine Projektarbeit zu diesem Projekt und diesem Mitarbeiter existiert</li>
     *             <li>die Dauer kleiner oder gleich 0 ist</li>
     *             </ul>
     * @return das geänderte Projekt
     */
    ProjectParticipation update(ProjectParticipation projectParticipation);

    /**
     * Liefert alle Projektarbeiten eines Mitarbeiters zurück.
     *
     * @param user
     *            Mitarbeiter, dessen Projektarbeiten gesucht werden sollen
     * @throws AssertionFailedException
     *             wenn user <tt>null</tt> ist
     * @return alle Projektarbeiten des Mitarbeiters
     */
    Collection<ProjectParticipation> findByUser(User user);

    /**
     * Liefert die Wissensgebiete der Skills zurück, die in einer Projektarbeit
     * angegeben wurden.
     *
     * @param projectParticipation
     *            Projektarbeit, deren Wissensgebiete zurückgegeben werden
     *            sollen.
     * @throws AssertionFailedException
     *             wenn die Projektarbeit <tt>null</tt> ist
     * @return alle vorkommenden Wissensgebiete
     */
    Set<Knowledge> findAllKnowledges(ProjectParticipation projectParticipation);

}

/**
 *
 */
package de.ars.skilldb.interactor;

import java.util.Collection;

import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.util.AssertionFailedException;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Interactor für Skills.
 */
public interface SkillInteractor extends Interactor<Skill> {

    /**
     * Liefert alle freigegebenen Skills alphabetisch aufsteigend nach dem Namen
     * des Wissensgebiets sortiert zurück.
     *
     * @param user
     *            Benutzer dessen Skills ausgegeben werden sollen
     * @return eine Collection aller Skills des Benutzers
     * @throws AssertionFailedException
     *             wenn der Benutzer <tt> null </tt> ist
     *
     */
    Collection<Skill> findUserSkills(final User user);

    /**
     * Sucht Skills zum angegebenen Wissensgebiet, die mindestens das angegebene Skill-Level besitzen.
     * @param knowledge Wissensgebiet des gesuchten Skills
     * @param skillLevel Mindestlevel des gesuchten Skills
     * @return eine Collection von Skills
     * @throws AssertionFailedException wenn das Wissensgebiet nur aus Whitespace-Zeichen besteht
     */
    Collection<Skill> findByKnowledgeNameAndSkillLevel(String knowledge, int skillLevel);

    /**
     * Fügt der Datenbank einen neuen Skill hinzu.
     *
     * @param skill
     *            Hinzuzufügender Skill
     * @return der gespeicherte Skill
     * @throws AssertionFailedException
     *             wenn der User oder der Skill <tt> null </tt> ist
     * @throws IllegalUserArgumentsException
     *             wenn das Wissensgebiet oder das Skill-Level <tt> null </tt> ist, oder
     *             ein Skill zu diesem Wissensgebiet bei diesem Benutzer bereits
     *             existiert
     */
    Skill add(final Skill skill);

    /**
     * Ändert das Skill-Level in der Datenbank und aktualisiert die
     * Vorgänger-Skill-Level. Das Wissensgebiet und der Benutzer dürfen nicht
     * geändert werden.
     *
     * @param skill
     *            der zu speichernde Skill
     * @return der geänderte Skill
     * @throws AssertionFailedException
     *             wenn der User oder der Skill <tt> null </tt> ist
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>das Wissensgebiet oder das Skill-Level <tt> null </tt> ist</li>
     *             <li>das Wissensgebiet geändert wurde</li>
     *             </ul>
     * @throws IllegalArgumentException wenn der Benutzer geändert wurde
     */
    Skill update(Skill skill);

    /**
     * Sucht alle Skills des übergebenen Mitarbeiters mit dem Status "SUBMITTED".
     * @param user Benutzer, dessen Skills durchsucht werden
     * @return Skills, die noch freizugeben sind.
     */
    Collection<Skill> findSubmittedSkills(User user);

    /**
     * Speichert einen freigegebenen Skill in die Datenbank.
     * @param skill Skill, der freigegeben wird.
     */
    void approve(Skill skill);
}

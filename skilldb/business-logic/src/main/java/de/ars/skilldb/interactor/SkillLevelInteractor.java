/**
 *
 */
package de.ars.skilldb.interactor;

import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.util.AssertionFailedException;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Interactor für Skill-Level.
 */
public interface SkillLevelInteractor extends Interactor<SkillLevel> {

    /**
     * Liefert ein Skill-Level anhand des internen Werts zurück.
     *
     * @param internalValue
     *            interner Wert des gesuchten Skill-Levels
     * @throws IllegalUserArgumentsException
     *             wenn der Wert nach dem gesucht werden soll nicht zwischen 0
     *             und 100 liegt.
     * @return das Skill-Level mit dem entsprechenden internen Wert. Null, wenn
     *         die Suche keinen Treffer ergab
     */
    SkillLevel findByInternalValue(final int internalValue);

    /**
     * Legt ein neues Skill-Level an.
     *
     * @param skillLevel
     *            Anzulegendes Skill-Level
     * @throws AssertionFailedException
     *             wenn das Skill-Level <tt> null </tt> ist
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>der interne Wert außerhalb des zulässigen bereichs liegt</li>
     *             <li>der Name nur whitespace enthält</li>
     *             <li>der interne Wert oder der Name bereits existiert</li>
     *             </ul>
     * @return das gespeicherte Skill-Level
     */
    SkillLevel add(SkillLevel skillLevel);

    /**
     * Ändert die Attribute des in der Datenbank gespeicherten Skill-Levels.
     *
     * @param skillLevel
     *            Skill-Level dessen Werte in die Datenbank gespeichert werden
     *            sollen
     * @throws AssertionFailedException
     *             wenn das Skill-Level <tt> null </tt> ist
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>der interne Wert außerhalb des zulässigen bereichs liegt</li>
     *             <li>der Name nur whitespace enthält</li>
     *             <li>der interne Wert oder der Name bereits existiert</li>
     *             </ul>
     * @return das geänderte Skill-Level
     */
    SkillLevel update(SkillLevel skillLevel);
}

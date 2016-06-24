package de.ars.skilldb.interactor;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.util.AssertionFailedException;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Interactor-Interface für Wissensgebiete.
 *
 */
public interface KnowledgeInteractor extends Interactor<Knowledge> {

    /**
     * Fügt der Datenbank ein neues Wissensgebiet hinzu.
     *
     * @param knowledge
     *            Hinzuzufügendes Wissensgebiet
     * @throws AssertionFailedException
     *             wenn das übergebene Wissensgebiet <tt> null </tt> ist
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>der Name des Wissensgebiets nur aus Whitespace besteht</li>
     *             <li>das Wissensgebiet keiner Kategorie zugeordnet wurde</li>
     *             <li>bereits ein Wissensgebiet mit dem gleichen Namen
     *             existiert</li>
     *             </ul>
     * @return das angelegte Wissensgebiet.
     */
    Knowledge add(Knowledge knowledge);

    /**
     * Ändert die Attribute des in der Datenbank gespeicherten Wissensgebietes.
     *
     * @param knowledge
     *            Wissensgebiet mit den neuen Attributen
     * @throws AssertionFailedException
     *             wenn das übergebene Wissensgebiet oder die ID des übergebenen Wissensgebiets <tt> null </tt> ist
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>der Name des Wissensgebiets nur aus Whitespace besteht</li>
     *             <li>das Wissensgebiet keiner Kategorie zugeordnet wurde</li>
     *             <li>bereits ein Wissensgebiet mit dem gleichen Namen
     *             existiert</li>
     *             </ul>
     * @return das geänderte Wissensgebiet
     */
    Knowledge update(Knowledge knowledge);

}

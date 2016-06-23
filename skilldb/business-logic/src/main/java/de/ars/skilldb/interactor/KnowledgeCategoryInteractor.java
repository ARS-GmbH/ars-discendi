package de.ars.skilldb.interactor;

import de.ars.skilldb.domain.KnowledgeCategory;
import de.ars.skilldb.util.AssertionFailedException;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Interactor-Interface für Kategorien der Wissensgebiete.
 *
 */
public interface KnowledgeCategoryInteractor extends Interactor<KnowledgeCategory> {

    /**
     * Fügt der Datenbank ein neues Wissensgebiet hinzu.
     *
     * @param knowledgeCategory
     *            Hinzuzufügende Kategorie von Wissensgebieten
     * @throws AssertionFailedException
     *             wenn
     *             <ul>
     *             <li>die übergebene Kategorie <tt> null </tt> ist</li>
     *             <li>die Eltern-Collection der Kategorie <tt> null </tt> ist</li>
     *             <li>die Kind-Collection der Kategorie <tt> null </tt> ist</li>
     *             </ul>
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>der Name der Kategory nur whitespace enthält</li>
     *             <li>bereits eine Wissensgebiets-Kategorie mit dem Namen
     *             existiert</li>
     *             <li>eine Kategorie sowohl in der Eltern- als auch in der
     *             Kind-Collection enthalten ist</li>
     *             </ul>
     * @return KnowledgeCategory, die angelegte Wissensgebiets-Kategorie.
     */
    KnowledgeCategory add(KnowledgeCategory knowledgeCategory);

    /**
     * Ändert die Attribute der in der Datenbank gespeicherten
     * Wissensgebiets-Kategorie.
     *
     * @param knowledgeCategory
     *            Kategorie mit den neuen Attributen
     * @throws AssertionFailedException
     *             wenn
     *             <ul>
     *             <li>die übergebene Kategorie <tt> null </tt> ist</li>
     *             <li>die ID der übergebenen Kategorie <tt> null </tt> ist </li>
     *             <li>die Eltern-Collection der Kategorie <tt> null </tt> ist</li>
     *             <li>die Kind-Collection der Kategorie <tt> null </tt> ist</li>
     *             </ul>
     * @throws IllegalUserArgumentsException
     *             wenn
     *             <ul>
     *             <li>der Name der Kategory nur whitespace enthält</li>
     *             <li>bereits eine Wissensgebiets-Kategorie mit dem Namen
     *             existiert</li>
     *             <li>eine Kategorie sowohl in der Eltern- als auch in der
     *             Kind-Collection enthalten ist</li>
     *             </ul>
     * @return die geänderte Wissensgebiets-Kategorie
     */
    KnowledgeCategory update(KnowledgeCategory knowledgeCategory);

    /**
     * Liefert den Wurzelknoten der Kategorien zurück.
     * @return Wurzelknoten der Kategorien
     */
    KnowledgeCategory findRoot();

}

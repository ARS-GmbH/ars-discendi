/**
 *
 */
package de.ars.skilldb.interactor;

import java.util.Collection;

/**
 * Basis-Interface für alle {@code Interactor}-Interfaces.
 *
 * @param <T>
 *            der Typ, der von diesem {@code Interactor} verwaltet wird.
 */
public interface Interactor<T> {

    /**
     * Liefert alle Objekte des entsprechenden Typs zurück. Die Objekte sind dabei nicht sortiert.
     *
     * @return alle Objekte.
     */
    Collection<T> findAll();

    /**
     * Liefert alle Objekte des entsprechenden Typs zurück, die auf der spezifizierten Seite bei
     * angegebener Anzahl an Elementen pro Seite liegen. Ermöglicht Pagination. Die Sortierung der
     * Elemente wird durch den Typ bestimmt.
     *
     * @param page
     *            der Index der Seite, von der die Elemente zurückgegeben werden sollen. Beginnt bei
     *            {@code 0}.
     * @param size
     *            Anzahl der Elemente pro Seite als ganze Zahl.
     * @return die Elemente der Seite.
     */
    Collection<T> findAll(int page, int size);

    /**
     * Liefert ein einzelnes Objekt anhand der ID zurück.
     *
     * @param id
     *            die ID des zu suchenden Objekts.
     * @return das gesuchte Objekt.
     */
    T findOne(Long id);

    /**
     * <p>
     * Komplettiert ein Objekt, das nur leere Objekte als Attribute besitzt (außer der ID). Die
     * fehlenden Informationen werden aus der Datenbank nachgeladen und dem angefragten Objekt
     * zugefügt.
     * </p>
     *
     * @param object
     *            das zu komplettierende Objekt.
     * @return das Objekt mit vollständigen Informationen.
     */
    T fetchFullData(T object);

    /**
     * Komplettiert mehrere Objekte. Iteriert über die übergebene {@code Collection} und wendet auf
     * jedes Objekt die Methode {@link #fetchFullData(Object)} an.
     *
     * @param objects
     *            die zu komplettierenden Objekte.
     * @param <E>
     *            der Typ der {@code Collection}, das die Objekte enthält.
     * @return die {@code Collection}, die übergeben wurde, wobei die Elemente innerhalb
     *         vervollständigt sind.
     */
    <E extends Collection<T>> E fetchFullData(E objects);

    /**
     * Speichert ein Objekt ab. Existiert bereits ein Objekt mit der gleichen ID, so wird das
     * bestehende Objekt überschrieben.
     *
     * @param object
     *            das zu speichernde Objekt.
     * @return das neu gespeicherte Objekt. Enthält auch die ID.
     */
    T save(T object);

    /**
     * Löscht alle Entitäten, für die dieser {@code Interactor} zuständig ist.
     */
    void deleteAll();

    /**
     * Liefert die Anzahl der Entitäten zurück, für die dieser {@code Interactor} zuständig ist.
     *
     * @return die Anzahl der Entitäten.
     */
    long count();

}

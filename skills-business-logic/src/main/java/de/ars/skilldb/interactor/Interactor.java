/**
 *
 */
package de.ars.skilldb.interactor;

import java.util.Collection;

import org.springframework.data.domain.Sort;

/**
 * Interactor-Interface für alle {@code Interactor}-Interfaces.
 *
 * @param <T>
 *            Daten-Typ, der von diesem {@code Interactor} verwaltet wird
 */
public interface Interactor<T> {

    /**
     * Befüllt das übergebene Objekt.
     *
     * @param object
     *            Objekt, das befüllt werden soll
     * @return das befüllte Objekt
     */
    T fetchFullData(T object);

    /**
     * Erhält eine Collection an Objekten, befüllt die Objekte und liefert die
     * Collection mit den gefüllten Objekten zurück.
     *
     * @param objects
     *            Collection mit zu befüllenden Objekten
     * @param <E>
     *            Typ der Collection, deren Daten befüllt werden soll
     * @return Collection mit gefüllten Objekten
     */
    <E extends Collection<T>> E fetchFullData(final E objects);

    /**
     * Liefert alle Objekte des entsprechenden Typs zurück.
     *
     * @return Alle Objekte des Typs
     */
    Collection<T> findAll();

    /**
     * Liefert alle Objekte des entsprechenden Typs zurück und sortiert sie
     * entsprechend dem übergebenen Sort-Objekt.
     *
     * @param sort
     *            Sort-Objekt, welches angibt wie die Objekte sortiert werden
     *            sollen
     * @return alle Objekte im Bereich der angegebenen Seite und Größe
     */
    Collection<T> findAll(Sort sort);

    /**
     * Liefert alle Objekte des entsprechenden Typs zurück, welche sich im
     * gewünschten Bereich befinden.
     *
     * @param page
     *            Index der Seite, die zurückgegeben wird
     * @param size
     *            Größe der Seite, die zurückgegeben wird
     * @return alle Objekte im Bereich der angegebenen Seite und Größe
     */
    Collection<T> findAll(final int page, final int size);

    /**
     * Liefert alle Objekte des entsprechenden Typs zurück, welche sich im
     * gewünschten Bereich befinden und sortiert sie entsprechend dem
     * übergebenen Sort-Objekt.
     *
     * @param page
     *            Index der Seite, die zurückgegeben wird
     * @param size
     *            Größe der Seite, die zurückgegeben wird
     * @param sort
     *            Sort-Objekt, welches angibt wie die Objekte sortiert werden
     *            sollen
     * @return alle Objekte im Bereich der angegebenen Seite und Größe
     */
    Collection<T> findAll(int page, int size, Sort sort);

    /**
     * Findet alle Objekte des gewünschten Typs und schließt dabei das Objekt
     * mit der übergebenen ID aus.
     *
     * @param id
     *            ID des Objekts, das nicht im Ergebnis enthalten sein soll
     * @return alle Objekte, die einen Treffer der Suche darstellen
     */
    Collection<T> findAllWithoutOne(Long id);

    /**
     * Findet alle Objekte des gewünschten Typs und schließt dabei das Objekt
     * mit der übergebenen ID aus.
     *
     * @param id
     *            ID des Objekts, das nicht im Ergebnis enthalten sein soll
     * @param page
     *            Index der Seite, die zurückgegeben wird
     * @param size
     *            Größe der Seite, die zurückgegeben wird
     * @param sort
     *            Sort-Objekt, welches angibt wie die Objekte sortiert werden
     *            sollen
     * @return alle Objekte, die einen Treffer der Suche darstellen, im Bereich
     *         der angegebenen Seite und Größe
     */
    Collection<T> findAllWithoutOne(Long id, int page, int size, Sort sort);

    /**
     * Findet ein Objekt des gewünschten Typs anhand der ID.
     *
     * @param id
     *            ID des gesuchten Objekts
     * @return Objekt des gewünschten Typs
     */
    T findOne(final Long id);

    /**
     * Zählt alle Objekte, die von diesem Typ in der Datenbank existieren.
     *
     * @return Anzahl der existierenden Elemente
     */
    long count();

}

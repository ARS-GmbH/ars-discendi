/**
 *
 */
package de.ars.skilldb.controller.util;

/**
 * Klasse, die für die Ergebnisrückgabe mit JSON verwendet wird.
 */
public class SearchResult {

    private final String name;
    private final String id;

    /**
     * Konstruiert ein neues Suchergebnis.
     *
     * @param name
     *            der Name des Suchergebnisses, das später dem Benutzer ausgegeben wird.
     * @param id
     *            eine eindeutige Id, dies kann die Id eines Objektes sein, auf das sich das
     *            Suchergebnis bezieht.
     */
    public SearchResult(final String name, final String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

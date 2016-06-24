/**
 *
 */
package de.ars.skilldb.command;

import java.util.LinkedList;
import java.util.List;

import de.ars.skilldb.domain.Path;

/**
 * Repräsentiert ein Objekt, das der Spring-View übergeben werden kann. Enthält eine Liste mit
 * Pfaden zu Zertifizierungen.
 */
public class PathListCommand {

    private List<Path> paths;

    /**
     * Erzeugt eine neues {@code PathListCommand}-Objekt.
     */
    public PathListCommand() {
        paths = new LinkedList<>();
    }

    /**
     * Erzeugt eine neues {@code PathListCommand}-Objekt mit der spezifizierten Liste.
     *
     * @param paths
     *            die Liste der {@code Path}s.
     */
    public PathListCommand(final List<Path> paths) {
        this.paths = paths;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(final List<Path> paths) {
        this.paths = paths;
    }

}

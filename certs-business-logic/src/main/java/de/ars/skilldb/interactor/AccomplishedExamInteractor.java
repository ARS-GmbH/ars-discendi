/**
 *
 */
package de.ars.skilldb.interactor;

import java.util.Collection;

import de.ars.skilldb.domain.AccomplishedExam;
import de.ars.skilldb.domain.User;

/**
 * Interactor für die {@code AccomplishedExam}s, der die notwendige Business-Logik zur Verfügung
 * stellt.
 */
public interface AccomplishedExamInteractor extends Interactor<AccomplishedExam> {

    /**
     * Findet alle {@code AccomplishedExam}s eines Benutzers, die von diesem abgeschlossen wurden.
     *
     * @param user
     *            der User, zu dem alle seine abgeschlossenen Tests zurückgeliefert werden sollen.
     * @return die abgeschlossenen Tests des Users.
     */
    Collection<AccomplishedExam> findAllAccomplishedExams(User user);

    /**
     * Fügt einen vom Benutzer abgeschlossenen Test hinzu.
     *
     * @param accomplishedExam
     *            die neue {@code AccomplishedExam}.
     * @param document
     *            die als Datei zu hinterlegende Bestätigung, dass der Test bestanden wurde.
     * @return den Test, der abgeschlossen wurde.
     */
    AccomplishedExam addAccomplishedExam(AccomplishedExam accomplishedExam, byte[] document);

    /**
     * Holt das Dokument des abgeschlossenen Tests mit der spezifizierten Id als byte-Array.
     *
     * @param id
     *            die Id des abgeschlossenen Tests ({@code AccomplishedExam}).
     * @return das Dokument als byte-Array.
     */
    byte[] getAccomplishedDocument(Long id);
}

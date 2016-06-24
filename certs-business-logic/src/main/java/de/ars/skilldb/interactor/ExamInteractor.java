package de.ars.skilldb.interactor;

import java.util.Collection;

import de.ars.skilldb.domain.Exam;
import de.ars.skilldb.domain.Path;
import de.ars.skilldb.domain.User;

/**
 * Interactor für die {@code Exam}s, der die notwendige Business-Logik zur Verfügung stellt.
 */
public interface ExamInteractor extends Interactor<Exam> {

    /**
     * Findet alle eigenen {@code Exam}s zu dem spezifizierten Benutzer.
     *
     * @param user
     *            der User, zu dem alle {@code Exam}s zurückgeliefert werden sollen.
     *
     * @return alle eigenen {code Exam}s.
     */
    Collection<Exam> findAllMyExams(User user);

    /**
     * Findet {@code Exam}s anhand eines Suchbegriffs. Ein Test wird gefunden, wenn der Suchbegriff
     * im Titel auftaucht.
     *
     * @param searchQuery
     *            der Suchbegriff, mit dem nach dem Test-Titel gesucht wird.
     * @param page
     *            die Seite, ab der die Treffer zurückgegeben werden sollen.
     * @param count
     *            die Anzahl der maximalen Treffer, die zurückgegeben werden sollen.
     * @return die gesuchten {@code Exam}s.
     */
    Collection<Exam> findByTitleContaining(String searchQuery, int page, int count);

    /**
     * Findet alle notwendigen {@code Exam}s von einem Pfad ({@code Path}).
     *
     * @param path
     *            der Pfad, zu dem alle notwendigen Tests gefunden werden sollen.
     * @return alle notwendigen Tests eines Pfads.
     */
    Collection<Exam> findNecessaryExamsOfPath(Path path);

    /**
     * Findet alle wählbaren {@code Exam}s von einem Pfad ({@code Path}).
     *
     * @param path
     *            der Pfad, zu dem alle wählbaren Tests gefunden werden sollen.
     * @return alle wählbaren Tests eines Pfads.
     */
    Collection<Exam> findChoosableExamsOfPath(Path path);

}

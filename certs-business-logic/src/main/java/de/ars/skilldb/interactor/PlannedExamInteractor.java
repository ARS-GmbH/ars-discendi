/**
 *
 */
package de.ars.skilldb.interactor;

import java.util.Collection;

import de.ars.skilldb.domain.Exam;
import de.ars.skilldb.domain.PlannedExam;
import de.ars.skilldb.domain.User;

/**
 * Interactor für die {@code PlannedExam}s, der die notwendige Business-Logik zur Verfügung stellt.
 */
public interface PlannedExamInteractor extends Interactor<PlannedExam> {

    /**
     * Findet alle {@code Exam}s eines Benutzers, die für diesen geplant wurden.
     *
     * @param user
     *            der User, zu dem alle seine geplanten Tests zurückgeliefert werden sollen.
     * @return die geplanten Tests des Users.
     */
    Collection<PlannedExam> findAllMyPlannedExams(User user);

    /**
     * Fügt einen vom Benutzer geplanten Test hinzu.
     *
     * @param plannedExam
     *            die neue {@code PlannedExam}.
     *
     * @return den Test, für den geplant wurde.
     */
    Exam addPlannedExam(PlannedExam plannedExam);

}

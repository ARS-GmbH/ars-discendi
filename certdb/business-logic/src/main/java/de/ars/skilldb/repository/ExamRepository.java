package de.ars.skilldb.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.*;

/**
 * Spring-Data Repository für die Klasse {@code Exam}.
 */
public interface ExamRepository extends PagingAndSortingRepository<Exam, Long> {

    /**
     * Findet alle abgelegten Tests ({@code Exam}s) des spezifizierten Benutzers.
     * 
     * @param user
     *            der Benutzer, dessen Tests gesucht sind.
     * @return die abgelegten Tests ({@code Exam}s) des spezifizierten Benutzers.
     */
    @Query("Start user=node({0}) Match (user)-[ACCOMPLISHED]->(exam:Exam) return exam")
    Iterable<Exam> findMyExams(User user);

    /**
     * Findet alle Tests ({@code Exam}s), die der spezifizierte Benutzer in naher Zukunft ablegen
     * soll.
     * 
     * @param user
     *            der Benutzer, dessen geplante Tests gesucht sind.
     * @return die geplanten Tests ({@code Exam}s) des spezifizierten Benutzers.
     */
    @Query("Start user=node({0}) Match (user)-[r:SHOULD_MAKE]->(exam:Exam) return r")
    Iterable<PlannedExam> findMyPlannedExams(User user);

    /**
     * Findet {@code Exam}s anhand eines Suchbegriffs. Ein Test wird gefunden, wenn der Titel den
     * Suchbegriff enthält.
     * 
     * @param searchQuery
     *            der Suchbegriff, mit dem nach einem Test gesucht werden soll.
     * @param pageable
     *            das {@code Pageable}-Objekt, mit dem die Anzahl der Objekte und die Start-Page
     *            ausgewählt wird.
     * @return eine {@code List}, die alle gefundenen {@code Exam}s enthält.
     */
    List<Exam> findByTitleContaining(String searchQuery, Pageable pageable);

    /**
     * Findet einen geplanten Test ({@code PlannedExam}) zu einem spezifizierten Benutzer und einer
     * spezifizierten {@code Exam}.
     * 
     * @param user
     *            der Benutzer, dessen geplante Tests gesucht sind.
     * @param exam
     *            die {@code Exam}, zu der der geplante Test des Benutzers gefunden werden soll.
     * @return die geplanten Tests ({@code PlannedExam}s) des spezifizierten Benutzers.
     */
    @Query("Start user=node({0}), exam=node({1}) Match (user)<-[r:SHOULD_MAKE]-(exam:Exam) return r")
    Iterable<PlannedExam> findPlannedExamOfUser(User user, Exam exam);

    /**
     * Findet einen abgeschlossenen Test ({@code Accomplishedxam}) zu einem spezifizierten Benutzer
     * und einer spezifizierten {@code Exam}.
     * 
     * @param user
     *            der Benutzer, dessen geplante Tests gesucht sind.
     * @param exam
     *            die {@code Exam}, zu der der abgeschlossene Test des Benutzers gefunden werden
     *            soll.
     * @return die abgeschlossenen Tests ({@code AccomplishedExam}s) des spezifizierten Benutzers.
     */
    @Query("Start user=node({0}), exam=node({1}) Match (user)<-[r:ACCOMPLISHED_BY]-(exam:Exam) return r")
    Iterable<AccomplishedExam> findAccomplishedExamOfUser(User user, Exam exam);

    /**
     * Findet alle notwendigen {@code Exam}s von einem Pfad ({@code Path}).
     * 
     * @param path
     *            der Pfad, zu dem alle notwendigen Tests gefunden werden sollen.
     * @return alle notwendigen Tests eines Pfads.
     */
    @Query("Start path=node({0}) Match (path)<-[NECESSARY_EXAM]-(exam) return exam")
    Iterable<Exam> findNecessaryOfPath(Path path);

    /**
     * Findet alle wählbaren {@code Exam}s von einem Pfad ({@code Path}).
     * 
     * @param path
     *            der Pfad, zu dem alle wählbaren Tests gefunden werden sollen.
     * @return alle wählbaren Tests eines Pfads.
     */
    @Query("Start path=node({0}) Match (path)<-[CHOOSABLE_EXAM]-(exam) return exam")
    Iterable<Exam> findChoosableOfPath(Path path);
}
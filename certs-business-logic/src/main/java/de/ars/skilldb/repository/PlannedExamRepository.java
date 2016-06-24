package de.ars.skilldb.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.Exam;
import de.ars.skilldb.domain.PlannedExam;
import de.ars.skilldb.domain.User;

/**
 * Spring-Data Repository für die Klasse {@code PlannedExam}.
 */
public interface PlannedExamRepository extends PagingAndSortingRepository<PlannedExam, Long> {

    /**
     * Findet eine {@code PlannedExam} anhand der verbundenen {@code Exam} und dem {@code User}.
     * 
     * @param exam
     *            die {@code Exam}, zu der geplant wurde.
     * @param user
     *            der {@code User}, für den der Test geplant wurde.
     * @return die {@code PlannedExam}.
     */
    @Query("Start exam=node({0}), user=node({1}) Match (user)-[r:SHOULD_MAKE]->(exam:Exam) return r")
    PlannedExam findByExamAndUser(Exam exam, User user);

}
/**
 *
 */
package de.ars.skilldb.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.AccomplishedExam;
import de.ars.skilldb.domain.User;

/**
 * Spring-Data Repository für die Klasse {@code AccomplishedExam}.
 */
public interface AccomplishedExamRepository extends PagingAndSortingRepository<AccomplishedExam, Long> {

    /**
     * Findet alle Tests ({@code AccomplishedExam}s), die der spezifizierte Benutzer abgelegt hat.
     * 
     * @param user
     *            der Benutzer, dessen abgeschlossene Tests gesucht sind.
     * @return die abgeschlossenen Tests ({@code AccomplishedExam}s) des spezifizierten Benutzers.
     */
    @Query("Start user=node({0}) Match (user)-[r:ACCOMPLISHED]->(exam:Exam) return r")
    Iterable<AccomplishedExam> findMyAccomplishedExams(User user);

}

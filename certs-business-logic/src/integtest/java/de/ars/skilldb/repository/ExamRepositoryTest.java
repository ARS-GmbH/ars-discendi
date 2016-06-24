package de.ars.skilldb.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import de.ars.skilldb.domain.*;
import de.ars.skilldb.testfixtures.AbstractIntegrationTest;

/**
 * Integrationstests für das {@code ExamRepository}.
 */
public class ExamRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Neo4jTemplate template;

    private User createTestUser(final int number) {
        User user = new User();
        user.setUserName("john.doe" + number);
        return user;
    }

    private Exam createTestExam(final int number) {
        Exam exam = new Exam();
        exam.setTitle("HumptyDumptyPrüfung" + number);
        exam.setNumber("HUM1234-" + number);
        exam.setPassingScorePercentage(50);
        exam.setTestDuration(90);
        return exam;
    }

    private AccomplishedExam createTestAccomplished(final User user, final Exam exam) {
        AccomplishedExam accomplished = new AccomplishedExam();
        accomplished.setUser(user);
        accomplished.setExam(exam);
        accomplished.setCarryOutDate(new Date());

        return accomplished;
    }

    private PlannedExam createTestPlanned(final User user, final Exam exam) {
        PlannedExam planned = new PlannedExam();
        planned.setUser(user);
        planned.setExam(exam);
        planned.setCarryOutUntil(new Date());

        return planned;
    }

    /**
     * Testet, ob die {@code Exam}s zu einem gesuchten {@code User} gefunden werden.
     */
    @Test
    @Transactional
    public void testFindMyExams() {
        User john = userRepository.save(createTestUser(1));
        User max = userRepository.save(createTestUser(2));
        Exam johnsExam = createTestExam(1);
        Exam maxExam = createTestExam(2);
        johnsExam.addAccomplished(createTestAccomplished(john, johnsExam));
        maxExam.addAccomplished(createTestAccomplished(max, maxExam));
        examRepository.save(johnsExam);
        Exam expected = examRepository.save(maxExam);

        List<Exam> actualExams = Lists.newArrayList(examRepository.findMyExams(max));

        assertEquals("The size of the exam list is not as expected.", 1, actualExams.size());
        assertEquals("The expected exam is not equal", expected, actualExams.get(0));
    }

    /**
     * Testet, ob eine Exam für einen Benutzer geplant werden kann.
     */
    @Test
    @Transactional
    public void testPlanExamForUser() {
        User john = userRepository.save(createTestUser(1));
        Exam exam = createTestExam(1);
        exam.addPlanned(createTestPlanned(john, exam));

        Exam expected = examRepository.save(exam);

        List<PlannedExam> actualExams = Lists.newArrayList(examRepository.findMyPlannedExams(john));
        assertEquals("The size of the exam list is not as expected.", 1, actualExams.size());
        assertEquals("The expected exam is not equal", expected, template.fetch(actualExams.get(0).getExam()));
    }

}

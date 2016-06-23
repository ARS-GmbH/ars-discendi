package de.ars.skilldb.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import de.ars.skilldb.domain.AccomplishedExam;
import de.ars.skilldb.domain.Exam;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.testfixtures.AbstractIntegrationTest;

/**
 * Integrationstests für das {@code ExamRepository}.
 */
public class AccomplishedExamRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private AccomplishedExamRepository accomplishedExamRepository;

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

    /**
     * Testet, ob die {@code Exam}s zu einem gesuchten {@code User} gefunden werden. Es sollen nur
     * {@code Exam}s zurückgegeben werden, die eine Zertifizierung abschließen.
     */
    @Test
    @Transactional
    public void testFindMyAccomplishedExams() {
        User john = userRepository.save(createTestUser(1));
        Exam exam = createTestExam(1);
        exam.addAccomplished(createTestAccomplished(john, exam));

        Exam expected = examRepository.save(exam);

        List<AccomplishedExam> actualExams = Lists.newArrayList(accomplishedExamRepository
                .findMyAccomplishedExams(john));
        assertEquals("The size of the exam list is not as expected.", 1, actualExams.size());
        assertEquals("The expected exam is not equal", expected, template.fetch(actualExams.get(0).getExam()));
    }

}

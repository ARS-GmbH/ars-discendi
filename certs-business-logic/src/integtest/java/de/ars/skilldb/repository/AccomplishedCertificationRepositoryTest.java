/**
 *
 */
package de.ars.skilldb.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.*;
import de.ars.skilldb.testfixtures.AbstractIntegrationTest;

/**
 * Integrationstests für das {@code CertificationRepository}.
 */
public class AccomplishedCertificationRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private AccomplishedCertificationRepository repository;

    @Autowired
    private Neo4jTemplate template;

    private Certification createTestCertification(final int number) {
        Certification cert = new Certification();
        cert.setName("Test-" + number);
        cert.setCertificationCode("TEST123-" + number);
        return cert;
    }

    private Exam createTestExam(final int number) {
        Exam exam = new Exam();
        exam.setTitle("HumptyDumptyPrüfung" + number);
        exam.setNumber("HUM1234-" + number);
        exam.setPassingScorePercentage(50);
        exam.setTestDuration(90);
        return exam;
    }

    private User createTestUser(final int number) {
        User user = new User();
        user.setUserName("john.doe" + number);
        return user;
    }

    /**
     * Testet, ob korrekt ermittelt wird, ob ein Benutzer alle notwendigen Tests eines Pfads
     * bestanden hat.
     */
    @Test
    @Transactional
    public void testHasUserNecessaryExamsForPath() {
        Path path = new Path(0);
        Exam exam1 = template.save(createTestExam(1));
        Exam exam2 = template.save(createTestExam(2));
        path.addNecessaryExam(exam1);
        path.addNecessaryExam(exam2);
        path = template.save(path);

        User user = template.save(createTestUser(1));
        AccomplishedExam accomplished1 = new AccomplishedExam();
        accomplished1.setExam(exam1);
        accomplished1.setUser(user);
        template.save(accomplished1);
        AccomplishedExam accomplished2 = new AccomplishedExam();
        accomplished2.setExam(exam2);
        accomplished2.setUser(user);
        template.save(accomplished2);

        assertTrue("The user has accomplished both necessary exams, but check returned false",
                repository.hasUserNecessaryExamsForPath(path, user));
    }

    /**
     * Testet, ob korrekt ermittelt wird, wenn ein Benutzer nicht alle notwendigen Tests eines Pfads
     * bestanden hat.
     */
    @Test
    @Transactional
    public void testHasNotAllNecessaryExamsForPath() {
        Path path = new Path(0);
        Exam exam1 = template.save(createTestExam(1));
        Exam exam2 = template.save(createTestExam(2));
        path.addNecessaryExam(exam1);
        path.addNecessaryExam(exam2);
        path = template.save(path);

        User user = template.save(createTestUser(1));
        AccomplishedExam accomplished1 = new AccomplishedExam();
        accomplished1.setExam(exam1);
        accomplished1.setUser(user);
        template.save(accomplished1);

        assertFalse("The user has not accomplished both necessary exams, but check returned true",
                repository.hasUserNecessaryExamsForPath(path, user));
    }

    /**
     * Testet, ob korrekterweise {@code true} zurück gegeben wird, wenn es für den Pfad keine
     * notwendigen Tests gibt.
     */
    @Test
    @Transactional
    public void testTrueWhenNoNecessaryExamsForPath() {
        Path path = new Path(0);
        Exam exam1 = template.save(createTestExam(1));
        path = template.save(path);

        User user = template.save(createTestUser(1));
        AccomplishedExam accomplished1 = new AccomplishedExam();
        accomplished1.setExam(exam1);
        accomplished1.setUser(user);
        template.save(accomplished1);

        assertTrue("There are no necessary exams for this path, but check returned false",
                repository.hasUserNecessaryExamsForPath(path, user));
    }

    /**
     * Testet, ob korrekt ermittelt wird, ob ein Benutzer einen der möglichen Tests eines Pfads
     * bestanden hat.
     */
    @Test
    @Transactional
    public void testHasUserOneChoosableExamForPath() {
        Path path = new Path(0);
        Exam exam1 = template.save(createTestExam(1));
        Exam exam2 = template.save(createTestExam(2));
        path.addChoosableExam(exam1);
        path.addChoosableExam(exam2);
        path = template.save(path);

        User user = template.save(createTestUser(1));
        AccomplishedExam accomplished = new AccomplishedExam();
        accomplished.setExam(exam1);
        accomplished.setUser(user);
        template.save(accomplished);

        assertTrue("The user has accomplished a choosable exam, but check returned false",
                repository.hasUserOneChoosableExamForPath(path, user));
    }

    /**
     * Testet, ob korrekt ermittelt wird, wenn ein Benutzer keinen der möglichen Tests eines Pfads
     * bestanden hat.
     */
    @Test
    @Transactional
    public void testHasUserNotOneChoosableExamForPath() {
        Path path = new Path(0);
        Exam exam1 = template.save(createTestExam(1));
        Exam exam2 = template.save(createTestExam(2));
        path.addChoosableExam(exam1);
        path.addChoosableExam(exam2);
        path = template.save(path);

        User user = template.save(createTestUser(1));

        assertFalse("The user has not accomplished a choosable exam, but check returned true",
                repository.hasUserOneChoosableExamForPath(path, user));
    }

    /**
     * Testet, ob korrekterweise {@code true} zurück gegeben wird, wenn es für den Pfad keine
     * wählbaren Tests gibt.
     */
    @Test
    @Transactional
    public void testTrueWhenNoChoosableExamsForPath() {
        Path path = new Path(0);
        Exam exam1 = template.save(createTestExam(1));
        path = template.save(path);

        User user = template.save(createTestUser(1));
        AccomplishedExam accomplished = new AccomplishedExam();
        accomplished.setExam(exam1);
        accomplished.setUser(user);
        template.save(accomplished);

        assertTrue("There are no choosable exams for this path, but check returned false",
                repository.hasUserOneChoosableExamForPath(path, user));
    }

    /**
     * Testet, ob korrekt ermittelt wird, ob ein Benutzer alle notwendigen Zertifizierungen eines
     * Pfads abgelegt hat.
     */
    @Test
    @Transactional
    public void testHasUserNecessaryCertificationsForPath() {
        Path path = new Path(0);
        Certification cert1 = template.save(createTestCertification(1));
        Certification cert2 = template.save(createTestCertification(2));
        path.addNecessaryCertification(cert1);
        path.addNecessaryCertification(cert2);
        path = template.save(path);

        User user = template.save(createTestUser(1));
        AccomplishedCertification accomplished1 = new AccomplishedCertification();
        accomplished1.setCertification(cert1);
        accomplished1.setUser(user);
        template.save(accomplished1);
        AccomplishedCertification accomplished2 = new AccomplishedCertification();
        accomplished2.setCertification(cert2);
        accomplished2.setUser(user);
        template.save(accomplished2);

        assertTrue("The user has accomplished both necessary certifications, but check returned false",
                repository.hasUserNecessaryCertificationsForPath(path, user));
    }

    /**
     * Testet, ob korrekt ermittelt wird, wenn ein Benutzer nicht alle notwendigen Zertifizierungen
     * eines Pfads abgelegt hat.
     */
    @Test
    @Transactional
    public void testHasNotAllNecessaryCertificationsForPath() {
        Path path = new Path(0);
        Certification cert1 = template.save(createTestCertification(1));
        Certification cert2 = template.save(createTestCertification(2));
        path.addNecessaryCertification(cert1);
        path.addNecessaryCertification(cert2);
        path = template.save(path);

        User user = template.save(createTestUser(1));
        AccomplishedCertification accomplished1 = new AccomplishedCertification();
        accomplished1.setCertification(cert1);
        accomplished1.setUser(user);
        template.save(accomplished1);

        assertFalse("The user has not accomplished both necessary certifications, but check returned true",
                repository.hasUserNecessaryCertificationsForPath(path, user));
    }

    /**
     * Testet, ob korrekterweise {@code true} zurück gegeben wird, wenn es für den Pfad keine
     * notwendigen Zertifizierungen gibt.
     */
    @Test
    @Transactional
    public void testTrueWhenNoNecessaryCertificationsForPath() {
        Path path = new Path(0);
        Certification cert1 = template.save(createTestCertification(1));
        path = template.save(path);

        User user = template.save(createTestUser(1));
        AccomplishedCertification accomplished1 = new AccomplishedCertification();
        accomplished1.setCertification(cert1);
        accomplished1.setUser(user);
        template.save(accomplished1);

        assertTrue("There are no necessary certifications for this path, but check returned false",
                repository.hasUserNecessaryCertificationsForPath(path, user));
    }

}

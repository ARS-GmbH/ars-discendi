/**
 *
 */
package de.ars.skilldb.interactor.impl;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.interactor.CertificationInteractor;
import de.ars.skilldb.testfixtures.AbstractIntegrationTest;

/**
 * Integrationstests für die Klasse {@code CertificationInteractorImpl}.
 */
public class CertificationInteractorImplIntegTest extends AbstractIntegrationTest {

    @Autowired
    private CertificationInteractor interactor;

    private Certification createTestCertification() {
        Certification cert = new Certification();
        cert.setName("Test");
        cert.setCertificationCode("TEST123");
        cert.setVersion(1);
        return cert;
    }

    /**
     * Testet, ob nach einem Update die Vorgänger-Nachfolger-Beziehungen korrekt sind.
     */
    @Test
    @Transactional
    @DirtiesContext
    public void testUpdateCertifications() {
        interactor.loadCertificationsInitially();
        Collection<Certification> response = interactor.updateCertifications();
        testCorrectPredecessorRelationship(response);
    }

    /**
     * Testet, ob nach einem initalen Laden die Vorgänger-Nachfolger-Beziehungen korrekt sind.
     */
    @Test
    @Transactional
    @DirtiesContext
    public void testLoadCertificationsInitially() {
        Collection<Certification> response = interactor.loadCertificationsInitially();
        testCorrectPredecessorRelationship(response);
        testCorrectPathCount(response);
    }

    private void testCorrectPathCount(final Collection<Certification> response) {
        for (Certification certification : response) {
            assertEquals("There is a wrong amount of paths for this certification", 1, certification.getPaths().size());
        }
    }

    private void testCorrectPredecessorRelationship(final Collection<Certification> response) {
        Collection<Certification> certifications = response;

        boolean testExecuted = false;
        for (Certification certification : certifications) {
            Certification fullCert = interactor.fetchFullData(certification);
            if (fullCert.getPredecessor() != null && fullCert.getPredecessor().getVersion() != null) {
                testExecuted = true;
                assertTrue("The predecessor version number is not less than the actual one.", fullCert.getPredecessor()
                        .getVersion() < fullCert.getVersion());
            }
        }

        if (!testExecuted) {
            fail("No predecessors have been tested.");
        }
    }

    /**
     * Testet, ob eine Certification anhand der Id gefunden werden kann.
     */
    @Test
    @Transactional
    public void testFindOne() {
        Certification cert = createTestCertification();
        Certification savedCert = interactor.save(cert);

        Certification foundCert = interactor.findOne(savedCert.getId());

        assertEquals("The found Certification is not equal to the saved one.", savedCert, foundCert);
    }

}

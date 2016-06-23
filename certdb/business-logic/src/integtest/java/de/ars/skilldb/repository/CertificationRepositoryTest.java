package de.ars.skilldb.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.testfixtures.AbstractIntegrationTest;

/**
 * Integrationstests für das {@code CertificationRepository}.
 */
public class CertificationRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private CertificationRepository repository;

    @Autowired
    private Neo4jTemplate template;

    /**
     * Testet, ob eine Zertifizierung anhand des eindeutigen Namens gefunden werden kann.
     */
    @Test
    @Transactional
    public void testFindByName() {
        Certification cert = new Certification();
        cert.setName("Testname");
        repository.save(cert);

        Certification actual = repository.findByName("Testname");

        assertEquals("The name of the found Certification is not equal.", "Testname", actual.getName());
    }

    /**
     * Testet, ob eine {@code DataIntegrityViolationException} geworfen wird, wenn versucht wird,
     * einen Zertifizierungsnamen zu setzen, der bereits anderweitig verwendet wird.
     */
    @Test(expected = DataIntegrityViolationException.class)
    @Transactional
    public void testSaveDuplicateCertifications() {
        Certification cert1 = new Certification();
        cert1.setName("Test1");

        Certification cert2 = new Certification();
        cert2.setName("Test1");

        repository.save(cert1);
        repository.save(cert2);
        assertEquals("The number of certifications saved is not 1.", 1, repository.count());

        Certification cert3 = new Certification();
        cert3.setName("Test2");
        Certification cert3saved = repository.save(cert3);
        cert3saved.setBrands(template.fetch(cert3saved.getBrands()));
        cert3saved.setName("Test1");
        repository.save(cert3saved);
    }

}

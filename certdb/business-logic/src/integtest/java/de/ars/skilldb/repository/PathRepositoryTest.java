package de.ars.skilldb.repository;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.Path;
import de.ars.skilldb.testfixtures.AbstractIntegrationTest;

/**
 * Integrationstests für das {@code CertificationRepository}.
 */
public class PathRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private CertificationRepository certRepo;

    private Certification createTestCertification() {
        Certification cert = new Certification();
        cert.setName("Test");
        cert.setCertificationCode("TEST123");
        return cert;
    }

    /**
     * Testet, ob Zertifizierungspfade durch Ersetzen des Sets entfernt werden können.
     */
    @Test
    public void testDeletePathFromCertification() {
        Certification cert = createTestCertification();
        Path path1 = new Path(0);
        Path path2 = new Path(1);
        cert.addPath(path1);
        cert.addPath(path2);

        Certification savedCert = certRepo.save(cert);
        assertEquals("The size of the paths is not as expected", 2, savedCert.getPaths().size());

        savedCert.setPaths(new HashSet<Path>());
        savedCert.addPath(path1);

        savedCert = certRepo.save(savedCert);
        assertEquals("The size of the paths is not as expected after save", 1, Lists.newArrayList(savedCert.getPaths())
                .size());
    }

}

package de.ars.skilldb.input;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/**
 * Testet die Klasse {@code CertSlurper}.
 * 
 */
public class CertSlurperTest {

    /**
     * Testet, ob die Zertifizierungen korrekt von einer Webseite geladen werden.
     */
    @Test
    public void testSlurpCertsFromWebPage() {
        String ibmCertPage = "http://www-03.ibm.com/certify/partner/pub/zz/mem_skillsreq_printout.shtml";
        String tableClass = "reference";
        CertSlurper slurper = new CertSlurper(ibmCertPage, tableClass);
        CertWebEntry expectedFith = createExpectedFithCertWebEntry();
        CertWebEntry expectedLast = createExpectedLastCertWebEntry();

        List<CertWebEntry> certWebEntries = slurper.slurpCertWebEntries();
        CertWebEntry actualFith = certWebEntries.get(4);
        CertWebEntry actualLast = certWebEntries.get(certWebEntries.size() - 1);

        assertEquals("The fith element of the table is not the expected one", expectedFith, actualFith);
        assertEquals("The last element of the table is not the expected one", expectedLast, actualLast);
    }

    /**
     * Testet, ob die Zertifizierungen korrekt von einer lokalen Datei geladen werden.
     */
    @Test
    public void testSlurpCertsFromLocalFile() {
        String ibmCertPage = "/eligible-skills.html";
        String tableClass = "reference";
        CertSlurper slurper = new CertSlurper(ibmCertPage, tableClass);
        CertWebEntry expectedFith = createExpectedFithCertWebEntry();
        CertWebEntry expectedLast = createExpectedLastCertWebEntry();

        List<CertWebEntry> certWebEntries = slurper.slurpCertWebEntries();
        CertWebEntry actualFith = certWebEntries.get(4);
        CertWebEntry actualLast = certWebEntries.get(certWebEntries.size() - 1);

        assertEquals("The fith element of the table is not the expected one", expectedFith, actualFith);
        assertEquals("The last element of the table is not the expected one", expectedLast, actualLast);
    }

    /**
     * Testet, ob eine {@code IllegalArgumentException} geworfen wird, wenn der Dateipfad ungültig
     * ist.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSlurpCertsFromLocalFileIllegalUrl() {
        CertSlurper slurper = new CertSlurper("FALSCH", "irgendwas");

        slurper.slurpCertWebEntries();
    }

    /**
     * Testet, ob eine {@code IllegalArgumentException} geworfen wird, wenn die Web-URL ungültig
     * ist.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSlurpCertsFromWebPageIllegalUrl() {
        CertSlurper slurper = new CertSlurper("http FALSCH", "irgendwas");

        slurper.slurpCertWebEntries();
    }

    private CertWebEntry createExpectedFithCertWebEntry() {
        CertWebEntry expectedFith = new CertWebEntry();
        expectedFith.setStatus("Active");
        expectedFith.setSequenceNumber("3");
        expectedFith.setName("IBM Certified Systems Expert - High "
                + "Availability for AIX Technical Support and Administration -v2");
        expectedFith.setLink("https://www.ibm.com/certify/certs/03005505.shtml");
        expectedFith.setTechnical("T");
        expectedFith.setSkillPoint("3");
        expectedFith.setExpirationDate("");
        expectedFith.setCertificationCode("030055");
        expectedFith.setVersion("05");
        expectedFith.setBrand("IBM Power Systems");
        expectedFith.setProductGroup("");

        return expectedFith;
    }

    private CertWebEntry createExpectedLastCertWebEntry() {
        CertWebEntry expectedFith = new CertWebEntry();
        expectedFith.setStatus("Active");
        expectedFith.setSequenceNumber("");
        expectedFith.setName("VMware Certified Professional (VCP) - VCP3, VCP4 or VCP5");
        expectedFith.setTechnical("");
        expectedFith.setSkillPoint("1");
        expectedFith.setExpirationDate("");
        expectedFith.setCertificationCode("OTH0351");
        expectedFith.setVersion("");
        expectedFith.setBrand("non-IBM Certifications");
        expectedFith.setProductGroup("");

        return expectedFith;
    }

}

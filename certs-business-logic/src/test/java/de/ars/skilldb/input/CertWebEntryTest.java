package de.ars.skilldb.input;

import static org.junit.Assert.assertEquals;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

/**
 * Testet die Klasse {@code CertWebEntry}.
 */
public class CertWebEntryTest {

    /**
     * Testet den Getter und Setter des Attributs {@code Status}.
     */
    @Test
    public void testGetAndSetStatus() {
        CertWebEntry testObject = new CertWebEntry();
        String expected = "TestString";
        testObject.setStatus(expected);

        String actual = testObject.getStatus();

        assertEquals("Getter And Setter for Status does not work as expected.", expected, actual);
    }

    /**
     * Testet den Getter und Setter des Attributs {@code SequenceNumber}.
     */
    @Test
    public void testGetAndSetSequenceNumber() {
        CertWebEntry testObject = new CertWebEntry();
        String expected = "13";
        testObject.setSequenceNumber(expected);

        String actual = testObject.getSequenceNumber();

        assertEquals("Getter And Setter for SequenceNumber does not work as expected.", expected, actual);
    }

    /**
     * Testet den Getter und Setter des Attributs {@code Name}.
     */
    @Test
    public void testGetAndSetName() {
        CertWebEntry testObject = new CertWebEntry();
        String expected = "Name";
        testObject.setName(expected);

        String actual = testObject.getName();

        assertEquals("Getter And Setter for Name does not work as expected.", expected, actual);
    }

    /**
     * Testet den Getter und Setter des Attributs {@code Technical}.
     */
    @Test
    public void testGetAndSetTechnical() {
        CertWebEntry testObject = new CertWebEntry();
        String expected = "T";
        testObject.setTechnical(expected);

        String actual = testObject.getTechnical();

        assertEquals("Getter And Setter for Technical does not work as expected.", expected, actual);
    }

    /**
     * Testet den Getter und Setter des Attributs {@code SkillPoint}.
     */
    @Test
    public void testGetAndSetSkillPoint() {
        CertWebEntry testObject = new CertWebEntry();
        String expected = "02";
        testObject.setSkillPoint(expected);

        String actual = testObject.getSkillPoint();

        assertEquals("Getter And Setter for SkillPoint does not work as expected.", expected, actual);
    }

    /**
     * Testet den Getter und Setter des Attributs {@code ExpirationDate}.
     */
    @Test
    public void testGetAndSetExpirationDate() {
        CertWebEntry testObject = new CertWebEntry();
        String expected = "Aug 13 2014";
        testObject.setExpirationDate(expected);

        String actual = testObject.getExpirationDate();

        assertEquals("Getter And Setter for ExpirationDate does not work as expected.", expected, actual);
    }

    /**
     * Testet den Getter und Setter des Attributs {@code CertificationCode}.
     */
    @Test
    public void testGetAndSetCertificationCode() {
        CertWebEntry testObject = new CertWebEntry();
        String expected = "000034";
        testObject.setCertificationCode(expected);

        String actual = testObject.getCertificationCode();

        assertEquals("Getter And Setter for CertificationCode does not work as expected.", expected, actual);
    }

    /**
     * Testet den Getter und Setter des Attributs {@code Version}.
     */
    @Test
    public void testGetAndSetVersion() {
        CertWebEntry testObject = new CertWebEntry();
        String expected = "1a";
        testObject.setVersion(expected);

        String actual = testObject.getVersion();

        assertEquals("Getter And Setter for Version does not work as expected.", expected, actual);
    }

    /**
     * Testet den Getter und Setter des Attributs {@code Brand}.
     */
    @Test
    public void testGetAndSetBrand() {
        CertWebEntry testObject = new CertWebEntry();
        String expected = "IBM WebSphere";
        testObject.setBrand(expected);

        String actual = testObject.getBrand();

        assertEquals("Getter And Setter for Brand does not work as expected.", expected, actual);
    }

    /**
     * Testet den Getter und Setter des Attributs {@code ProductGroup}.
     */
    @Test
    public void testGetAndSetProductGroup() {
        CertWebEntry testObject = new CertWebEntry();
        String expected = "IBM Cloud Computing";
        testObject.setProductGroup(expected);

        String actual = testObject.getProductGroup();

        assertEquals("Getter And Setter for ProductGroup does not work as expected.", expected, actual);
    }

    /**
     * Testet den Equals. und HashCode-Vertrag.
     */
    @Test
    public void testEqualsObject() {
        EqualsVerifier.forClass(CertWebEntry.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    /**
     * Testet die Methode {@code toString()}.
     */
    @Test
    public void testToString() {
        CertWebEntry testObject = new CertWebEntry();
        addAttributesTo(testObject);
        String expected = "CertWebEntry [status=ACTIVE, sequenceNumber=0, name=Beispiel, link=www.ibm.de, "
                + "technical=T, skillPoint=3, expirationDate=null, certificationCode=00013, "
                + "version=2, brand=IBM WebSphere, productGroup=IBM WAS]";

        String actual = testObject.toString();

        assertEquals("The toString() method does not return the expected String", expected, actual);
    }

    private void addAttributesTo(final CertWebEntry testObject) {
        testObject.setStatus("ACTIVE");
        testObject.setSequenceNumber("0");
        testObject.setName("Beispiel");
        testObject.setLink("www.ibm.de");
        testObject.setTechnical("T");
        testObject.setSkillPoint("3");
        testObject.setCertificationCode("00013");
        testObject.setVersion("2");
        testObject.setBrand("IBM WebSphere");
        testObject.setProductGroup("IBM WAS");
    }

}

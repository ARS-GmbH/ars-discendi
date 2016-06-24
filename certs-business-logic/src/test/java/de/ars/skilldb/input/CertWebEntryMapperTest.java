package de.ars.skilldb.input;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.collect.Multimap;

import de.ars.skilldb.domain.Brand;
import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.ProductGroup;
import de.ars.skilldb.domain.enums.CertKind;
import de.ars.skilldb.domain.enums.CertStatus;

/**
 * Testet die Klasse {@code CertWebEntryMapper}.
 * 
 */
public class CertWebEntryMapperTest {

    private static final String BEISPIEL_CERTCODE = "570001";
    private static final String BEISPIEL_BRAND = "IBM Networking";
    private static final String BEISPIEL_PRODUCT_GROUP = "Beispiel ProductGroup";
    private static final String BEISPIEL_ZERT = "BeispielZert";
    private static final String BEISPIEL_NR = "1a";

    private CertWebEntryMapper certWebEntryMapper;

    private ProductGroupContainer productGroupContainer;
    private BrandContainer brandContainer;
    private CertificationContainer certificationContainer;

    private Brand brand;
    private ProductGroup productGroup;
    private CertWebEntry certWebEntry;

    /**
     * Initialisiert die Mocks mit Beispieldaten.
     */
    @Before
    public void before() {
        createCertWebEntryMapper();
        certWebEntry = createSampleWebEntry();

        Certification certification;
        certification = new Certification();
        certification.setName(BEISPIEL_ZERT);
        brand = new Brand();
        productGroup = new ProductGroup();
        productGroup.addBrand(brand);
        productGroup.setName(BEISPIEL_PRODUCT_GROUP);

        when(certificationContainer.retrieve(BEISPIEL_ZERT)).thenReturn(certification);
        when(brandContainer.retrieve(BEISPIEL_BRAND)).thenReturn(brand);
        when(productGroupContainer.retrieve(BEISPIEL_PRODUCT_GROUP)).thenReturn(productGroup);
    }

    /**
     * Testet, ob ein {@code CertWebEntry} korrekt in eine {@code Certification} konvertiert wird.
     */
    @Test
    public void testConvertToCertification() {
        Certification expected = createSampleCertification();

        Certification actual = certWebEntryMapper.convertToCertification(certWebEntry);

        assertEquals("Die erwartete Zertifizierung stimmt nicht überein.", expected, actual);
    }

    /**
     * Testet, ob ein {@code CertWebEntry} korrekt in eine {@code Certification} konvertiert wird,
     * wenn das Ablaufdatum falsch gesetzt ist.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertToCertificationIllegalDate() {
        certWebEntry.setExpirationDate("FALSCH");

        certWebEntryMapper.convertToCertification(certWebEntry);
    }

    /**
     * Testet die Konvertierung einer Sales-Zertifizierung.
     */
    @Test
    public void testConvertToCertificationWithKindSales() {
        Certification expected = createSampleCertification();
        expected.setKind(CertKind.SALES);
        certWebEntry.setTechnical("S");

        Certification actual = certWebEntryMapper.convertToCertification(certWebEntry);

        assertEquals("Die erwartete Zertifizierung stimmt nicht überein.", expected, actual);
    }

    /**
     * Testet die Konvertierung einer Zertifizierung ohne Art (Sales oder Technical).
     */
    @Test
    public void testConvertToCertificationWithNoKind() {
        Certification expected = createSampleCertification();
        expected.setKind(CertKind.NONE);
        certWebEntry.setTechnical("");

        Certification actual = certWebEntryMapper.convertToCertification(certWebEntry);

        assertEquals("Die erwartete Zertifizierung stimmt nicht überein.", expected, actual);
    }

    /**
     * Testet die Konvertierung einer Zertifizierung mit unbestimmter Art.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertToCertificationIllegalKind() {
        certWebEntry.setTechnical("FALSCH");

        certWebEntryMapper.convertToCertification(certWebEntry);
    }

    /**
     * Testet die Konvertierung einer Zertifizierung mit Status EXPIRING.
     */
    @Test
    public void testConvertToCertificationStatusExpiring() {
        testConversionWithDifferentStatus(CertStatus.EXPIRING, "expiring");
    }

    /**
     * Testet die Konvertierung einer Zertifizierung mit Status EXPIRED.
     */
    @Test
    public void testConvertToCertificationStatusExpired() {
        testConversionWithDifferentStatus(CertStatus.EXPIRED, "expired");
    }

    /**
     * Testet die Konvertierung einer Zertifizierung mit Status WITHDRAWN.
     */
    @Test
    public void testConvertToCertificationStatusWithdrawn() {
        testConversionWithDifferentStatus(CertStatus.WITHDRAWN, "withdrAwn");
    }

    /**
     * Testet die Konvertierung einer Zertifizierung mit Status NEW_ACTIVE.
     */
    @Test
    public void testConvertToCertificationStatusNewActive() {
        testConversionWithDifferentStatus(CertStatus.NEW_ACTIVE, "new/aCtIve");
    }

    /**
     * Testet die Konvertierung einer Zertifizierung mit ungültigem Status.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertToCertificationStatusIllegal() {
        testConversionWithDifferentStatus(CertStatus.NEW_ACTIVE, "FALSCH");
    }

    private void testConversionWithDifferentStatus(final CertStatus status, final String statusLiteral) {
        Certification expected = createSampleCertification();
        expected.setStatus(status);
        certWebEntry.setStatus(statusLiteral);

        Certification actual = certWebEntryMapper.convertToCertification(certWebEntry);

        assertEquals("Die erwartete Zertifizierung stimmt nicht überein.", expected, actual);
    }

    /**
     * Testet die Konvertierung mehrerer Zertifizierungen.
     */
    @Test
    public void testConvertMultipleEntries() {
        List<CertWebEntry> webEntries = new LinkedList<>();
        webEntries.add(createSampleWebEntry());
        webEntries.add(createSampleWebEntry());

        List<Certification> actual = certWebEntryMapper.convertMultipleEntries(webEntries);

        assertEquals("Die Liste hat nicht die passende Anzahl an Elementen", 2, actual.size());
    }

    /**
     * Testet die Konvertierung mehrerer Zertifizierungen zu einer Multimap.
     */
    @Test
    public void testConvertMultipleEntriesToMap() {
        List<CertWebEntry> webEntries = new LinkedList<>();
        webEntries.add(createSampleWebEntry());
        webEntries.add(createSampleWebEntry());

        Multimap<String, Certification> actual = certWebEntryMapper.convertMultipleEntriesToMap(webEntries);

        assertEquals("Die Liste hat nicht die passende Anzahl an Elementen", 2, actual.size());
    }

    private void createCertWebEntryMapper() {
        certWebEntryMapper = new CertWebEntryMapper("MMM dd yyyy");
        productGroupContainer = mock(ProductGroupContainer.class);
        brandContainer = mock(BrandContainer.class);
        certificationContainer = mock(CertificationContainer.class);
        ReflectionTestUtils.setField(certWebEntryMapper, "productGroups", productGroupContainer);
        ReflectionTestUtils.setField(certWebEntryMapper, "brands", brandContainer);
        ReflectionTestUtils.setField(certWebEntryMapper, "certifications", certificationContainer);
    }

    private CertWebEntry createSampleWebEntry() {
        CertWebEntry webEntry = new CertWebEntry();
        webEntry.setLink("http://www.samplelink.com/ibm");
        webEntry.setStatus("ACTIVE");
        webEntry.setSequenceNumber(BEISPIEL_NR);
        webEntry.setName(BEISPIEL_ZERT);
        webEntry.setTechnical("T");
        webEntry.setSkillPoint("2");
        webEntry.setExpirationDate("Aug 31 2014");
        webEntry.setCertificationCode(BEISPIEL_CERTCODE);
        webEntry.setVersion("01");
        webEntry.setBrand(BEISPIEL_BRAND);
        webEntry.setProductGroup(BEISPIEL_PRODUCT_GROUP);
        return webEntry;
    }

    private Certification createSampleCertification() {
        Certification certification = new Certification();
        certification.setLink("http://www.samplelink.com/ibm");
        certification.setStatus(CertStatus.ACTIVE);
        certification.setSequenceNumber(BEISPIEL_NR);
        certification.setName(BEISPIEL_ZERT);
        certification.setKind(CertKind.TECHNICAL);
        certification.setSkillPoint(2);
        certification.setExpirationDate(new Date(1409436000000L));
        certification.setCertificationCode(BEISPIEL_CERTCODE);
        certification.setVersion(1);
        certification.addBrand(brand);
        certification.addProductGroup(productGroup);
        return certification;
    }
}
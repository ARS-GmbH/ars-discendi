package de.ars.skilldb.input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import de.ars.skilldb.domain.Brand;
import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.ProductGroup;
import de.ars.skilldb.domain.enums.CertKind;
import de.ars.skilldb.domain.enums.CertStatus;

/**
 * Mappt die von der Website ausgelesenen IBM-Zertifizierungen auf die {@code Certification}
 * -Objekte, die im Anschluss in die Datenbank abgespeichert werden können.
 */
public class CertWebEntryMapper {

    private static final String THE_SPECIFIED_STRING = "The specified String '";

    @Autowired
    private ProductGroupContainer productGroups;

    @Autowired
    private BrandContainer brands;

    @Autowired
    private CertificationContainer certifications;

    private final String dateFormat;

    /**
     * Erzeugt einen neuen {@code CertWebEntryMapper} mit dem spezifizierten Datumsformat.
     *
     * @param dateFormat
     *            das Format der Datumsangabe.
     */
    public CertWebEntryMapper(final String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Konvertiert ein Zertifizierungs-Webeintrag ({@code CertWebEntry}) in eine
     * {@code Certification}.
     *
     * @param entry
     *            der Webeintrag einer Zertifizierung, der konvertiert werden soll.
     * @return ein {@code Certification}-Objekt.
     */
    public Certification convertToCertification(final CertWebEntry entry) {
        Certification cert = certifications.retrieve(entry.getName());
        cert.setStatus(getStatusFromString(entry.getStatus()));
        cert.setSequenceNumber(entry.getSequenceNumber());
        cert.setKind(getKindFromString(entry.getTechnical()));
        cert.setLink("".equals(entry.getLink()) ? cert.getLink() : entry.getLink());

        String skillPoint = entry.getSkillPoint();
        cert.setSkillPoint("".equals(skillPoint) ? 0 : Integer.parseInt(entry.getSkillPoint()));

        String expirationDate = entry.getExpirationDate();

        if (!"".equals(expirationDate)) {
            cert.setExpirationDate(parseDate(expirationDate));
        }
        cert.setCertificationCode(entry.getCertificationCode());

        String version = entry.getVersion();
        cert.setVersion("".equals(version) ? 0 : Integer.parseInt(entry.getVersion()));

        String productGroupName = entry.getProductGroup();
        String brandName = entry.getBrand();
        Brand brand = brands.retrieve(brandName);
        cert.addBrand(brand);

        if (!"".equals(productGroupName)) {
            ProductGroup productGroup = productGroups.retrieve(productGroupName);
            productGroup.addBrand(brand);
            cert.addProductGroup(productGroup);
        }

        return cert;
    }

    private Date parseDate(final String expirationDate) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            return format.parse(expirationDate);
        }
        catch (ParseException e) {
            throw new IllegalArgumentException(THE_SPECIFIED_STRING + expirationDate
                    + "' is not a valid expiration date.", e);
        }
    }

    private CertKind getKindFromString(final String technical) {
        switch (technical.toUpperCase(Locale.ENGLISH)) {
        case "T":
            return CertKind.TECHNICAL;
        case "S":
            return CertKind.SALES;
        case "":
            return CertKind.NONE;
        default:
            throw new IllegalArgumentException(THE_SPECIFIED_STRING + technical
                    + "' is not a valid Certification kind.");
        }
    }

    private CertStatus getStatusFromString(final String status) {
        switch (status.toUpperCase(Locale.ENGLISH)) {
        case "ACTIVE":
            return CertStatus.ACTIVE;
        case "EXPIRING":
            return CertStatus.EXPIRING;
        case "EXPIRED":
            return CertStatus.EXPIRED;
        case "WITHDRAWN":
            return CertStatus.WITHDRAWN;
        case "NEW/ACTIVE":
            return CertStatus.NEW_ACTIVE;
        default:
            throw new IllegalArgumentException(THE_SPECIFIED_STRING + status + "' is not a valid status.");
        }
    }

    /**
     * Konvertiert mehrere {@code CertWebEntry}-Objekte in eine {@code Certification}-List.
     *
     * @param entries
     *            die Webeinräge einer Zertifizierung, der konvertiert werden sollen
     * @return die Liste von {@code Certification}-Objekten.
     */
    public List<Certification> convertMultipleEntries(final List<CertWebEntry> entries) {
        List<Certification> certs = new LinkedList<>();
        for (CertWebEntry entry : entries) {
            certs.add(convertToCertification(entry));
        }

        return certs;
    }

    /**
     * Konvertiert mehrere, in einer {@code Collection} vorhandene {@code CertWebEntry}-Objekte in
     * eine Multimap, wobei der Zertifizierungs-Code den Schlüssel bildet.
     *
     * @param entries
     *            die {@code CertWebEntry}-Objekte, die konvertiert werden sollen.
     * @return die {@code Multimap} aller {@code Certification}s.
     */
    public Multimap<String, Certification> convertMultipleEntriesToMap(final Collection<CertWebEntry> entries) {
        Multimap<String, Certification> convertedCerts = ArrayListMultimap.create();
        for (CertWebEntry entry : entries) {
            convertedCerts.put(entry.getCertificationCode(), convertToCertification(entry));
        }

        return convertedCerts;
    }
}

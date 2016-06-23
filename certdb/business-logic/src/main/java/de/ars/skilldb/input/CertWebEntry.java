package de.ars.skilldb.input;

import java.util.Objects;

/**
 * Datenobjekt, das eine aus dem Web ausgelesene Zertifizierung repräsentiert.
 */
public class CertWebEntry {

    private String status;
    private String sequenceNumber;
    private String name;
    private String link;
    private String technical;
    private String skillPoint;
    private String expirationDate;
    private String certificationCode;
    private String version;
    private String brand;
    private String productGroup;

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(final String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public String getTechnical() {
        return technical;
    }

    public void setTechnical(final String technical) {
        this.technical = technical;
    }

    public String getSkillPoint() {
        return skillPoint;
    }

    public void setSkillPoint(final String skillPoint) {
        this.skillPoint = skillPoint;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCertificationCode() {
        return certificationCode;
    }

    public void setCertificationCode(final String certificationCode) {
        this.certificationCode = certificationCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(final String productGroup) {
        this.productGroup = productGroup;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(status, sequenceNumber, name, link, technical, skillPoint, expirationDate,
                certificationCode, version, brand, productGroup);
    }

    //CHECKSTYLE:OFF
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof CertWebEntry)) { return false; }

        CertWebEntry other = (CertWebEntry) obj;
        if (!Objects.equals(status, other.status)) { return false; }
        if (!Objects.equals(sequenceNumber, other.sequenceNumber)) { return false; }
        if (!Objects.equals(name, other.name)) { return false; }
        if (!Objects.equals(link, other.link)) { return false; }
        if (!Objects.equals(technical, other.technical)) { return false; }
        if (!Objects.equals(skillPoint, other.skillPoint)) { return false; }
        if (!Objects.equals(expirationDate, other.expirationDate)) { return false; }
        if (!Objects.equals(certificationCode, other.certificationCode)) { return false; }
        if (!Objects.equals(version, other.version)) { return false; }
        if (!Objects.equals(brand, other.brand)) { return false; }
        if (!Objects.equals(productGroup, other.productGroup)) { return false; }

        return true;
    }
    //CHECKSTYLE:ON

    @Override
    public String toString() {
        return "CertWebEntry [status=" + status + ", sequenceNumber=" + sequenceNumber + ", name=" + name + ", link="
                + link + ", technical=" + technical + ", skillPoint=" + skillPoint + ", expirationDate="
                + expirationDate + ", certificationCode=" + certificationCode + ", version=" + version + ", brand="
                + brand + ", productGroup=" + productGroup + "]";
    }

}

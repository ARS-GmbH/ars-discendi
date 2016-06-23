package de.ars.skilldb.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.format.annotation.DateTimeFormat;

import de.ars.skilldb.domain.enums.CertKind;
import de.ars.skilldb.domain.enums.CertStatus;

@NodeEntity
public class Certification {

    @GraphId
    private Long id;

    private CertStatus status;
    private String sequenceNumber;

    @NotBlank(message = "Der Titel der Zertifizierung darf nicht leer sein.")
    @Indexed(unique = true)
    private String name;

    private String link;

    private CertKind kind;
    private Integer skillPoint;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date expirationDate;

    @NotBlank(message = "Der Zertifizierungscode darf nicht leer sein.")
    @Indexed
    private String certificationCode;

    private Integer version;

    @RelatedTo(type = "RELEVANT_FOR", direction = Direction.OUTGOING)
    private Set<ProductGroup> productGroups;

    @RelatedTo(type = "BELONGS_TO", direction = Direction.OUTGOING)
    private Set<Brand> brands;

    private String description;

    @RelatedTo(type = "PREDECESSOR", direction = Direction.OUTGOING)
    private Certification predecessor;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date lastModified;

    @RelatedTo(type = "DESTINATION_TO", direction = Direction.INCOMING)
    private Set<Path> paths;

    /**
     * Default-Konstruktor.
     */
    public Certification() {
        productGroups = new HashSet<>();
        brands = new HashSet<>();
        paths = new HashSet<>();
    }

    /**
     * Kopierkonstruktor. Erstellt eine flache Kopie einer {@code Certification}.
     *
     * @param certification
     *            die {@code Certification}, die kopiert werden soll.
     */
    public Certification(final Certification certification) {
        if (certification == null) {
            throw new IllegalArgumentException("The certification to copy must not be null.");
        }
        id = certification.getId();
        status = certification.status;
        sequenceNumber = certification.sequenceNumber;
        name = certification.name;
        link = certification.link;
        kind = certification.kind;
        skillPoint = certification.skillPoint;
        expirationDate = certification.expirationDate;
        certificationCode = certification.certificationCode;
        version = certification.version;
        productGroups = certification.productGroups;
        brands = certification.brands;
        description = certification.description;
        predecessor = certification.predecessor;
        lastModified = certification.lastModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public CertStatus getStatus() {
        return status;
    }

    public void setStatus(final CertStatus status) {
        this.status = status;
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

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(final String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public CertKind getKind() {
        return kind;
    }

    public void setKind(final CertKind kind) {
        this.kind = kind;
    }

    public Integer getSkillPoint() {
        return skillPoint;
    }

    public void setSkillPoint(final Integer skillPoint) {
        this.skillPoint = skillPoint;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCertificationCode() {
        return certificationCode;
    }

    public void setCertificationCode(final String certificationCode) {
        this.certificationCode = certificationCode;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(final Integer version) {
        this.version = version;
    }

    public Set<ProductGroup> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(final Set<ProductGroup> productGroups) {
        this.productGroups = productGroups;
    }

    public void addProductGroup(final ProductGroup productGroup) {
        productGroups.add(productGroup);
    }

    public Set<Brand> getBrands() {
        return brands;
    }

    public void setBrands(final Set<Brand> brands) {
        this.brands = brands;
    }

    public void addBrand(final Brand brand) {
        brands.add(brand);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Certification getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(final Certification predecessor) {
        this.predecessor = predecessor;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(final Date lastModified) {
        this.lastModified = lastModified;
    }

    public Set<Path> getPaths() {
        return paths;
    }

    public void setPaths(final Set<Path> paths) {
        this.paths = paths;
    }

    public void addPath(final Path path) {
        paths.add(path);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, status, sequenceNumber, name, link, kind, skillPoint,
                expirationDate, certificationCode, version, productGroups, brands,
                description, predecessor, paths);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == this) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof Certification)) { return false; }

        Certification other = (Certification) obj;
        if (!Objects.equals(id, other.id)) { return false; }
        if (!Objects.equals(status, other.status)) { return false; }
        if (!Objects.equals(sequenceNumber, other.sequenceNumber)) { return false; }
        if (!Objects.equals(name, other.name)) { return false; }
        if (!Objects.equals(link, other.link)) { return false; }
        if (!Objects.equals(kind, other.kind)) { return false; }
        if (!Objects.equals(skillPoint, other.skillPoint)) { return false; }
        if (!Objects.equals(expirationDate, other.expirationDate)) { return false; }
        if (!Objects.equals(certificationCode, other.certificationCode)) { return false; }
        if (!Objects.equals(version, other.version)) { return false; }
        if (!Objects.equals(productGroups, other.productGroups)) { return false; }
        if (!Objects.equals(brands, other.brands)) { return false; }
        if (!Objects.equals(description, other.description)) { return false; }
        if (!Objects.equals(predecessor, other.predecessor)) { return false; }
        if (!Objects.equals(paths, other.paths)) { return false; }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Certification [id=").append(id)
               .append(", status=").append(status)
               .append(", sequenceNumber=").append(sequenceNumber)
               .append(", name=").append(name)
               .append(", link=").append(link)
               .append(", kind=").append(kind)
               .append(", skillPoint=").append(skillPoint)
               .append(", expirationDate=").append(expirationDate)
               .append(", certificationCode=").append(certificationCode)
               .append(", version=").append(version)
               .append(", productGroups=").append(productGroups)
               .append(", brands=").append(brands)
               .append(", description=").append(description)
               .append(", predecessor=").append(predecessor)
               .append(", lastModified=").append(lastModified)
               .append(", paths").append(paths)
               .append("]");
        return builder.toString();
    }
}

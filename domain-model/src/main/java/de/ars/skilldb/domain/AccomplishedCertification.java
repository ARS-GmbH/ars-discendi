/**
 *
 */
package de.ars.skilldb.domain;

import java.util.Date;
import java.util.Objects;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Repräsentiert eine bestandene Zertifizierung.
 */
@NodeEntity
public class AccomplishedCertification {

    @GraphId
    private Long id;
    private Date carryOutDate;

    @RelatedTo(type = "ORIGINATES_FROM", direction = Direction.OUTGOING)
    private Certification certification;

    @RelatedTo(type = "OWNS", direction = Direction.INCOMING)
    private User user;

    private String documentId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getCarryOutDate() {
        return carryOutDate;
    }

    public void setCarryOutDate(final Date carryOutDate) {
        this.carryOutDate = carryOutDate;
    }

    public Certification getCertification() {
        return certification;
    }

    public void setCertification(final Certification certification) {
        this.certification = certification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(final String documentId) {
        this.documentId = documentId;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, carryOutDate, certification, user, documentId);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof AccomplishedCertification)) { return false; }

        AccomplishedCertification other = (AccomplishedCertification) obj;
        if (!Objects.equals(id, other.id)) { return false; }
        if (!Objects.equals(carryOutDate, other.carryOutDate)) { return false; }
        if (!Objects.equals(certification, other.certification)) { return false; }
        if (!Objects.equals(user, other.user)) { return false; }
        if (!Objects.equals(documentId, other.documentId)) { return false; }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AccomplishedCertification [id=").append(id)
                .append(", carryOutDate=").append(carryOutDate)
                .append(", certification=").append(certification)
                .append(", user=").append(user)
                .append(", documentId=").append(documentId)
                .append("]");
        return builder.toString();
    }

}

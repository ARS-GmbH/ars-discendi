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
 * Repräsentiert eine für einen Angestellten geplante Zertifizierung.
 *
 */
@NodeEntity
public class PlannedCertification {

    @GraphId
    private Long id;

    @RelatedTo(type="PLANNED_FOR", direction=Direction.OUTGOING)
    private Path path;

    @RelatedTo(type="SHOULD_COMPLETE", direction=Direction.INCOMING)
    private User user;

    private Date carryOutUntil;

    private String plannerUserName;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(final Path path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Date getCarryOutUntil() {
        return carryOutUntil;
    }

    public void setCarryOutUntil(final Date carryOutUntil) {
        this.carryOutUntil = carryOutUntil;
    }


    public String getPlannerUserName() {
        return plannerUserName;
    }

    public void setPlannerUserName(final String plannerUserName) {
        this.plannerUserName = plannerUserName;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, path, user, carryOutUntil, plannerUserName);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof PlannedCertification)) { return false; }

        PlannedCertification other = (PlannedCertification) obj;
        if (!Objects.equals(id, other.id)) { return false; }
        if (!Objects.equals(path, other.path)) { return false; }
        if (!Objects.equals(user, other.user)) { return false; }
        if (!Objects.equals(carryOutUntil, other.carryOutUntil)) { return false; }
        if (!Objects.equals(plannerUserName, other.plannerUserName)) { return false; }

        return true;
    }

}

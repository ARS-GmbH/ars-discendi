/**
 *
 */
package de.ars.skilldb.domain;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Repräsentiert eine Mitarbeit an einem Projekt.
 */
@NodeEntity
public class ProjectParticipation extends Proof {

    @NotNull(message = "Es muss ein Mitarbeiter angegeben werden.")
    @RelatedTo(type="DID", direction=Direction.INCOMING)
    private User user;

    @NotNull(message = "Das Projekt muss angegeben werden.")
    @RelatedTo(type="BELONGS_TO", direction=Direction.OUTGOING)
    @Fetch
    private Project project;

    @Range(min=1)
    private int duration;

    public ProjectParticipation() {
    }

    public ProjectParticipation(final User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(final Project project) {
        this.project = project;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProjectParticipation [id=").append(id)
            .append(", user=").append(user)
            .append(", project=").append(project)
            .append(", duration=").append(duration)
            .append("]");
        return builder.toString();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ProjectParticipation)) {
            return false;
        }

        ProjectParticipation other = (ProjectParticipation) obj;
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (!Objects.equals(user, other.user)) {
            return false;
        }
        if (!Objects.equals(project, other.project)) {
            return false;
        }
        if (!Objects.equals(duration, other.duration)) {
            return false;
        }
        if (!Objects.equals(created, other.created)) {
            return false;
        }
        if (!Objects.equals(lastModified, other.lastModified)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, project, duration, created, lastModified);
    }

    @Override
    public String getTitle() {
        return project.getName();
    }
}

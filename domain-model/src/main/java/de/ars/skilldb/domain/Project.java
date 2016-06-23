/**
 *
 */
package de.ars.skilldb.domain;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Repräsentiert ein Projekt.
 */
@NodeEntity
public class Project {

    @GraphId
    private Long id;

    @NotBlank(message = "Der Projektname darf nicht leer sein.")
    private String name;

    @NotNull(message = "Der Kunde darf nicht leer sein.")
    @RelatedTo(type = "RELATED_TO", direction = Direction.OUTGOING)
    @Fetch
    private Customer customer;

    @RelatedTo(type = "WORKED_ON", direction = Direction.INCOMING)
    private Set<User> users;

    @RelatedTo(type = "INCLUDES", direction = Direction.OUTGOING)
    private Set<Knowledge> knowledges;

    @NotNull(message = "Das Startdatum darf nicht leer sein.")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date start;

    @NotNull(message = "Das Enddatum darf nicht leer sein.")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date end;

    private String shortDescription;

    private String longDescription;

    @DateTimeFormat(pattern = "dd.MM.yyyy kk:mm")
    private Date created;

    @DateTimeFormat(pattern = "dd.MM.yyyy kk:mm")
    private Date lastModified;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(final Set<User> users) {
        this.users = users;
    }

    public Set<Knowledge> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(final Set<Knowledge> knowledges) {
        this.knowledges = knowledges;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(final Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(final Date end) {
        this.end = end;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(final String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(final String longDescription) {
        this.longDescription = longDescription;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(final Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Project [id=").append(id)
            .append(", name=").append(name)
            .append(", customer=").append(customer == null ? "null" : customer.toString())
            .append(", start=").append(start == null ? "null" : start.toString())
            .append(", end=").append(end == null ? "null" : end.toString())
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
        if (!(obj instanceof Project)) {
            return false;
        }

        Project other = (Project) obj;
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (!Objects.equals(name, other.name)) {
            return false;
        }
        if (!Objects.equals(customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(users, other.users)) {
            return false;
        }
        if (!Objects.equals(knowledges, other.knowledges)) {
            return false;
        }
        if (!Objects.equals(start, other.end)) {
            return false;
        }
        if (!Objects.equals(shortDescription, other.shortDescription)) {
            return false;
        }
        if (!Objects.equals(longDescription, other.longDescription)) {
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
        return Objects.hash(id, name, customer, users, knowledges, start, end, shortDescription, longDescription, created,
                lastModified);
    }
}

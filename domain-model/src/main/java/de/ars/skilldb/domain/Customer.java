/**
 *
 */
package de.ars.skilldb.domain;

import java.util.Date;
import java.util.Objects;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Repräsentiert einen Kunden.
 */
@NodeEntity
public class Customer {

    @GraphId
    private Long id;

    @NotBlank(message="Der Name darf nicht leer sein.")
    private String name;

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
        builder.append("Customer [id=").append(id)
            .append(", name=").append(name)
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
        if (!(obj instanceof Customer)) {
            return false;
        }

        Customer other = (Customer) obj;
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (!Objects.equals(name, other.name)) {
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
        return Objects.hash(id, name, created, lastModified);
    }
}

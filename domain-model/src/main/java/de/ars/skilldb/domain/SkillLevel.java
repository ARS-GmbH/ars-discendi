/**
 *
 */
package de.ars.skilldb.domain;

import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Repräsentiert ein Skill-Level.
 */
@NodeEntity
public class SkillLevel {

    @GraphId
    private Long id;

    @NotNull()
    @Range(min = 0, max = 100, message = "Der interne Wert muss zwischen 0 und 100 liegen.")
    @Indexed(unique=true)
    private Integer internalValue;

    @NotBlank(message="Die Bezeichnung darf nicht leer sein.")
    private String name;

    private String description;

    private String tags;

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

    public Integer getInternalValue() {
        return internalValue;
    }

    public void setInternalValue(final int internalValue) {
        this.internalValue = internalValue;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(final String tags) {
        this.tags = tags;
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
        builder.append("SkillLevel [id=").append(id)
            .append(", internalValue=").append(internalValue)
            .append(", name=").append(name)
            .append(", description=").append(description)
            .append(", tags=").append(tags)
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
        if (!(obj instanceof SkillLevel)) {
            return false;
        }

        SkillLevel other = (SkillLevel) obj;
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (!Objects.equals(internalValue, other.internalValue)) {
            return false;
        }
        if (!Objects.equals(name, other.name)) {
            return false;
        }
        if (!Objects.equals(description, other.description)) {
            return false;
        }
        if (!Objects.equals(tags, other.tags)) {
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
        return Objects.hash(id, internalValue, name, description, tags, created, lastModified);
    }
}

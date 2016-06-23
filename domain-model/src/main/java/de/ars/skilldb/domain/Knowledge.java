package de.ars.skilldb.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Repräsentiert ein Wissensgebiet.
 */
@NodeEntity
public class Knowledge {

    @GraphId
    private Long id;

    @NotBlank(message = "Der Name des Wissensgebiets darf nicht leer sein.")
    @Indexed(unique = true)
    private String name;

    private String description;

    @NotEmpty(message = "Das Wissensgebiet muss mindestens einer Kategorie zugeordnet werden.")
    @RelatedTo(type = "BELONGS_TO", direction = Direction.OUTGOING)
    private Set<KnowledgeCategory> categories;

    @DateTimeFormat(pattern = "dd.MM.yyyy kk:mm")
    private Date created;

    @DateTimeFormat(pattern = "dd.MM.yyyy kk:mm")
    private Date lastModified;

    public Knowledge() {
        categories = new HashSet<KnowledgeCategory>();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<KnowledgeCategory> getCategories() {
        return categories;
    }

    public void setCategories(final Set<KnowledgeCategory> categories) {
        this.categories = categories;
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
        builder.append("Knwoledge [id=").append(id)
            .append(", name=").append(name)
            .append(", description=").append(description)
            .append(", KnowledgeCategory=").append(generateKnowledgeCategoryString())
            .append("]");
        return builder.toString();
    }

    private String generateKnowledgeCategoryString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
//        for (KnowledgeCategory category : categories) {
//            builder.append("id=").append(category.getId())
//            .append(", name=").append(category.getName())
//            .append(",");
//        }
        builder.append("]");

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
        if (!(obj instanceof Knowledge)) {
            return false;
        }

        Knowledge other = (Knowledge) obj;
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (!Objects.equals(name, other.name)) {
            return false;
        }
        if (!Objects.equals(description, other.description)) {
            return false;
        }
        if (!Objects.equals(categories, other.categories)) {
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
        return Objects.hash(id, name, description, categories, created, lastModified);
    }

}

package de.ars.skilldb.domain;

import java.util.Date;
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
 * Repräsentiert eine Wissensgebiets-Kategorie.
 */
@NodeEntity
public class KnowledgeCategory {

    @GraphId
    private Long id;

    @NotBlank(message = "Der Name darf nicht leer sein.")
    @Indexed(unique = true)
    private String name;

    private String description;

    @NotEmpty(message = "Der Kategorie muss mindestens eine Elternkategorie zugeordnet werden.")
    @RelatedTo(type = "HAS_PARENT", direction = Direction.OUTGOING)
    private Set<KnowledgeCategory> parents;

    @RelatedTo(type = "HAS_PARENT", direction = Direction.INCOMING)
    private Set<KnowledgeCategory> childrenCategories;

    @RelatedTo(type = "BELONGS_TO", direction = Direction.INCOMING)
    private Set<Knowledge> childrenKnowledges;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<KnowledgeCategory> getParents() {
        return parents;
    }

    public void setParents(final Set<KnowledgeCategory> parent) {
        parents = parent;
    }

    public Set<KnowledgeCategory> getChildrenCategories() {
        return childrenCategories;
    }

    public void setChildrenCategories(final Set<KnowledgeCategory> childrenCategories) {
        this.childrenCategories = childrenCategories;
    }

    public Set<Knowledge> getChildrenKnowledges() {
        return childrenKnowledges;
    }

    public void setChildrenKnowledges(final Set<Knowledge> childrenKnowledges) {
        this.childrenKnowledges = childrenKnowledges;
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
        builder.append("KnowledgeCategory [id=").append(id)
            .append(", name=").append(name)
            .append(", description=").append(description)
            .append(", parents=").append(generateParentString())
            .append("]");
        return builder.toString();
    }

    private String generateParentString() {
        if (parents == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder();
        for (KnowledgeCategory category : parents) {
            builder.append("[id=").append(category.getId() == null ? "null" : category.getId()).append(", name=")
                    .append(category.getName() == null ? "null" : category.getName()).append("]");
        }
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
        if (!(obj instanceof KnowledgeCategory)) {
            return false;
        }

        KnowledgeCategory other = (KnowledgeCategory) obj;
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (!Objects.equals(name, other.name)) {
            return false;
        }
        if (!Objects.equals(description, other.description)) {
            return false;
        }
        if (!Objects.equals(parents, other.parents)) {
            return false;
        }
        if (!Objects.equals(childrenCategories, other.childrenCategories)) {
            return false;
        }
        if (!Objects.equals(childrenKnowledges, other.childrenKnowledges)) {
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
        return Objects.hash(id, name, description, parents, childrenCategories, childrenKnowledges, created, lastModified);
    }
}

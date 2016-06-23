/**
 *
 */
package de.ars.skilldb.domain;

import java.util.Date;
import java.util.Set;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Abstrakte Oberklasse aller Nachweise.
 */
@NodeEntity
public abstract class Proof {

    @GraphId
    protected Long id;

    @RelatedToVia(type = "PROVES")
    protected Set<SkillProofEdge> skillProofEdges;

    @DateTimeFormat(pattern = "dd.MM.yyyy kk:mm")
    protected Date created;

    @DateTimeFormat(pattern = "dd.MM.yyyy kk:mm")
    protected Date lastModified;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Set<SkillProofEdge> getSkillProofEdges() {
        return skillProofEdges;
    }

    public void setSkillProofEdges(final Set<SkillProofEdge> skillProofEdges) {
        this.skillProofEdges = skillProofEdges;
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

    public abstract String toString();

    public abstract boolean equals(Object obj);

    public abstract int hashCode();

    public String getType(){
        return this.getClass().getSimpleName();
    }

    public String getSubUrl(){
        return this.getClass().getSimpleName().toLowerCase();
    }

    public abstract String  getTitle();

}

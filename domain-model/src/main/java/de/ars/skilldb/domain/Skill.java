package de.ars.skilldb.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.format.annotation.DateTimeFormat;

import de.ars.skilldb.domain.enums.SkillStatus;

/**
 * Repräsentiert einen Skill.
 */
@NodeEntity
public class Skill {

    @GraphId
    private Long id;

    @NotNull(message = "Das Wissensgebiet des Skills darf nicht leer sein.")
    @RelatedTo(type = "BASE_KNOWLEDGE", direction = Direction.OUTGOING)
    @Fetch
    private Knowledge knowledge;

    @NotNull(message = "Der User des Skills darf nicht leer sein.")
    @RelatedTo(type = "CAN", direction = Direction.INCOMING)
    private User user;

    @RelatedTo(type = "HAS_LEVEL", direction = Direction.OUTGOING)
    @Fetch
    private SkillLevel skillLevel;

    @RelatedTo(type = "SUBMITTED_LEVEL", direction = Direction.OUTGOING)
    @Fetch
    private SkillLevel submittedSkillLevel;

    @RelatedTo(type = "REJECTED_LEVEL", direction = Direction.OUTGOING)
    @Fetch
    private SkillLevel rejectedSkillLevel;

    private String rejectionReason;

    @RelatedTo(type = "HAD_LEVEL", direction = Direction.OUTGOING)
    private Set<SkillLevel> previousSkillLevel;

    @RelatedToVia(type = "PROVES")
    protected Set<SkillProofEdge> skillProofEdges;

    private SkillStatus status;

    @DateTimeFormat(pattern = "dd.MM.yyyy kk:mm")
    private Date created;

    @DateTimeFormat(pattern = "dd.MM.yyyy kk:mm")
    private Date lastModified;

    public Skill() {
        previousSkillLevel = new HashSet<SkillLevel>();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(final Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(final SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    public SkillLevel getSubmittedSkillLevel() {
        return submittedSkillLevel;
    }

    public void setSubmittedSkillLevel(final SkillLevel submittedSkillLevel) {
        this.submittedSkillLevel = submittedSkillLevel;
    }

    public SkillLevel getRejectedSkillLevel() {
        return rejectedSkillLevel;
    }

    public void setRejectedSkillLevel(final SkillLevel rejectedSkillLevel) {
        this.rejectedSkillLevel = rejectedSkillLevel;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(final String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Set<SkillLevel> getPreviousSkillLevel() {
        return previousSkillLevel;
    }

    public void setPreviousSkillLevel(final Set<SkillLevel> skillLevel) {
        previousSkillLevel = skillLevel;
    }

    public Set<SkillProofEdge> getSkillProofEdges() {
        return skillProofEdges;
    }

    public void setSkillProofEdges(final Set<SkillProofEdge> skillProofEdges) {
        this.skillProofEdges = skillProofEdges;
    }

    public SkillStatus getStatus() {
        return status;
    }

    public void setStatus(final SkillStatus status) {
        this.status = status;
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
        builder.append("Skill [id=").append(id)
            .append(", knowledge=").append(knowledge.getId()).append(" ").append(knowledge.getName())
            .append(", user=").append(user.getUserName())
            .append(", skillLevel=").append(skillLevel.getInternalValue())
            .append(", submittedLevel=").append(submittedSkillLevel == null ? "null" : submittedSkillLevel.getInternalValue())
            .append(", rejectedLevel=").append(rejectedSkillLevel == null ? "null" : rejectedSkillLevel.getInternalValue())
            .append(", prevSkillLevel=").append(prevSkillLevelToString())
            .append(", status=").append(status.getName())
            .append("]");
        return builder.toString();
    }

    private String prevSkillLevelToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (SkillLevel level : previousSkillLevel) {
            builder.append("'").append(level.getInternalValue())
            .append(" ").append(level.getName()).append("', ");
        }
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
        if (!(obj instanceof Skill)) {
            return false;
        }

        Skill other = (Skill) obj;
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (!Objects.equals(knowledge, other.knowledge)) {
            return false;
        }
        if (!Objects.equals(user, other.user)) {
            return false;
        }
        if (!Objects.equals(skillLevel, other.skillLevel)) {
            return false;
        }
        if (!Objects.equals(submittedSkillLevel, other.submittedSkillLevel)) {
            return false;
        }
        if (!Objects.equals(rejectedSkillLevel, other.rejectedSkillLevel)) {
            return false;
        }
        if (!Objects.equals(rejectionReason, other.rejectionReason)) {
            return false;
        }
        if (!Objects.equals(previousSkillLevel, other.previousSkillLevel)) {
            return false;
        }
        if (!Objects.equals(skillProofEdges, other.skillProofEdges)) {
            return false;
        }
        if (!Objects.equals(status, other.status)) {
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
        return Objects.hash(id, knowledge, user, skillLevel, submittedSkillLevel, rejectedSkillLevel, rejectionReason, previousSkillLevel, skillProofEdges, status, created, lastModified);
    }
}

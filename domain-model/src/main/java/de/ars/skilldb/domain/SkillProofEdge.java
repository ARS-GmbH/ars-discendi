/**
 *
 */
package de.ars.skilldb.domain;

import java.util.Objects;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * Repräsentiert eine Kante von einem Nachweis zu einem Skill.
 */
@RelationshipEntity(type = "PROVES")
public class SkillProofEdge {

    @GraphId
    private Long id;

    @StartNode
    @Fetch
    private Proof proof;

    @EndNode
    @Fetch
    private Skill skill;

    @Range(min=0)
    private int hours;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Proof getProof() {
        return proof;
    }

    public void setProof(final Proof proof) {
        this.proof = proof;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(final Skill skill) {
        this.skill = skill;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(final int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SkillProofEdge [id=").append(id)
                .append(", proof=").append(proof)
                .append(", skill=").append(skill)
                .append(", hours=").append(hours)
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
        if (!(obj instanceof SkillProofEdge)) {
            return false;
        }
        SkillProofEdge other = (SkillProofEdge) obj;
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (!Objects.equals(proof, other.proof)) {
            return false;
        }
        if (!Objects.equals(skill, other.skill)) {
            return false;
        }
        if (!Objects.equals(hours, other.hours)) {
            return false;
        }

        return true;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, proof, skill, hours);
    }

}

/**
 *
 */
package de.ars.skilldb.domain;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.neo4j.annotation.*;

/**
 * Repräsentiert eine Kante zu einem Test ({@code Exam}), die bestanden wurde.
 */
@RelationshipEntity(type = "SHOULD_MAKE")
public class PlannedExam {

    @GraphId
    private Long id;

    private Date carryOutUntil;

    @EndNode
    private Exam exam;

    @StartNode
    private User user;

    private String plannerUserName;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getCarryOutUntil() {
        return carryOutUntil;
    }

    public void setCarryOutUntil(final Date carryOutUntil) {
        this.carryOutUntil = carryOutUntil;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(final Exam exam) {
        this.exam = exam;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getPlanner() {
        return plannerUserName;
    }

    public void setPlanner(final String plannerUserName) {
        this.plannerUserName = plannerUserName;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, carryOutUntil, exam, user, plannerUserName);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof PlannedExam)) { return false; }

        PlannedExam other = (PlannedExam) obj;
        if (!Objects.equals(id, other.id)) { return false; }
        if (!Objects.equals(carryOutUntil, other.carryOutUntil)) { return false; }
        if (!Objects.equals(exam, other.exam)) { return false; }
        if (!Objects.equals(user, other.user)) { return false; }
        if (!Objects.equals(plannerUserName, other.plannerUserName)) { return false; }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PlannedExam [id=").append(id)
                .append(", carryOutUntil=").append(carryOutUntil)
                .append(", exam=").append(exam.getId())
                .append(", user=").append(user)
                .append(", planner=").append(plannerUserName)
                .append("]");
        return builder.toString();
    }

}

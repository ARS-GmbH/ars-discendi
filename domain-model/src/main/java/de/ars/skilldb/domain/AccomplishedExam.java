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
@RelationshipEntity(type = "ACCOMPLISHED")
public class AccomplishedExam {

    @GraphId
    private Long id;
    private Date carryOutDate;

    @EndNode
    private Exam exam;

    @StartNode
    private User user;

    private String examDocumentId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getCarryOutDate() {
        return carryOutDate;
    }

    public void setCarryOutDate(final Date carryOutDate) {
        this.carryOutDate = carryOutDate;
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

    public String getExamDocumentId() {
        return examDocumentId;
    }

    public void setExamDocumentId(final String examDocument) {
        examDocumentId = examDocument;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, carryOutDate, exam, user, examDocumentId);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof AccomplishedExam)) { return false; }

        AccomplishedExam other = (AccomplishedExam) obj;
        if (!Objects.equals(id, other.id)) { return false; }
        if (!Objects.equals(carryOutDate, other.carryOutDate)) { return false; }
        if (!Objects.equals(exam, other.exam)) { return false; }
        if (!Objects.equals(user, other.user)) { return false; }
        if (!Objects.equals(examDocumentId, other.examDocumentId)) { return false; }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AccomplishedExam [id=").append(id)
                .append(", carryOutDate=").append(carryOutDate)
                .append(", exam=").append(exam)
                .append(", user=").append(user)
                .append(", examDocument=").append(examDocumentId)
                .append("]");

        return builder.toString();
    }

}

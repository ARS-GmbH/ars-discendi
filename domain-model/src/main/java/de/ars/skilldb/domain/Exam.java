package de.ars.skilldb.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

/**
 * Repräsentiert einen Test, der zum Erwerb einer Zertifizierung notwendig ist.
 */
@NodeEntity
public class Exam {

    @GraphId
    private Long id;

    @NotBlank(message = "Die Nummer des Tests darf nicht leer sein.")
    @Indexed(unique = true)
    private String number;

    @NotNull(message = "Der Titel des Tests darf nicht leer sein.")
    private String title;

    @NotNull(message = "Die Dauer des Tests darf nicht leer sein.")
    private Integer testDuration;

    private Integer numberOfQuestions;

    @NotNull(message = "Der Passing Score des Tests darf nicht leer sein.")
    private Integer passingScorePercentage;

    @RelatedTo(type = "NECESSARY_FOR", direction = Direction.OUTGOING)
    private Set<Certification> certifications;

    @RelatedToVia(type = "ACCOMPLISHED")
    private Set<AccomplishedExam> accomplished;

    @RelatedToVia(type = "SHOULD_MAKE")
    private Set<PlannedExam> planned;

    public Exam() {
        certifications = new HashSet<Certification>();
        accomplished = new HashSet<AccomplishedExam>();
        planned = new HashSet<PlannedExam>();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Integer getTestDuration() {
        return testDuration;
    }

    public void setTestDuration(final Integer testDuration) {
        this.testDuration = testDuration;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(final Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Integer getPassingScorePercentage() {
        return passingScorePercentage;
    }

    public void setPassingScorePercentage(final Integer passingScorePercentage) {
        this.passingScorePercentage = passingScorePercentage;
    }

    public Set<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(final Set<Certification> certifications) {
        this.certifications = certifications;
    }

    /**
     * Fügt eine neue {@code Certification} hinzu.
     *
     * @param certification
     *            die {@code Certification}, die hinzugefügt werden soll.
     */
    public void addCertification(final Certification certification) {
        certifications.add(certification);
    }

    public Set<AccomplishedExam> getAccomplished() {
        return accomplished;
    }

    public void setAccomplished(final Set<AccomplishedExam> accomplished) {
        this.accomplished = accomplished;
    }

    /**
     * Fügt eine neue Verbindung zu einem Benutzer hinzu, der den Test abgeschlossen hat.
     *
     * @param accomplishedExam
     *            die Verbindung zu einem Benutzer.
     */
    public void addAccomplished(final AccomplishedExam accomplishedExam) {
        accomplished.add(accomplishedExam);
    }

    public Set<PlannedExam> getPlanned() {
        return planned;
    }

    public void setPlanned(final Set<PlannedExam> planned) {
        this.planned = planned;
    }

    /**
     * Fügt eine neue Verbindung zu einem Benutzer hinzu, für den ein Test geplant ist.
     *
     * @param plannedExam
     *            die Verbindung zu einem Benutzer.
     */
    public void addPlanned(final PlannedExam plannedExam) {
        planned.add(plannedExam);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, number, title, testDuration, numberOfQuestions, passingScorePercentage, certifications,
                accomplished, planned);
    }

    // CHECKSTYLE:OFF
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof Exam)) { return false; }

        Exam other = (Exam) obj;
        if (!Objects.equals(id, other.id)) { return false; }
        if (!Objects.equals(number, other.number)) { return false; }
        if (!Objects.equals(title, other.title)) { return false; }
        if (!Objects.equals(testDuration, other.testDuration)) { return false; }
        if (!Objects.equals(numberOfQuestions, other.numberOfQuestions)) { return false; }
        if (!Objects.equals(passingScorePercentage, other.passingScorePercentage)) { return false; }
        if (!Objects.equals(certifications, other.certifications)) { return false; }
        if (!Objects.equals(accomplished, other.accomplished)) { return false; }
        if (!Objects.equals(planned, other.planned)) { return false; }

        return true;
    } // CHECKSTYLE:ON

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Exam [id=").append(id)
                .append(", number=").append(number)
                .append(", title=").append(title)
                .append(", testDuration=").append(testDuration)
                .append(", numberOfQuestions=").append(numberOfQuestions)
                .append(", passingScorePercentage=").append(passingScorePercentage)
                .append(", certifications=").append(certifications)
                .append(", accomplished=").append(accomplished)
                .append(", planned=").append(planned)
                .append("]");

        return builder.toString();
    }

}
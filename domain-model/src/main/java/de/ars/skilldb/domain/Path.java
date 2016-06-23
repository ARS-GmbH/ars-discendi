/**
 *
 */
package de.ars.skilldb.domain;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Repräsentiert einen Pfad, der zum Erwerb einer Zertifizierung führt.
 */
@NodeEntity
public class Path implements Comparable<Path> {

    @GraphId
    private Long id;
    private Integer sequenceNumber;

    @RelatedTo(type="DESTINATION_TO", direction=Direction.OUTGOING)
    private Certification destination;

    @RelatedTo(type="NECESSARY_CERTIFICATION", direction=Direction.INCOMING)
    private Set<Certification> necessaryCertifications;

    @RelatedTo(type="NECESSARY_EXAM", direction=Direction.INCOMING)
    private Set<Exam> necessaryExams;

    @RelatedTo(type="CHOOSABLE_EXAM", direction=Direction.INCOMING)
    private Set<Exam> choosableExams;

    public Path() {
        this(0);
    }

    public Path(final int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        necessaryCertifications = new LinkedHashSet<>();
        necessaryExams = new LinkedHashSet<>();
        choosableExams = new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(final Integer sequenceNumber) {
        if (sequenceNumber == null) {
            throw new IllegalArgumentException("The sequence number must not be empty or null.");
        }
        this.sequenceNumber = sequenceNumber;
    }

    public Certification getDestination() {
        return destination;
    }

    public void setDestination(final Certification destination) {
        this.destination = destination;
    }

    public Set<Certification> getNecessaryCertifications() {
        return necessaryCertifications;
    }

    public void setNecessaryCertifications(final Set<Certification> necessaryCertifications) {
        if (necessaryCertifications == null) {
            this.necessaryCertifications = new LinkedHashSet<>();
        }
        else {
            this.necessaryCertifications = necessaryCertifications;
        }
    }

    /**
     * Fügt eine neue, notwendige {@code Certification} hinzu.
     *
     * @param certification
     *            die {@code Certification}, die hinzugefügt werden soll.
     */
    public void addNecessaryCertification(final Certification certification) {
        necessaryCertifications.add(certification);
    }

    public Set<Exam> getNecessaryExams() {
        return necessaryExams;
    }

    public void setNecessaryExams(final Set<Exam> necessaryExams) {
        if (necessaryExams == null) {
            this.necessaryExams = new LinkedHashSet<>();
        }
        else {
            this.necessaryExams = necessaryExams;
        }
    }

    /**
     * Fügt eine neue, notwendige {@code Exam} hinzu.
     *
     * @param exam
     *            die {@code Exam}, die hinzugefügt werden soll.
     */
    public void addNecessaryExam(final Exam exam) {
        necessaryExams.add(exam);
    }

    public Set<Exam> getChoosableExams() {
        return choosableExams;
    }

    public void setChoosableExams(final Set<Exam> choosableExams) {
        if (choosableExams == null) {
            this.choosableExams = new LinkedHashSet<>();
        }
        else {
            this.choosableExams = choosableExams;
        }
    }

    /**
     * Fügt eine neue, wählbare {@code Exam} hinzu.
     *
     * @param exam
     *            die {@code Exam}, die hinzugefügt werden soll.
     */
    public void addChoosableExam(final Exam exam) {
        choosableExams.add(exam);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, sequenceNumber, destination, necessaryCertifications, necessaryExams, choosableExams);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof Path)) { return false; }

        Path other = (Path) obj;
        if (!Objects.equals(id, other.id)) { return false; }
        if (!Objects.equals(sequenceNumber, other.sequenceNumber)) { return false; }
        if (!Objects.equals(destination, other.destination)) { return false; }
        if (!Objects.equals(necessaryCertifications, other.necessaryCertifications)) { return false; }
        if (!Objects.equals(necessaryExams, other.necessaryExams)) { return false; }
        if (!Objects.equals(choosableExams, other.choosableExams)) { return false; }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Path [id=").append(id)
                .append(", sequenceNumber=").append(sequenceNumber)
                .append(", destination=").append(destination)
                .append(", necessaryCertifications=").append(necessaryCertifications)
                .append(", necessaryExams=").append(necessaryExams)
                .append(", choosableExams=").append(choosableExams)
                .append("]");

        return builder.toString();
    }

    @Override
    public int compareTo(final Path other) {
        if (sequenceNumber < other.sequenceNumber) { return -1; }
        if (sequenceNumber.equals(other.sequenceNumber)) { return 0; }
        if (sequenceNumber > other.sequenceNumber) { return 1; }

        throw new IllegalArgumentException("Cannot compare two Paths.");
    }

}

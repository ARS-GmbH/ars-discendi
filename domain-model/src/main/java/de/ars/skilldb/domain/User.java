package de.ars.skilldb.domain;

import java.util.Collection;
import java.util.Objects;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Repräsentiert einen Benutzer.
 */
@NodeEntity
public class User {

    @GraphId
    private Long id;

    @Indexed(unique = true)
    private String userName;

    private String firstName;
    private String lastName;
    private String email;

    @RelatedTo(type = "CAN", direction = Direction.OUTGOING)
    private Collection<Skill> skills;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFullName() {
        if (firstName == null) {
            return lastName;
        }
        if (lastName == null) {
            return firstName;
        }
        return firstName + " " + lastName;
    }

    public Collection<Skill> getSkills() {
        return skills;
    }

    public void setSkills(final Collection<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, userName, firstName, lastName, email, skills);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof User)) { return false; }

        User other = (User) obj;
        if (!Objects.equals(id, other.id)) { return false; }
        if (!Objects.equals(userName, other.userName)) { return false; }
        if (!Objects.equals(firstName, other.firstName)) { return false; }
        if (!Objects.equals(lastName, other.lastName)) { return false; }
        if (!Objects.equals(email, other.email)) { return false; }
        if (!Objects.equals(skills, other.skills)) { return false; }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [id=").append(id)
               .append(", userName=").append(userName)
               .append(", firstName=").append(firstName)
               .append(", lastName=").append(lastName)
               .append(", email=").append(email)
               .append(", skills=").append(skills != null ? skillsToString() : "")
               .append("]");
        return builder.toString();
    }

    private String skillsToString() {
        StringBuilder skillsString = new StringBuilder();
        skillsString.append("[");
        for (Skill skill : skills){
            skillsString.append(skill.getId()).append(", ");
        }
        skillsString.append("]");
        return skillsString.toString();
    }

}

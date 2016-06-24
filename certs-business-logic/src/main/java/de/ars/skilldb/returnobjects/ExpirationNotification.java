/**
 *
 */
package de.ars.skilldb.returnobjects;

import java.util.List;
import java.util.Objects;

import de.ars.skilldb.domain.AccomplishedCertification;
import de.ars.skilldb.domain.User;

/**
 * Repräsentiert eine Erinnerung an einen Benutzer, dass einige der eigenen Zertifizierungen bald
 * ablaufen.
 */
public class ExpirationNotification {

    private final User user;
    private final List<AccomplishedCertification> accomplished;

    /**
     * Erstellt eine neue {@code ExpirationNotification}.
     *
     * @param user
     *            der Benutzer, dessen Zertifizierungen bald ablaufen.
     * @param accomplished
     *            die abgeschlossenen Zertifzierunge, die bald ablaufen.
     */
    public ExpirationNotification(final User user, final List<AccomplishedCertification> accomplished) {
        this.user = user;
        this.accomplished = accomplished;
    }

    public User getUser() {
        return user;
    }

    public List<AccomplishedCertification> getAccomplished() {
        return accomplished;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(user, accomplished);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof ExpirationNotification)) { return false; }

        ExpirationNotification other = (ExpirationNotification) obj;
        if (!Objects.equals(user, other.user)) { return false; }
        if (!Objects.equals(accomplished, other.accomplished)) { return false; }

        return true;
    }
}

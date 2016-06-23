/**
 *
 */
package de.ars.skilldb.schedule;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.AccomplishedCertificationInteractor;
import de.ars.skilldb.interactor.UserInteractor;
import de.ars.skilldb.returnobjects.ExpirationNotification;

/**
 * Prüft die vorhandenen, abgelegten Zertifizierungen auf baldiges Ablaufen.
 */
public class CertificationReminder implements Runnable {

    @Autowired
    private AccomplishedCertificationInteractor accomCertificationInteractor;

    @Autowired
    private UserInteractor userInteractor;

    private final Set<ExpirationSubscriber> subscribers;

    /**
     * Erzeugt einen neuen {@link CertificationReminder}.
     *
     * @param subscribers
     *            die {@link ExpirationSubscriber}s, die benachrichtigt werden sollen.
     */
    public CertificationReminder(final Set<ExpirationSubscriber> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public void run() {
        List<ExpirationNotification> notifications = new ArrayList<>();
        Collection<User> allUsers = userInteractor.findAll();
        for (User user : allUsers) {
            notifications.add(new ExpirationNotification(user, accomCertificationInteractor
                    .findByUserExpiringSoon(user)));
        }

        for (ExpirationSubscriber subscriber : subscribers) {
            subscriber.receiveNotification(notifications.toArray(new ExpirationNotification[notifications.size()]));
        }
    }

}

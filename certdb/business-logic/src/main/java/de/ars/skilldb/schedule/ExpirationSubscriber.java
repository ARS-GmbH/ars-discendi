/**
 *
 */
package de.ars.skilldb.schedule;

import de.ars.skilldb.returnobjects.ExpirationNotification;

/**
 * Ein {@code ExpirationSubscriber} empfängt {@link ExipirationNotification}s wenn Zertifizierungen
 * ablaufen.
 */
public interface ExpirationSubscriber {

    /**
     * Methode zum Empfangen von {@link ExpirationNotification}s.
     *
     * @param notifications
     *            die zu empfangenden {@link ExpirationNotification}s.
     */
    void receiveNotification(ExpirationNotification... notifications);

}

/**
 *
 */
package de.ars.skilldb.schedule;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.interactor.CertificationInteractor;
import de.ars.skilldb.returnobjects.UpdateResponse;

/**
 * Führt ein Update der Zertifizierungen von der IBM Webseite aus. Eine Instanz dieser Klasse wird
 * per Cron-Job getriggert.
 */
public class CertificationUpdateRunner implements Runnable {

    @Autowired
    private CertificationInteractor certificationInteractor;

    private final Set<UpdateSubscriber> updateSubscribers;

    /**
     * Erzeugt einen {@code CertificationUpdateRunner} mit den spezifizierten Subscribern.
     *
     * @param updateSubscribers
     *            die {@code Subscriber}, denen das Ergebnis mitgeteilt wird.
     */
    public CertificationUpdateRunner(final Set<UpdateSubscriber> updateSubscribers) {
        this.updateSubscribers = updateSubscribers;
    }

    @Override
    public void run() {
        Collection<Certification> all = certificationInteractor.updateCertifications();
        Collection<Certification> updated = certificationInteractor.findUpdated();
        Collection<Certification> newOnes = certificationInteractor.findNewOnes();

        populateResponseToSubscribers(new UpdateResponse(all, updated, newOnes));
    }

    private void populateResponseToSubscribers(final UpdateResponse response) {
        for (UpdateSubscriber subscriber : updateSubscribers) {
            subscriber.recieveResponse(response);
        }
    }

}

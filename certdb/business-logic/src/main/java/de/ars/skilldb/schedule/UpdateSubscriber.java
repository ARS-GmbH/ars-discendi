/**
 *
 */
package de.ars.skilldb.schedule;

import de.ars.skilldb.returnobjects.UpdateResponse;

/**
 * Ein {@code UpdateSubscriber} empfängt {@code UpdateResponse}-Objekte durch die Ausführung von
 * {@code Interactor}-Methoden.
 *
 */
public interface UpdateSubscriber {

    /**
     * Methode zum Empfangen von {@code UpdateResponse}-Objekten.
     *
     * @param response
     *            das {@code UpdateResponse}.
     */
    void recieveResponse(UpdateResponse response);

}

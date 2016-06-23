/**
 *
 */
package de.ars.skilldb.returnobjects;

import de.ars.skilldb.domain.Certification;

/**
 * Return-Objekt, das eine Zertifizierung ({@link Certification}) sowie deren vorangegangene Version
 * beinhaltet.
 */
public class CertificationRevision {

    private final Certification actual;
    private final Certification preVersion;

    /**
     * Erzeugt eine neue {@link CertificationRevision} mit der aktuellen und der
     * Vorgänger-Zertifizierung.
     *
     * @param actual
     *            die aktuelle {@link Certification}.
     * @param preVersion
     *            die vorhergehende Version der Zertifizierung.
     */
    public CertificationRevision(final Certification actual, final Certification preVersion) {
        this.actual = actual;
        this.preVersion = preVersion;
    }

    public Certification getActual() {
        return actual;
    }

    public Certification getPreVersion() {
        return preVersion;
    }

}

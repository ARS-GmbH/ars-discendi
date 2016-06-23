package de.ars.skilldb.domain.enums;

public enum CertStatus {
    /** Aktive Zertifizierungen. */
    ACTIVE("Aktiv"),
    /** Neue und gleichzeitig aktive Zertifizierungen. */
    NEW_ACTIVE("Neu (Aktiv)"),
    /** Abgelaufene Zertifizierungen. */
    EXPIRED("Ausgelaufen"),
    /** Ablaufende Zertifizierungen. */
    EXPIRING("Läuft aus"),
    /** Verworfene Zertifizierungen. */
    WITHDRAWN("Verworfen");
    
    private final String name;
    
    private CertStatus(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}

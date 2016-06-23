package de.ars.skilldb.domain.enums;

public enum CertKind {
    /** Technische Zertifizierung. */
    TECHNICAL("Technical"),
    /** Verkaufs-Zertifizierung. */
    SALES("Sales"),
    /** Keine Zertifizierungs-Art. (Für nicht-Ibm-Zertifizierungen) */
    NONE("Keine");

    private final String name;

    private CertKind(final String name) {
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

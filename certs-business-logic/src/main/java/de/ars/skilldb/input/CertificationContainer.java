package de.ars.skilldb.input;

import java.util.Map;
import java.util.TreeMap;

import de.ars.skilldb.domain.Certification;

/**
 * Container für {@code Certification}s, der bestehende Objekte ausgibt, wenn diese bereits
 * existieren. Andernfalls wird ein neues Objekt erzeugt. Dient zur Vermeidung von Duplikaten.
 *
 */
public class CertificationContainer {

    private final Map<String, Certification> container;

    /**
     * Default-Konstruktor.
     */
    public CertificationContainer() {
        container = new TreeMap<>();
    }

    /**
     * Holt die {@code Certification} mit dem spezifizierten Namen.
     *
     * @param name
     *            der Name der {@code Certification}, die geholt werden soll.
     * @return die angefragte {@code Certification}.
     */
    public Certification retrieve(final String name) {
        if (container.containsKey(name)) {
            return container.get(name);
        }
        else {
            Certification certification = new Certification();
            certification.setName(name);
            container.put(name, certification);
            return certification;
        }
    }
}

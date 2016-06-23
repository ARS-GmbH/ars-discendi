package de.ars.skilldb.input;

import java.util.Map;
import java.util.TreeMap;

import de.ars.skilldb.domain.Brand;

/**
 * Container für {@code Brand}s, der bestehende Objekte ausgibt, wenn diese bereits existieren.
 * Andernfalls wird ein neues Objekt erzeugt. Dient zur Vermeidung von Duplikaten.
 */
public class BrandContainer {

    private final Map<String, Brand> container;

    /**
     * Default-Konstruktor.
     */
    public BrandContainer() {
        container = new TreeMap<>();
    }

    /**
     * Holt die {@code Brand} mit dem spezifizierten Namen.
     *
     * @param name
     *            der Name der {@code Brand}, die geholt werden soll.
     * @return die angefragte {@code Brand}.
     */
    public Brand retrieve(final String name) {
        if (container.containsKey(name)) {
            return container.get(name);
        }
        else {
            Brand brand = new Brand();
            brand.setName(name);
            container.put(name, brand);
            return brand;
        }
    }
}

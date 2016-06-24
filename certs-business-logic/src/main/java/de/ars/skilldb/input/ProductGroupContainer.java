package de.ars.skilldb.input;

import java.util.Map;
import java.util.TreeMap;

import de.ars.skilldb.domain.ProductGroup;

/**
 * Container für {@code ProductGroup}s, der bestehende Objekte ausgibt, wenn diese bereits
 * existieren. Andernfalls wird ein neues Objekt erzeugt. Dient zur Vermeidung von Duplikaten.
 *
 */
public class ProductGroupContainer {

    private final Map<String, ProductGroup> container;

    /**
     * Default-Konstruktor.
     */
    public ProductGroupContainer() {
        container = new TreeMap<>();
    }

    /**
     * Holt die {@code ProductGroup} mit dem spezifizierten Namen.
     *
     * @param name
     *            der Name der {@code ProductGroup}, die geholt werden soll.
     * @return die angefragte {@code ProductGroup}.
     */
    public ProductGroup retrieve(final String name) {
        if (container.containsKey(name)) {
            return container.get(name);
        }
        else {
            ProductGroup productGroup = new ProductGroup();
            productGroup.setName(name);
            container.put(name, productGroup);
            return productGroup;
        }
    }
}

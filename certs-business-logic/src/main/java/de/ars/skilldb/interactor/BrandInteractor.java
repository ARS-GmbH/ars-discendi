package de.ars.skilldb.interactor;

import de.ars.skilldb.domain.Brand;

/**
 * Interactor für die {@code Brand}s, der die notwendige Business-Logik zur Verfügung stellt.
 */
public interface BrandInteractor extends Interactor<Brand> {

    /**
     * Findet {@code Brand}s anhand des Namens.
     *
     * @param name
     *            der Name der {@code Brand}.
     * @return die gesuchte {@code Brand}.
     */
    Brand findByName(String name);

}

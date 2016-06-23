package de.ars.skilldb.interactor;

import java.util.Collection;

import de.ars.skilldb.domain.ProductGroup;
import de.ars.skilldb.returnobjects.Readiness;

/**
 * Interactor für die {@code ProductGroup}s, der die notwendige Business-Logik zur Verfügung stellt.
 */
public interface ProductGroupInteractor extends Interactor<ProductGroup> {

    /**
     * Findet eine {@code ProductGroup} anhand des Namens.
     *
     * @param name
     *            der Name der {@code ProductGroup}.
     * @return die gesuchte {@code ProductGroup}.
     */
    ProductGroup findByName(String name);

    /**
     * Findet alle {@code Readiness}-Status Objekte.
     *
     * @return alle {@code Readiness}-Status Objekte.
     */
    Collection<Readiness> findAllReadinessStatus();

    /**
     * Überprüft, ob die {@code ProductGroup} den Status {@code ready} erreicht hat.
     *
     * @param productGroup
     *            die zu prüfunde {@code ProductGroup}.
     * @return {@code true} wenn die {@code ProductGroup} den Status {@code ready} erreicht hat,
     *         andernfalls {@code false}.
     */
    boolean isReady(ProductGroup productGroup);

}

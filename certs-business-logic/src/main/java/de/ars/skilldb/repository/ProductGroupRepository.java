package de.ars.skilldb.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.ProductGroup;

/**
 * Spring-Data Repository für die Klasse {@code ProductGroup}.
 */
public interface ProductGroupRepository extends PagingAndSortingRepository<ProductGroup, Long> {

    /**
     * Findet eine {@code ProductGroup} anhand des Namens.
     * 
     * @param productGroup
     *            der Name der gesuchten {@code ProductGroup}.
     * @return die gesuchte {@code ProductGroup}.
     */
    ProductGroup findByName(String productGroup);

    /**
     * Findet alle {@code ProductGroup}s.
     * 
     * @return eine Liste aller {@code ProductGroup}s.
     */
    @Query("MATCH (p:ProductGroup) return p;")
    List<ProductGroup> findAllProductGroups();
}

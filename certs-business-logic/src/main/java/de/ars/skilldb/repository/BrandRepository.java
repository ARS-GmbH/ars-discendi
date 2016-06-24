package de.ars.skilldb.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.Brand;

/**
 * Spring-Data Repository für die Klasse {@code Brand}.
 */
public interface BrandRepository extends PagingAndSortingRepository<Brand, Long> {

    /**
     * Findet eine {@code Brand} anhand ihres Namens.
     * 
     * @param name
     *            der Name der gesuchten {@code Brand}.
     * @return die gesuchte {@code Brand}.
     */
    Brand findByName(String name);

}

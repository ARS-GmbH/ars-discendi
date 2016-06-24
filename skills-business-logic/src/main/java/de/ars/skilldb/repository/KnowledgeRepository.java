package de.ars.skilldb.repository;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.Knowledge;

/**
 * Repository für  Wissensgebiete.
 *
 */
public interface KnowledgeRepository extends PagingAndSortingRepository<Knowledge, Long> {

    /**
     * Liefert das anhand des Namens gesuchte Wissensgebiet zurück.
     * @param name Name des Wissensgebiets
     * @return das gesuchte Wissensgebiet
     */
    Knowledge findByName(String name);

    /**
     * Liefert das anhand des Namens gesuchte Wissensgebiet zurück. Die Suche erfolgt case-insensitive.
     * @param name Name des Wisssensgebiets (case-insensitive)
     * @return das gesuchte Wissensgebiet
     */
    @Query("Match (n:Knowledge) Where n.name =~ ('(?i)' + {0}) return n")
    Knowledge findByNameCaseInsensitive(String name);

    /**
     * Liefert das anhand des Namens gesuchte Wissensgebiet zurück. Die Suche erfolgt case-insensitive.
     * Dabei wird aber das Wissensgebiet mit der übergebenen ID nicht berücksichtigt.
     * @param name Name des Wisssensgebiets (case-insensitive)
     * @param id ID, die nicht berücksichtigt werden soll
     * @return das gesuchte Wissensgebiet
     */
    @Query("Match (n:Knowledge) Where n.name =~ ('(?i)' + {0}) AND NOT id(n)={1} return n")
    Knowledge findByNameWithoutOne(String name, Long id);

    /**
     * Liefert alle Wissensgebiete nach Namen alphabetisch aufsteigend sortiert zurück.
     * @return Wissensgebiete alphabetisch sortiert
     */
    @Query("MATCH (k:Knowledge) Return k Order By Upper(k.name) Asc")
    Collection<Knowledge> findAllSortByNameAsc();

}

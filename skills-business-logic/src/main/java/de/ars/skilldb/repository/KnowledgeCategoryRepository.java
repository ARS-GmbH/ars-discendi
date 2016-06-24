package de.ars.skilldb.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ars.skilldb.domain.KnowledgeCategory;

/**
 * Repository für  Wissensgebiete.
 *
 */
public interface KnowledgeCategoryRepository extends PagingAndSortingRepository<KnowledgeCategory, Long> {

    /**
     * Liefert die anhand des Namens gesuchte Wissensgebiets-Kategorie zurück. Die Suche erfolgt case-insensitive.
     * @param name Name der Kategorie (case-insensitive)
     * @return die gesuchte Kategorie
     */
    @Query("MATCH (c:KnowledgeCategory) WHERE c.name =~ ('(?i)' + {0}) RETURN c")
    KnowledgeCategory findByNameCaseInsensitive(String name);

    /**
     * Liefert die anhand des Namens gesuchte Wissensgebiets-Kategorie zurück. Die Suche erfolgt case-insensitive.
     * Dabei wird aber die Kategorie mit der übergebenen ID nicht berücksichtigt.
     * @param name Name der Kategorie (case-insensitive)
     * @param id ID, die nicht berücksichtigt werden soll
     * @return die gesuchte Wissensgebiets-Kategorie
     */
    @Query("MATCH (c:KnowledgeCategory) WHERE c.name =~ ('(?i)' + {0}) AND NOT id(c)={1} RETURN c")
    KnowledgeCategory findByNameWithoutOne(String name, Long id);

    /**
     * Liefert alle Kind-Knoten der übergebenen Kategorie.
     * @param category Kategorie deren Kinder gesucht werden
     * @return alle existierenden Kinder der übergebenen Kategorie
     */
    @Query("START category=NODE({0}) MATCH (category)<-[:HAS_PARENT]-(children) RETURN children")
    Set<KnowledgeCategory> findChildren(KnowledgeCategory category);

    /**
     * Liefert den Wurzelknoten der Wissensgebietskategorien zurück.
     * @return Wurzelknoten
     */
    @Query("MATCH (n:KnowledgeCategory) WHERE NOT (n)-->() RETURN n")
    KnowledgeCategory findRoot();
}

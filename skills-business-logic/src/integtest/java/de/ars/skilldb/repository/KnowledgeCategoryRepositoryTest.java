package de.ars.skilldb.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.KnowledgeCategory;

/**
 * Testklasse für das Repository von Wissensgebiets-Kategorien.
 */
public class KnowledgeCategoryRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private KnowledgeCategoryRepository knowledgeCategoryRepository;

    /**
     * Testet das Finden von Wissensgebieten anhand des Namens unabhängig von Groß- und Kleinschreibung.
     */
    @Test
    @Transactional
    public void testFindByNameCaseInsensitive() {
        String name = "Test1";
        String nameOtherCase = "test1";
        KnowledgeCategory expected = generateCategory(name);
        KnowledgeCategory other = generateCategory("other");

        knowledgeCategoryRepository.save(expected);
        knowledgeCategoryRepository.save(other);

        KnowledgeCategory actual = knowledgeCategoryRepository.findByNameCaseInsensitive(name);
        KnowledgeCategory otherCase = knowledgeCategoryRepository.findByNameCaseInsensitive(nameOtherCase);

        assertEquals("Result-Knowledge is not equal.", expected, actual);
        assertEquals("Result-Knowledge is not equal.", expected, otherCase);
    }

    private KnowledgeCategory generateCategory(final String name) {
        KnowledgeCategory category = new KnowledgeCategory();
        category.setName(name);
        category.setDescription("Description");
        category.setParents(new HashSet<KnowledgeCategory>());
        category.setChildrenCategories(new HashSet<KnowledgeCategory>());
        category.setChildrenKnowledges(new HashSet<Knowledge>());
        category.setCreated(new Date());
        category.setLastModified(new Date());

        return category;
    }
}
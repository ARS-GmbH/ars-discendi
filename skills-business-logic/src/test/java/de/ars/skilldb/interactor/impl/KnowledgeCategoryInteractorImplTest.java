package de.ars.skilldb.interactor.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import de.ars.skilldb.domain.KnowledgeCategory;
import de.ars.skilldb.interactor.KnowledgeCategoryInteractor;
import de.ars.skilldb.repository.KnowledgeCategoryRepository;
import de.ars.skilldb.util.AssertionFailedException;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Testklasse zum KnowledgeCategoryInteractor.
 *
 */
public class KnowledgeCategoryInteractorImplTest {

    private KnowledgeCategoryInteractor interactor;
    private KnowledgeCategoryRepository knowledgeCategoryRepository;
    private static final String CATEGORY_NAME = "TestName";

    /**
     * Initiales erzeugen von Objekten, die in den meisten Testfällen benötigt werden.
     */
    @Before
    public void initObjects() {
        knowledgeCategoryRepository = Mockito.mock(KnowledgeCategoryRepository.class);
        interactor = new KnowledgeCategoryInteractorImpl(knowledgeCategoryRepository);
        ReflectionTestUtils.setField(interactor, "knowledgeCategoryRepository", knowledgeCategoryRepository);
    }

    /**
     * Testet das Anlegen einer neuen Wissensgebiets-Kategorie.
     */
    @Test
    public void testAdd() {
        KnowledgeCategory categoryToSave = generateCategory(CATEGORY_NAME);

        categoryToSave.setParents(generateParents());

        Mockito.when(knowledgeCategoryRepository.findByNameCaseInsensitive(Matchers.anyString())).thenReturn(null);
        Mockito.when(knowledgeCategoryRepository.save(categoryToSave)).thenReturn(categoryToSave);

        KnowledgeCategory actual = interactor.add(categoryToSave);

        assertEquals("Category is not equal.", categoryToSave, actual);
    }

    /**
     * Testet das Anlegen einer Wissensgebiets-Kategorie, wenn die Kategorie
     * null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testAddNull() {
        interactor.add(null);
    }

    /**
     * Testet das Anlegen einer Wissensgebiets-Kategorie, wenn der Name leer ist.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddNameBlank() {
        KnowledgeCategory category = generateCategory(" ");

        interactor.add(category);
    }

    /**
     * Testet das Anlegen einer bereits existierenden Wissensgebiets-Kategorie.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddExistingKnowledgeCategory() {
        KnowledgeCategory existingCategory = generateCategory(CATEGORY_NAME);
        Mockito.when(knowledgeCategoryRepository.findByNameCaseInsensitive(Matchers.anyString())).thenReturn(existingCategory);

        interactor.add(existingCategory);
    }

    /**
     * Testet das Anlegen einer neuen Wissensgebiets-Kategorie, wenn die Eltern-Kategorie null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testAddParentNull() {
        KnowledgeCategory categoryToSave = generateCategory(CATEGORY_NAME);
        categoryToSave.setParents(null);

        interactor.add(categoryToSave);
    }

    /**
     * Testet das Anlegen einer neuen Wissensgebiets-Kategorie, wenn die Eltern-Kategorie leer ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testAddParentEmpty() {
        KnowledgeCategory categoryToSave = generateCategory(CATEGORY_NAME);
        categoryToSave.setParents(new HashSet<KnowledgeCategory>());

        interactor.add(categoryToSave);
    }

    /**
     * Testet das Editieren einer Wissensgebiets-Kategorie.
     */
    @Test
    public void testUpdate() {
        KnowledgeCategory category = generateCategory(CATEGORY_NAME);
        Long id = 0L;
        category.setId(id);
        Mockito.when(knowledgeCategoryRepository.findByNameWithoutOne(CATEGORY_NAME, id)).thenReturn(null);
        Mockito.when(knowledgeCategoryRepository.findOne(Matchers.anyLong())).thenReturn(category);
        Mockito.when(knowledgeCategoryRepository.save(category)).thenReturn(category);

        KnowledgeCategory actual = interactor.update(category);

        assertEquals("Category is not equal.", category, actual);
    }

    /**
     * Testet das Ändern einer Wissensgebiets-Kategorie, wenn die Kategorie
     * null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testUpdateNull() {
        interactor.update(null);
    }

    /**
     * Testet das Ändern einer Wissensgebiets-Kategorie, wenn der Name leer ist.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testUpdateNameBlank() {
        KnowledgeCategory category = generateCategory(" ");

        interactor.update(category);
    }

    /**
     * Testet das Ändern einer Wissensgebiets-Kategorie, wenn die ID null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testUpdateIdNull() {
        KnowledgeCategory category = generateCategory(CATEGORY_NAME);
        category.setId(null);

        interactor.update(category);
    }

    /**
     * Testet das Ändern einer Wissensgebiets-Kategorie, wenn der Name der
     * Kategorie bereits existiert.
     */
    @Test(expected = IllegalUserArgumentsException.class)
    public void testUpdateToExistingName() {
        Long id = 0L;
        KnowledgeCategory category = generateCategory(CATEGORY_NAME);
        category.setId(id);
        Mockito.when(knowledgeCategoryRepository.findByNameWithoutOne(CATEGORY_NAME, id)).thenReturn(category);
        interactor.update(category);
    }

    /**
     * Testet das Ändern einer Wissensgebiets-Kategorie, wenn Parent null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testUpdateParentsNull() {
        KnowledgeCategory categoryToSave = generateCategory(CATEGORY_NAME);

        categoryToSave.setParents(null);

        Mockito.when(knowledgeCategoryRepository.findByNameCaseInsensitive(Matchers.anyString())).thenReturn(null);
        Mockito.when(knowledgeCategoryRepository.findOne(Matchers.anyLong())).thenReturn(categoryToSave);
        Mockito.when(knowledgeCategoryRepository.save(categoryToSave)).thenReturn(categoryToSave);

        interactor.update(categoryToSave);
    }

    /**
     * Testet das Ändern einer Wissensgebiets-Kategorie, wenn Parent leer ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testUpdateParentsEmpty() {
        KnowledgeCategory categoryToSave = generateCategory(CATEGORY_NAME);

        categoryToSave.setParents(new HashSet<KnowledgeCategory>());

        Mockito.when(knowledgeCategoryRepository.findByNameCaseInsensitive(Matchers.anyString())).thenReturn(null);
        Mockito.when(knowledgeCategoryRepository.findOne(Matchers.anyLong())).thenReturn(categoryToSave);
        Mockito.when(knowledgeCategoryRepository.save(categoryToSave)).thenReturn(categoryToSave);

        interactor.update(categoryToSave);
    }

    /**
     * Testet das Finden aller Kategorien.
     */
    @Test
    public void testFindAll() {
        Collection<KnowledgeCategory> expected = generateAllCategories();
        Mockito.when(knowledgeCategoryRepository.findAll()).thenReturn(expected);

        Collection<KnowledgeCategory> actual = interactor.findAll();

        assertEquals("Categories are not equal.", expected, actual);
    }

    private Collection<KnowledgeCategory> generateAllCategories() {
        KnowledgeCategory c1 = generateCategory("c1");
        KnowledgeCategory c2 = generateCategory("C2");
        KnowledgeCategory c3 = generateCategory("c3");

        Collection<KnowledgeCategory> allCategories = new ArrayList<KnowledgeCategory>();

        allCategories.add(c2);
        allCategories.add(c3);
        allCategories.add(c1);

        return allCategories;
    }

    private KnowledgeCategory generateCategory(final String name) {
        KnowledgeCategory category = new KnowledgeCategory();
        category.setId(1L);
        category.setName(name);
        category.setDescription("Description");
        category.setParents(generateParents());

        return category;
    }

    private Set<KnowledgeCategory> generateParents() {
        Set<KnowledgeCategory> parents = new HashSet<KnowledgeCategory>();
        parents.add(new KnowledgeCategory());
        return parents;
    }

}

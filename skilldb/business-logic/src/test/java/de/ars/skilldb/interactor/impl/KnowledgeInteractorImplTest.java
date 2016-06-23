package de.ars.skilldb.interactor.impl;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.KnowledgeCategory;
import de.ars.skilldb.interactor.KnowledgeInteractor;
import de.ars.skilldb.repository.KnowledgeRepository;
import de.ars.skilldb.util.AssertionFailedException;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Testklasse zum KnowledgeInteractor.
 *
 */
public class KnowledgeInteractorImplTest {

    private KnowledgeInteractor interactor;
    private KnowledgeRepository knowledgeRepository;
    private static final String KNOWLEDGE_NAME = "TestName";

    /**
     * Initiales erzeugen von Objekten, die in den meisten Testfällen benötigt werden.
     */
    @Before
    public void initObjects() {
        knowledgeRepository = Mockito.mock(KnowledgeRepository.class);
        interactor = new KnowledgeInteractorImpl(knowledgeRepository);
        ReflectionTestUtils.setField(interactor, "knowledgeRepository", knowledgeRepository);
    }

    /**
     * Testet das Anlegen eines neuen Wissensgebiets.
     */
    @Test
    public void testAdd() {
        Knowledge knowledgeToSave = generateKnowledge(KNOWLEDGE_NAME);
        Mockito.when(knowledgeRepository.findByNameCaseInsensitive(Matchers.anyString())).thenReturn(null);
        Mockito.when(knowledgeRepository.save(knowledgeToSave)).thenReturn(knowledgeToSave);

        Knowledge actual = interactor.add(knowledgeToSave);

        assertEquals("Knowledge is not equal.", knowledgeToSave, actual);
    }

    /**
     * Testet das Anlegen eines neuen Wissensgebiets, wenn das Wissensgebiet null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testAddNull() {
        interactor.add(null);
    }

    /**
     * Testet das Anlegen eines neuen Wissensgebiets, wenn der Name des
     * Wissensgebiets kein Zeichen außer Whitespace enthält.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddNameBlank() {
        Knowledge knowledge = generateKnowledge(" ");
        interactor.add(knowledge);
    }

    /**
     * Testet das Anlegen eines neuen Wissensgebiets, wenn das Wissensgebiet
     * keiner Kategorie zugeordnet wurde.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddCategoryEmpty() {
        Knowledge knowledge = generateKnowledge(KNOWLEDGE_NAME);
        knowledge.setCategories(new HashSet<KnowledgeCategory>());
        interactor.add(knowledge);
    }

    /**
     * Testet das Anlegen eines bereits existierenden Wissensgebiets.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddExistingKnowledge() {
        Knowledge existingKnowledge = generateKnowledge(KNOWLEDGE_NAME);
        Mockito.when(knowledgeRepository.findByNameCaseInsensitive(Matchers.anyString())).thenReturn(existingKnowledge);

        interactor.add(existingKnowledge);
    }

    /**
     * Testet das Updaten eines Wissensgebiets.
     */
    @Test
    public void testUpdate() {
        Knowledge knowledge = generateKnowledge(KNOWLEDGE_NAME);
        Long id = 0L;
        knowledge.setId(id);
        Mockito.when(knowledgeRepository.findByNameWithoutOne(KNOWLEDGE_NAME, id)).thenReturn(null);
        Mockito.when(knowledgeRepository.save(knowledge)).thenReturn(knowledge);

        Knowledge actual = interactor.update(knowledge);

        assertEquals("Knowledge is not equal.", knowledge, actual);
    }

    /**
     * Testet das Updaten eines Wissensgebiets, wenn das Wissensgebiet null ist.
     */
    @Test(expected = AssertionFailedException.class)
    public void testUpdateNull() {
        interactor.update(null);
    }

    /**
     * Testet das Updaten eines Wissensgebiets, wenn der Name des
     * Wissensgebiets kein Zeichen außer Whitespace enthält.
     */
    @Test(expected = IllegalUserArgumentsException.class)
    public void testUpdateNameBlank() {
        Knowledge knowledge = generateKnowledge(" ");
        interactor.update(knowledge);
    }

    /**
     * Testet das Updaten eines Wissensgebiets, wenn das Wissensgebiet keiner
     * Kategorie zugeordnet wurde.
     */
    @Test(expected = IllegalUserArgumentsException.class)
    public void testUpdateCategoriesEmpty() {
        Knowledge knowledge = generateKnowledge(KNOWLEDGE_NAME);
        knowledge.setCategories(new HashSet<KnowledgeCategory>());
        interactor.update(knowledge);
    }

    /**
     * Testet das Updaten eines Wissensgebiets, wenn die ID des Wissensgebiets
     * null ist.
     */
    @Test(expected = AssertionFailedException.class)
    public void testUpdateIdNull() {
        Knowledge knowledge = generateKnowledge(KNOWLEDGE_NAME);
        knowledge.setId(null);
        interactor.update(knowledge);
    }

    /**
     * Testet das Updaten eines Wissensgebiets, wenn der Name bereits bei einem
     * anderen Wissensgebiet verwendet wird.
     */
    @Test(expected = IllegalUserArgumentsException.class)
    public void testUpdateToExistingName() {
        Long id = 0L;
        Knowledge knowledge = generateKnowledge(KNOWLEDGE_NAME);
        knowledge.setId(id);
        Mockito.when(knowledgeRepository.findByNameWithoutOne(KNOWLEDGE_NAME, id)).thenReturn(knowledge);
        interactor.update(knowledge);
    }

    private Set<KnowledgeCategory> generateCategories() {
        Set<KnowledgeCategory> possibleParents = new HashSet<KnowledgeCategory>();
        possibleParents.add(generateCategory("possibleParent"));
        possibleParents.add(generateCategory("possibleParent2"));
        return possibleParents;
    }

    private Knowledge generateKnowledge(final String name) {
        Knowledge knowledge = new Knowledge();
        knowledge.setId(1L);
        knowledge.setName(name);
        knowledge.setDescription("Description");
        knowledge.setCategories(generateCategories());

        return knowledge;
    }

    private KnowledgeCategory generateCategory(final String name) {
        KnowledgeCategory category = new KnowledgeCategory();
        category.setId(1L);
        category.setName(name);
        category.setDescription("Description");

        return category;
    }

}

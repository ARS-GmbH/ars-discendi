package de.ars.skilldb.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.Knowledge;

/**
 * Testklasse für das Wissensgebiets-Repository.
 */
public class KnowledgeRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    /**
     * Testet das Finden von Wissensgebieten anhand des Namens.
     */
    @Test
    @Transactional
    public void testFindByName() {
        String name = "Test1";
        Knowledge expected = generateKnowledge(name);
        Knowledge other = generateKnowledge("other");

        knowledgeRepository.save(expected);
        knowledgeRepository.save(other);

        Knowledge actual = knowledgeRepository.findByName(name);

        assertEquals("Result-Knowledge is not equal.", expected, actual);
    }

    /**
     * Testet das Finden von Wissensgebieten anhand des Namens unabhängig von Groß- und Kleinschreibung.
     */
    @Test
    @Transactional
    public void testFindByNameCaseInsensitive() {
        String name = "Test1";
        String nameOtherCase = "test1";
        Knowledge expected = generateKnowledge(name);
        Knowledge other = generateKnowledge("other");

        knowledgeRepository.save(expected);
        knowledgeRepository.save(other);

        Knowledge noKnowledge = knowledgeRepository.findByName(nameOtherCase);
        Knowledge actual = knowledgeRepository.findByNameCaseInsensitive(nameOtherCase);

        assertNull("noKnowledge is not null.", noKnowledge);
        assertEquals("Result-Knowledge is not equal.", expected, actual);
    }

    /**
     * Testet, die Methode zum finden eines Wissensgebiets anhand des Namens
     * (unabhängig von Groß- und Kleinschreibung), bei der eine bestimmte ID
     * nicht berücksichtigt werden soll.
     */
    @Test
    @Transactional
    public void testFindByNameCaseInsensitiveWithoutId() {
        Knowledge expected = knowledgeRepository.save(generateKnowledge("Test-Name"));
        Knowledge other = knowledgeRepository.save(generateKnowledge("Other"));

        String nameOtherCase = expected.getName().toLowerCase(Locale.GERMANY);


        Knowledge actual = knowledgeRepository.findByNameWithoutOne(expected.getName(), other.getId());
        Knowledge actualOtherCase = knowledgeRepository.findByNameWithoutOne(nameOtherCase, other.getId());


        assertEquals("Result-Knowledge is not equal.", expected, actual);
        assertEquals("Result-Knowledge is not equal.", expected, actualOtherCase);
    }

    /**
     * Testet, die Methode zum finden eines Wissensgebiets anhand des Namens
     * (unabhängig von Groß- und Kleinschreibung), bei der eine bestimmte ID
     * nicht berücksichtigt werden soll. Es wird genau die ID und der Name des
     * existierenden Wissensgebiets übergeben, sodass die Suche null ergeben
     * muss.
     */
    @Test
    @Transactional
    public void testFindByNameCaseInsensitiveWithoutIdNoMatch() {
        Knowledge savedKnowledge = knowledgeRepository.save(generateKnowledge("Test-Name"));
        String nameOtherCase = savedKnowledge.getName().toLowerCase(Locale.GERMANY);

        Knowledge noKnowledge = knowledgeRepository.findByNameWithoutOne(savedKnowledge.getName(), savedKnowledge.getId());
        Knowledge noKnowledgeOtherCase = knowledgeRepository.findByNameWithoutOne(nameOtherCase, savedKnowledge.getId());

        assertNull("noKnowledge is not null.", noKnowledge);
        assertNull("noKnowledgeOtherCase is not null.", noKnowledgeOtherCase);
    }

    /**
     * Testet das Finden aller Wissensgebiete alphabetisch sortiert und caseinsensitive.
     */
    @Test
    public void testFindAllSortByNameAsc() {
        Knowledge a = generateKnowledge("A");
        Knowledge b = generateKnowledge("b");
        Knowledge f = generateKnowledge("f");
        Knowledge z = generateKnowledge("Z");

        Collection<Knowledge> inputList = new ArrayList<Knowledge>();
        inputList.add(f);
        inputList.add(a);
        inputList.add(z);
        inputList.add(b);

        Collection<Knowledge> expected = new ArrayList<Knowledge>();
        expected.add(a);
        expected.add(b);
        expected.add(f);
        expected.add(z);

        knowledgeRepository.save(inputList);

        Collection<Knowledge> actual = knowledgeRepository.findAllSortByNameAsc();

        assertEquals("Find-Result is not equal.", expected, actual);
    }

    private Knowledge generateKnowledge(final String name) {
        Knowledge knowledge = new Knowledge();
        knowledge.setName(name);
        knowledge.setDescription("TestBeschreibung");
        return knowledge;
    }
}
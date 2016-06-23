/**
 *
 */
package de.ars.skilldb.interactor.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.interactor.SkillLevelInteractor;
import de.ars.skilldb.repository.SkillLevelRepository;
import de.ars.skilldb.util.AssertionFailedException;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Testklasse zum Skill-Level-Interactor.
 *
 */
public class SkillLevelInteractorImplTest {

    private SkillLevelInteractor interactor;
    private SkillLevelRepository skillLevelRepository;

    /**
     * Initiales erzeugen von Objekten, die in den meisten Testfällen benötigt werden.
     */
    @Before
    public void initObjects() {
        skillLevelRepository = Mockito.mock(SkillLevelRepository.class);
        interactor = new SkillLevelInteractorImpl(skillLevelRepository);
        ReflectionTestUtils.setField(interactor, "skillLevelRepository", skillLevelRepository);
    }

    /**
     * Testet das Finden eines Skill-Levels anhand des internen Werts.
     */
    @Test
    public void testFindByInternalValue() {
        int internalValue = 100;
        SkillLevel expected = generateSkillLevel(internalValue);
        Mockito.when(skillLevelRepository.findByInternalValue(Matchers.anyInt())).thenReturn(expected);

        SkillLevel actual = interactor.findByInternalValue(internalValue);

        assertEquals("SkillLevel is not equal.", expected, actual);
    }

    /**
     * Testet das Finden eines Skill-Levels anhand des internen Werts, wenn der
     * gesuchte Wert kleiner als 0 ist.
     */
    @Test(expected = IllegalUserArgumentsException.class)
    public void testFindByInternalValueSmallerThanZero() {
        int internalValue = -1;
        interactor.findByInternalValue(internalValue);
    }

    /**
     * Testet das Finden eines Skill-Levels anhand des internen Werts, wenn der
     * gesuchte Wert größer als 100 ist.
     */
    @Test(expected = IllegalUserArgumentsException.class)
    public void testFindByInternalValueHigherThanHundret() {
        int internalValue = 101;
        interactor.findByInternalValue(internalValue);
    }

    /**
     * Testet das Hinzufügen eines neuen Skill-Levels.
     */
    @Test
    public void testAdd() {
        SkillLevel expected = generateSkillLevel(10);
        Mockito.when(skillLevelRepository.findByInternalValue(Matchers.anyInt())).thenReturn(null);
        Mockito.when(skillLevelRepository.findByNameCaseInsensitive(Matchers.anyString())).thenReturn(null);
        Mockito.when(skillLevelRepository.save(expected)).thenReturn(expected);

        SkillLevel actual = interactor.add(expected);

        assertEquals("Response is not equal.", expected, actual);
        assertNotNull("Created date is null.", actual.getCreated());
    }

    /**
     * Testet das Hinzufügen eines neuen Skill-Levels, wenn das Level null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testAddNull() {
        interactor.add(null);
    }

    /**
     * Testet das Hinzufügen eines neuen Skill-Levels, wenn der interne Wert kleiner 0 ist.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddSmallerThanZero() {
        SkillLevel skillLevel = generateSkillLevel(-1);
        interactor.add(skillLevel);
    }

    /**
     * Testet das Hinzufügen eines neuen Skill-Levels, wenn der interne Wert größer 100 ist.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddHigherThanHundret() {
        SkillLevel skillLevel = generateSkillLevel(101);
        interactor.add(skillLevel);
    }

    /**
     * Testet das Hinzufügen eines neuen Skill-Levels, wenn bereits ein Level mit dem internen Wert exisitiert.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddInternalValueExists() {
        SkillLevel skillLevel = generateSkillLevel(10);
        Mockito.when(skillLevelRepository.findByInternalValue(Matchers.anyInt())).thenReturn(skillLevel);

        interactor.add(skillLevel);
    }

    /**
     * Testet das Hinzufügen eines neuen Skill-Levels, wenn bereits ein Level mit dem Namen exisitiert.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testAddNameExists() {
        SkillLevel skillLevel = generateSkillLevel(10);
        Mockito.when(skillLevelRepository.findByInternalValue(Matchers.anyInt())).thenReturn(null);
        Mockito.when(skillLevelRepository.findByNameCaseInsensitive(Matchers.anyString())).thenReturn(skillLevel);

        interactor.add(skillLevel);
    }

    /**
     * Testet das Ändern eines Skill-Levels.
     */
    @Test
    public void testEdit() {
        SkillLevel expected = generateSkillLevel(10);
        Mockito.when(skillLevelRepository.findByInternalValueWithoutId(Matchers.anyLong(), Matchers.anyInt())).thenReturn(null);
        Mockito.when(skillLevelRepository.findByNameCaseInsensitiveWithoutId(Matchers.anyLong(), Matchers.anyString())).thenReturn(null);
        Mockito.when(skillLevelRepository.save(expected)).thenReturn(expected);

        SkillLevel actual = interactor.update(expected);

        assertEquals("Response is not equal.", expected, actual);
        assertNotNull("Last modified date is null.", actual.getLastModified());
    }

    /**
     * Testet das Ändern eines Skill-Levels, wenn das Level null ist.
     */
    @Test (expected = AssertionFailedException.class)
    public void testUpdateNull() {
        interactor.update(null);
    }

    /**
     * Testet das Ändern eines Skill-Levels, wenn der interne Wert kleiner 0 ist.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testUpdateSmallerThanZero() {
        SkillLevel skillLevel = generateSkillLevel(-1);
        interactor.update(skillLevel);
    }

    /**
     * Testet das Ändern eines Skill-Levels, wenn der interne Wert größer 100 ist.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testUpdateHigherThanHundret() {
        SkillLevel skillLevel = generateSkillLevel(101);
        interactor.update(skillLevel);
    }

    /**
     * Testet das Ändern eines Skill-Levels, wenn bereits ein Level mit dem internen Wert exisitiert.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testEditInternalValueExists() {
        SkillLevel skillLevel = generateSkillLevel(10);
        Mockito.when(skillLevelRepository.findByInternalValueWithoutId(Matchers.anyLong(), Matchers.anyInt())).thenReturn(skillLevel);

        interactor.update(skillLevel);
    }

    /**
     * Testet das Ändern eines Skill-Levels, wenn bereits ein Level mit dem Namen exisitiert.
     */
    @Test (expected = IllegalUserArgumentsException.class)
    public void testEditNameExists() {
        SkillLevel skillLevel = generateSkillLevel(10);
        Mockito.when(skillLevelRepository.findByInternalValueWithoutId(Matchers.anyLong(), Matchers.anyInt())).thenReturn(null);
        Mockito.when(skillLevelRepository.findByNameCaseInsensitiveWithoutId(Matchers.anyLong(), Matchers.anyString())).thenReturn(skillLevel);

        interactor.update(skillLevel);
    }

    private SkillLevel generateSkillLevel(final int internalValue) {
        SkillLevel skillLevel = new SkillLevel();
        skillLevel.setInternalValue(internalValue);
        skillLevel.setName("Wissen");
        skillLevel.setDescription("Erlerntes in unveränderter Weise erkennen,...");
        skillLevel.setTags("erkennen, ...");
        return skillLevel;
      }
}

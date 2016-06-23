package de.ars.skilldb.input;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import de.ars.skilldb.domain.ProductGroup;

/**
 * Testet die Klasse {@code ProductGroupContainer}.
 */
public class ProductGroupContainerTest {

    /**
     * Testet, ob eine bereits existierende {@code ProductGroup} korrekt zurückgeliefert wird.
     */
    @Test
    public void testRetrieveExistingProductGroup() {
        ProductGroupContainer testProductGroupContainer = new ProductGroupContainer();
        ProductGroup expected = new ProductGroup();
        @SuppressWarnings("unchecked")
        Map<String, ProductGroup> container = mock(Map.class);
        ReflectionTestUtils.setField(testProductGroupContainer, "container", container);
        when(container.containsKey("Name")).thenReturn(true);
        when(container.get("Name")).thenReturn(expected);

        ProductGroup actual = testProductGroupContainer.retrieve("Name");

        assertSame("The returned ProductGroup is not the same", expected, actual);
    }

    /**
     * Testet, ob eine nicht existierende {@code ProductGroup} korrekt erzeugt und zurückgeliefert
     * wird.
     */
    @Test
    public void testRetrieveNotExistingProductGroup() {
        ProductGroupContainer testProductGroupContainer = new ProductGroupContainer();
        ProductGroup expected = new ProductGroup();
        expected.setName("Name");
        @SuppressWarnings("unchecked")
        Map<String, ProductGroup> container = mock(Map.class);
        ReflectionTestUtils.setField(testProductGroupContainer, "container", container);
        when(container.containsKey("Name")).thenReturn(false);

        ProductGroup actual = testProductGroupContainer.retrieve("Name");

        verify(container, times(1)).put(eq("Name"), eq(actual));
        assertEquals("The returned ProductGroup is not equal.", expected, actual);
    }
}

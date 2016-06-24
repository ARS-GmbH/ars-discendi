package de.ars.skilldb.input;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import de.ars.skilldb.domain.Brand;

/**
 * Testet die Klasse {@code BrandContainer}.
 */
public class BrandContainerTest {

    /**
     * Testet, ob eine bereits existierende {@code Brand} korrekt zurückgeliefert wird.
     */
    @Test
    public void testRetrieveExistingBrand() {
        BrandContainer testBrandContainer = new BrandContainer();
        Brand expected = new Brand();
        @SuppressWarnings("unchecked")
        Map<String, Brand> container = mock(Map.class);
        ReflectionTestUtils.setField(testBrandContainer, "container", container);
        when(container.containsKey("Name")).thenReturn(true);
        when(container.get("Name")).thenReturn(expected);

        Brand actual = testBrandContainer.retrieve("Name");

        assertSame("The returned Brand is not the same", expected, actual);
    }

    /**
     * Testet, ob eine nicht existierende {@code Brand} korrekt erzeugt und zurückgeliefert wird.
     */
    @Test
    public void testRetrieveNotExistingBrand() {
        BrandContainer testBrandContainer = new BrandContainer();
        Brand expected = new Brand();
        expected.setName("Name");
        @SuppressWarnings("unchecked")
        Map<String, Brand> container = mock(Map.class);
        ReflectionTestUtils.setField(testBrandContainer, "container", container);
        when(container.containsKey("Name")).thenReturn(false);

        Brand actual = testBrandContainer.retrieve("Name");

        verify(container, times(1)).put(eq("Name"), eq(actual));
        assertEquals("The returned Brand is not equal.", expected, actual);
    }
}

package de.ars.skilldb.input;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import de.ars.skilldb.domain.Certification;

/**
 * Testet die Klasse {@code CertificationContainer}.
 * 
 */
public class CertificationContainerTest {

    /**
     * Testet, ob eine bereits existierende {@code Certification} korrekt zurückgeliefert wird.
     */
    @Test
    public void testRetrieveExistingCertification() {
        CertificationContainer testCertificationContainer = new CertificationContainer();
        Certification expected = new Certification();
        @SuppressWarnings("unchecked")
        Map<String, Certification> container = mock(Map.class);
        ReflectionTestUtils.setField(testCertificationContainer, "container", container);
        when(container.containsKey("Name")).thenReturn(true);
        when(container.get("Name")).thenReturn(expected);

        Certification actual = testCertificationContainer.retrieve("Name");

        assertSame("The returned Certification is not the same", expected, actual);
    }

    /**
     * Testet, ob eine nicht existierende {@code Certification} korrekt erzeugt und zurückgeliefert
     * wird.
     */
    @Test
    public void testRetrieveNotExistingCertification() {
        CertificationContainer testCertificationContainer = new CertificationContainer();
        Certification expected = new Certification();
        expected.setName("Name");
        @SuppressWarnings("unchecked")
        Map<String, Certification> container = mock(Map.class);
        ReflectionTestUtils.setField(testCertificationContainer, "container", container);
        when(container.containsKey("Name")).thenReturn(false);

        Certification actual = testCertificationContainer.retrieve("Name");

        verify(container, times(1)).put(eq("Name"), eq(actual));
        assertEquals("The returned Certification is not equal.", expected, actual);
    }
}

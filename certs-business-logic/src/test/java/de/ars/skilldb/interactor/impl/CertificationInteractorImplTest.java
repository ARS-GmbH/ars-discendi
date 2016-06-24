package de.ars.skilldb.interactor.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.input.CertSlurper;
import de.ars.skilldb.input.CertWebEntry;
import de.ars.skilldb.input.CertWebEntryMapper;
import de.ars.skilldb.interactor.PathInteractor;
import de.ars.skilldb.repository.CertificationRepository;

/**
 * Testet die Klasse {@code CertificationInteractorImpl}.
 */
public class CertificationInteractorImplTest {

    private CertificationInteractorImpl testCertInteractor;

    private CertSlurper slurper;
    private CertWebEntryMapper mapper;
    private CertificationRepository certRepository;

    /**
     * Initialisiert die Mocks und das zu testende Objekt.
     */
    @Before
    public void before() {
        certRepository = mock(CertificationRepository.class);
        testCertInteractor = new CertificationInteractorImpl(certRepository);
        slurper = mock(CertSlurper.class);
        mapper = mock(CertWebEntryMapper.class);
        PathInteractor paths = mock(PathInteractor.class);
        ReflectionTestUtils.setField(testCertInteractor, "slurper", slurper);
        ReflectionTestUtils.setField(testCertInteractor, "mapper", mapper);
        ReflectionTestUtils.setField(testCertInteractor, "certRepository", certRepository);
        ReflectionTestUtils.setField(testCertInteractor, "pathInteractor", paths);
    }

    /**
     * Testet, ob die von der IBM Webseite eingelesenen Zertifizierungen korrekt zurückgeliefert
     * werden.
     */
    @Test
    public void testLoadCertificationsInitially() {
        List<CertWebEntry> entries = Collections.emptyList();
        when(slurper.slurpCertWebEntries()).thenReturn(entries);
        Multimap<String, Certification> newCerts = ArrayListMultimap.create();
        Certification cert1 = mock(Certification.class);
        when(cert1.getVersion()).thenReturn(1);
        when(cert1.getCertificationCode()).thenReturn("001");
        Certification cert2 = mock(Certification.class);
        when(cert2.getVersion()).thenReturn(2);
        when(cert2.getCertificationCode()).thenReturn("001");
        newCerts.put("001", cert1);
        newCerts.put("001", cert2);
        when(mapper.convertMultipleEntriesToMap(entries)).thenReturn(newCerts);
        Iterable<Certification> iterable = Collections.emptySet();
        @SuppressWarnings("unchecked")
        Result<Certification> result = mock(Result.class);
        when(result.iterator()).thenReturn(iterable.iterator());
        when(certRepository.findAll()).thenReturn(result);
        List<Certification> expected = Lists.newArrayList(iterable);

        Collection<Certification> actual = testCertInteractor.loadCertificationsInitially();

        verify(cert2, times(1)).setPredecessor(cert1);
        ArgumentCaptor<Certification> captor = ArgumentCaptor.forClass(Certification.class);
        verify(certRepository, times(2)).save(captor.capture());
        assertEquals("First call on save was not with Certification 1.", cert1, captor.getAllValues().get(0));
        assertEquals("Second call on save was not with Certification 2.", cert2, captor.getAllValues().get(1));
        assertEquals("The returned certifications are not equal.", expected, actual);
    }

    /**
     * Testet, ob alle vorhandenen Zertifizierungen korrekt zurückgeliefert werden.
     */
    @Test
    public void testFindAllCertifications() {
        Iterable<Certification> iterable = Collections.emptySet();
        @SuppressWarnings("unchecked")
        Result<Certification> result = mock(Result.class);
        when(result.iterator()).thenReturn(iterable.iterator());
        when(certRepository.findAll()).thenReturn(result);
        List<Certification> expected = Lists.newArrayList(iterable);

        Collection<Certification> actual = testCertInteractor.findAll();

        assertEquals("The returned certifications are not equal.", expected, actual);
    }

}

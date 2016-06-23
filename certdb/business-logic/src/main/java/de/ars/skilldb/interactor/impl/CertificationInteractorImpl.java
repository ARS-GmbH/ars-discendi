package de.ars.skilldb.interactor.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.Path;
import de.ars.skilldb.domain.ProductGroup;
import de.ars.skilldb.exception.InvalidModelException;
import de.ars.skilldb.exception.InvalidModelException.Builder;
import de.ars.skilldb.input.CertSlurper;
import de.ars.skilldb.input.CertWebEntry;
import de.ars.skilldb.input.CertWebEntryMapper;
import de.ars.skilldb.interactor.CertificationInteractor;
import de.ars.skilldb.interactor.PathInteractor;
import de.ars.skilldb.repository.CertificationRepository;
import de.ars.skilldb.repository.PathRepository;
import de.ars.skilldb.returnobjects.CertificationRevision;
import de.ars.skilldb.util.Ensure;

/**
 * Implementiert den {@code CertificationInteractor}.
 *
 */
public class CertificationInteractorImpl extends AbstractInteractor<Certification> implements CertificationInteractor {

    @Autowired
    private CertSlurper slurper;

    @Autowired
    private CertWebEntryMapper mapper;

    private final CertificationRepository certRepository;

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private PathInteractor pathInteractor;

    private final List<Certification> lastAddedCertifications;
    private final List<Certification> lastUpdatedCertifications;
    private final List<Certification> oldUpdatedCertifications;

    /**
     * Erzeugt einen neuen {@link CertificationInteractorImpl}.
     *
     * @param certRepository
     *            das {@code Repository} für Zertifizierungen.
     */
    @Autowired
    public CertificationInteractorImpl(final CertificationRepository certRepository) {
        super(certRepository);
        this.certRepository = certRepository;
        lastAddedCertifications = new ArrayList<>();
        lastUpdatedCertifications = new ArrayList<>();
        oldUpdatedCertifications = new ArrayList<>();
    }

    @Override
    @Transactional
    public Collection<Certification> loadCertificationsInitially() {
        List<CertWebEntry> slurpedEntries = slurper.slurpCertWebEntries();
        Multimap<String, Certification> newIbmCertifications = mapper.convertMultipleEntriesToMap(slurpedEntries);
        for (Certification cert : newIbmCertifications.values()) {
            Collection<Certification> possiblePredecessors = Lists.newArrayList(newIbmCertifications.get(cert
                    .getCertificationCode()));
            Certification latestPredecessor = findLatestPredecessor(possiblePredecessors, cert.getVersion());
            cert.setPredecessor(latestPredecessor);
            cert.setLastModified(new Date());
            if (cert.getPaths().size() == 0) {
                Path path = pathInteractor.save(new Path(0));
                cert.addPath(path);
            }
            certRepository.save(cert);
        }

        return findAll();
    }

    @Override
    public Collection<Certification> updateCertifications() {
        lastAddedCertifications.clear();
        lastUpdatedCertifications.clear();
        updateCertsFromWebpage();

        return findAll();
    }

    @Transactional
    private void updateCertsFromWebpage() {
        List<CertWebEntry> slurpedEntries = slurper.slurpCertWebEntries();
        Multimap<String, Certification> newIbmCertifications = mapper.convertMultipleEntriesToMap(slurpedEntries);
        for (Certification cert : newIbmCertifications.values()) {
            String certificationCode = cert.getCertificationCode();
            Collection<Certification> possiblePredecessors = new ArrayList<>();
            possiblePredecessors.addAll(newIbmCertifications.get(certificationCode));
            possiblePredecessors.addAll(certRepository.findByCertificationCode(certificationCode));

            Certification latestPredecessor = findLatestPredecessor(possiblePredecessors, cert.getVersion());
            cert.setPredecessor(latestPredecessor);
            Date now = new Date();
            Certification dbCert = certRepository.findByCertificationCodeAndVersion(cert.getCertificationCode(),
                    cert.getVersion());
            if (dbCert == null) {
                cert.setLastModified(now);
                lastAddedCertifications.add(certRepository.save(cert));
            }
            else {
                Certification refreshedCert = refreshDatabaseCertification(dbCert, cert);
                Certification savedCert = certRepository.save(refreshedCert);

                if (!dbCert.equals(savedCert)) {
                    savedCert.setLastModified(now);
                    lastUpdatedCertifications.add(certRepository.save(savedCert));
                    oldUpdatedCertifications.add(fetchFullData(dbCert));
                }
            }
        }
    }

    private Certification refreshDatabaseCertification(final Certification dbCert, final Certification cert) {
        Certification certification = new Certification(dbCert);
        certification.setName(cert.getName());
        certification.setExpirationDate(cert.getExpirationDate());
        certification.setKind(cert.getKind());
        certification.setBrands(cert.getBrands());
        certification.setProductGroups(cert.getProductGroups());
        certification.setStatus(cert.getStatus());
        certification.setSkillPoint(cert.getSkillPoint());
        certification.setPredecessor(cert.getPredecessor());
        certification.setLink(cert.getLink());

        return certification;
    }

    private Certification findLatestPredecessor(final Collection<Certification> possiblePredecessors, final int version) {
        Certification bestMatch = null;

        for (Certification certification : possiblePredecessors) {
            if (bestMatch == null) {
                if (certification.getVersion() < version) {
                    bestMatch = certification;
                }
                continue;
            }

            if (certification.getVersion() < version) {

                if (certification.getVersion() > bestMatch.getVersion()) {
                    bestMatch = certification;
                }
                else if (bestMatch.getVersion().equals(certification.getVersion())) {
                    bestMatch = certification.getId() == null ? bestMatch : certification;
                }
            }
        }
        return bestMatch;
    }

    @Override
    public Collection<Certification> findUpdated() {
        return Lists.newArrayList(lastUpdatedCertifications);
    }

    @Override
    public Collection<Certification> findNewOnes() {
        return Lists.newArrayList(lastAddedCertifications);
    }

    @Override
    @Transactional
    public Certification save(final Certification cert) {
        Ensure.that(cert).isNotNull();
        checkCertificationConstraints(cert);
        if (cert.getPaths().size() == 0) {
            cert.addPath(new Path());
        }
        cert.setPaths(pathInteractor.fetchFullData(Sets.newHashSet(pathInteractor.save(cert.getPaths()))));
        Certification createdCert = certRepository.save(cert);
        pathRepository.deleteAllDanglingPaths();

        return createdCert;
    }

    @Override
    public void checkCertificationConstraints(final Certification cert) {
        InvalidModelException.Builder builder = new InvalidModelException.Builder();
        checkCertificationName(cert, builder);
        checkPredecessorConstraints(cert, builder);
        checkCertificationCodeAndVersion(cert, builder);
        if (builder.hasModelErrors()) {
            builder.throwIt();
        }
    }

    private void checkCertificationName(final Certification cert, final InvalidModelException.Builder builder) {
        Certification dbCert = certRepository.findByName(cert.getName());
        if (dbCert != null && !dbCert.getId().equals(cert.getId())) {
            builder.withModelError("name", "Der Name wird bereits für eine andere Zertifizierung verwendet.");
        }
    }

    private void checkPredecessorConstraints(final Certification cert, final InvalidModelException.Builder builder) {
        if (cert.getPredecessor() != null && cert.getName().equals(cert.getPredecessor().getName())) {
            builder.message("There were model errors for Certification " + cert).withModelError("predecessor",
                    "Der Vorgänger kann nicht die Zertifizierung selbst sein.");
        }
    }

    private void checkCertificationCodeAndVersion(final Certification cert, final Builder builder) {
        try {
            Certification existing = certRepository.findByCertificationCodeAndVersion(cert.getCertificationCode(),
                    cert.getVersion());
            if (cert.getId() == null && existing != null || cert.getId() != null
                    && !cert.getId().equals(existing.getId())) {
                throw new NoSuchElementException();
            }
        }
        catch (NoSuchElementException e) {
            builder.cause(e).withModelError("version",
                    "Es existiert bereits eine Zertifizierung mit diesem Kürzel und dieser Version!");
        }
    }

    @Override
    public Certification findByName(final String name) {
        Ensure.that(name).isNotEmpty();
        return certRepository.findByName(name);
    }

    @Override
    public CertificationRevision findOneWithOldCertification(final Long id) {
        Ensure.that(id).isPositive();
        Certification certification = certRepository.findOne(id);
        if (certification == null) {
            throw new IllegalStateException("No Certification with id " + id + " found");
        }

        Certification preVersion = null;
        for (Certification oldUpdated : oldUpdatedCertifications) {
            if (certification.getId().equals(oldUpdated.getId())) {
                preVersion = oldUpdated;
            }
        }
        return new CertificationRevision(certification, preVersion);
    }

    @Override
    public Collection<Certification> findByNameContaining(final String searchQuery, final int page, final int count) {
        Ensure.that(searchQuery).isNotEmpty();
        Pageable pageable = new PageRequest(page, count);

        return certRepository.findByNameContaining(searchQuery, pageable);
    }

    @Override
    public Collection<Certification> findByProductGroup(final ProductGroup productGroup) {
        Ensure.that(productGroup).isNotNull();

        return Lists.newArrayList(certRepository.findByProductGroup(productGroup));
    }

    @Override
    public Certification fetchFullData(final Certification certification) {
        Ensure.that(certification).isNotNull();

        certification.setPredecessor(getTemplate().fetch(certification.getPredecessor()));
        certification.setBrands(getTemplate().fetch(certification.getBrands()));
        certification.setProductGroups(getTemplate().fetch(certification.getProductGroups()));
        certification.setPaths(getTemplate().fetch(certification.getPaths()));

        return certification;
    }

    @Override
    public long countWithNameContaining(final String searchQuery) {
        Ensure.that(searchQuery).isNotEmpty();
        return certRepository.countWithNameContaining(searchQuery);
    }

}
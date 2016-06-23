/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import de.ars.skilldb.domain.*;
import de.ars.skilldb.domain.enums.CertKind;
import de.ars.skilldb.interactor.AccomplishedCertificationInteractor;
import de.ars.skilldb.output.BlobPersister;
import de.ars.skilldb.repository.AccomplishedCertificationRepository;
import de.ars.skilldb.repository.CertificationRepository;
import de.ars.skilldb.repository.PathRepository;
import de.ars.skilldb.util.Ensure;

/**
 * Implementiert den {@code AccomplishedCertificationInteractor}.
 */
public class AccomplishedCertificationInteractorImpl extends AbstractInteractor<AccomplishedCertification> implements
        AccomplishedCertificationInteractor {

    private final AccomplishedCertificationRepository accomplishedCertificationRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private BlobPersister persister;

    /**
     * Erzeugt einen neuen {@link AccomplishedCertificationInteractorImpl}.
     *
     * @param accomplishedCertificationRepository
     *            das {@code Repository} für abgeschlossene Zertifizierungen.
     */
    @Autowired
    public AccomplishedCertificationInteractorImpl(
            final AccomplishedCertificationRepository accomplishedCertificationRepository) {
        super(accomplishedCertificationRepository);
        this.accomplishedCertificationRepository = accomplishedCertificationRepository;
    }

    @Override
    public AccomplishedCertification fetchFullData(final AccomplishedCertification object) {
        Ensure.that(object).isNotNull();
        object.setCertification(getTemplate().fetch(object.getCertification()));
        object.setUser(getTemplate().fetch(object.getUser()));
        return object;
    }

    @Override
    public List<AccomplishedCertification> findByCertification(final Certification certification) {
        return Lists.newArrayList(accomplishedCertificationRepository.findByCertification(certification));
    }

    @Override
    public List<AccomplishedCertification> findByUser(final User user) {
        return Lists.newArrayList(accomplishedCertificationRepository.findByUser(user));
    }

    @Override
    public List<AccomplishedCertification> findByUserExpiringSoon(final User user) {
        Ensure.that(user).isNotNull();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() - 14 * 24 * 3600 * 1000);

        return fetchFullData(Lists.newArrayList(accomplishedCertificationRepository.findByUserExpiringSoon(user,
                String.valueOf(expirationDate.getTime()))));
    }

    @Override
    public List<AccomplishedCertification> findByProductGroupAndKind(final ProductGroup productGroup,
            final CertKind certKind) {
        Ensure.that(productGroup).isNotNull();
        Ensure.that(certKind).isNotNull();

        return Lists.newArrayList(accomplishedCertificationRepository.findByProductGroupAndKind(productGroup, certKind
                .toString().toUpperCase()));
    }

    @Override
    public byte[] getAccomplishedDocument(final Long id) {
        return persister.retrieveBlob(accomplishedCertificationRepository.findOne(id).getDocumentId());
    }

    @Override
    public AccomplishedCertification save(final AccomplishedCertification accomplishedCertification, final byte[] file) {
        Certification certification = accomplishedCertification.getCertification();
        User actingUser = accomplishedCertification.getUser();
        Certification dbCertification = certificationRepository.findOne(certification.getId());
        Set<Path> pathsOfCert = pathRepository.findAllPathsOfCertification(dbCertification);
        for (Path path : pathsOfCert) {
            boolean userCanAccomplishCert = accomplishedCertificationRepository.hasUserNecessaryExamsForPath(path,
                    actingUser)
                    && accomplishedCertificationRepository.hasUserNecessaryCertificationsForPath(path, actingUser)
                    && accomplishedCertificationRepository.hasUserOneChoosableExamForPath(path, actingUser);
            if (userCanAccomplishCert) {
                accomplishedCertification.setDocumentId(persister.persistBlob(file));
                return accomplishedCertificationRepository.save(accomplishedCertification);
            }
        }
        throw new IllegalStateException(
                "The user cannot accomplish this certification because he has missing tests and/or missing certifications.");
    }

}

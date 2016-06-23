/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import de.ars.skilldb.domain.AccomplishedExam;
import de.ars.skilldb.domain.PlannedExam;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.AccomplishedExamInteractor;
import de.ars.skilldb.output.BlobPersister;
import de.ars.skilldb.repository.AccomplishedExamRepository;
import de.ars.skilldb.repository.PlannedExamRepository;
import de.ars.skilldb.util.Ensure;

/**
 * Implementiert den {@link AccomplishedExamInteractor}.
 */
public class AccomplishedExamInteractorImpl extends AbstractInteractor<AccomplishedExam> implements
        AccomplishedExamInteractor {

    private final AccomplishedExamRepository accomplishedExams;

    @Autowired
    private PlannedExamRepository plannedExamRepository;

    @Autowired
    private BlobPersister persister;

    /**
     * Standard-Konstruktor.
     *
     * @param accomplishedExams
     *            das {@link Repository} für abgeschlossene Tests.
     */
    @Autowired
    public AccomplishedExamInteractorImpl(final AccomplishedExamRepository accomplishedExams) {
        super(accomplishedExams);
        this.accomplishedExams = accomplishedExams;
    }

    @Override
    public AccomplishedExam addAccomplishedExam(final AccomplishedExam accomplishedExam, final byte[] document) {
        PlannedExam toDelete = plannedExamRepository.findByExamAndUser(accomplishedExam.getExam(),
                accomplishedExam.getUser());
        plannedExamRepository.delete(toDelete);
        accomplishedExam.setExamDocumentId(persister.persistBlob(document));

        return accomplishedExams.save(accomplishedExam);
    }

    @Override
    @Transactional
    public AccomplishedExam fetchFullData(final AccomplishedExam object) {
        object.setExam(getTemplate().fetch(object.getExam()));
        object.setUser(getTemplate().fetch(object.getUser()));

        return object;
    }

    @Override
    public Collection<AccomplishedExam> findAllAccomplishedExams(final User user) {
        Ensure.that(user).isNotNull();

        return fetchFullData(Lists.newArrayList(accomplishedExams.findMyAccomplishedExams(user)));
    }

    @Override
    public byte[] getAccomplishedDocument(final Long id) {
        Ensure.that(id).isNotNull();
        Ensure.that(id).isPositive();

        AccomplishedExam accomplished = accomplishedExams.findOne(id);
        return persister.retrieveBlob(accomplished.getExamDocumentId());
    }

}

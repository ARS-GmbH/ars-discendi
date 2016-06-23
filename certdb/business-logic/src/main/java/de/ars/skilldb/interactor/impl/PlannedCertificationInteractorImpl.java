/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import de.ars.skilldb.domain.*;
import de.ars.skilldb.interactor.PlannedCertificationInteractor;
import de.ars.skilldb.interactor.PlannedExamInteractor;
import de.ars.skilldb.repository.ExamRepository;
import de.ars.skilldb.repository.PlannedCertificationRepository;
import de.ars.skilldb.util.Ensure;

/**
 * Implementiert {@link PlannedCertificationInteractor}.
 */
public class PlannedCertificationInteractorImpl extends AbstractInteractor<PlannedCertification> implements
        PlannedCertificationInteractor {

    @Autowired
    private ExamRepository exams;

    @Autowired
    private PlannedCertificationRepository plannedCerts;

    @Autowired
    private PlannedExamInteractor plannedExamInteractor;

    /**
     * Konstruiert einen neuen {@link PlannedCertificationInteractorImpl}.
     *
     * @param plannedCertifications
     *            das {@code Repository} für geplante Zertifizierungen.
     */
    @Autowired
    protected PlannedCertificationInteractorImpl(final PlannedCertificationRepository plannedCertifications) {
        super(plannedCertifications);
    }

    @Override
    @Transactional
    public PlannedCertification save(final PlannedCertification plannedCertification) {
        Ensure.that(plannedCertification).isNotNull();
        Ensure.that(plannedCertification.getPath()).isNotNull();
        Ensure.that(plannedCertification.getUser()).isNotNull();

        planNecessaryExamsFor(plannedCertification);

        return super.save(plannedCertification);
    }

    private void planNecessaryExamsFor(final PlannedCertification plannedCertification) {
        Iterable<Exam> necessaryExams = exams.findNecessaryOfPath(plannedCertification.getPath());
        for (Exam exam : necessaryExams) {
            PlannedExam planned = new PlannedExam();
            planned.setCarryOutUntil(plannedCertification.getCarryOutUntil());
            planned.setExam(exam);
            planned.setUser(plannedCertification.getUser());
            planned.setPlanner(plannedCertification.getPlannerUserName());

            plannedExamInteractor.addPlannedExam(planned);
        }
    }

    @Override
    public Collection<PlannedCertification> findByUser(final User user) {
        Ensure.that(user).isNotNull();
        return Lists.newArrayList(plannedCerts.findByUser(user));
    }

    @Override
    public PlannedCertification fetchFullData(final PlannedCertification planned) {
        Ensure.that(planned).isNotNull();
        planned.setPath(getTemplate().fetch(planned.getPath()));
        planned.setUser(getTemplate().fetch(planned.getUser()));

        return planned;
    }

    @Override
    public void deleteByAccomplish(final User user, final Certification certification) {
        Ensure.that(user).isNotNull();
        Ensure.that(certification).isNotNull();

        plannedCerts.deleteByUserAndCertification(user, certification);

    }

}

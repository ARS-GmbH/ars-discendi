/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import de.ars.skilldb.domain.Exam;
import de.ars.skilldb.domain.PlannedExam;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.PlannedExamInteractor;
import de.ars.skilldb.repository.ExamRepository;
import de.ars.skilldb.repository.PlannedExamRepository;
import de.ars.skilldb.util.Ensure;

/**
 * Implementiert den {@link PlannedExamInteractor}.
 */
public class PlannedExamInteractorImpl extends AbstractInteractor<PlannedExam> implements PlannedExamInteractor {

    @Autowired
    private ExamRepository examRepository;

    /**
     * Konstruiert einen neuen {@link PlannedExamInteractorImpl}.
     *
     * @param plannedExams
     *            das {@code Repository} für geplante Tests.
     */
    @Autowired
    public PlannedExamInteractorImpl(final PlannedExamRepository plannedExams) {
        super(plannedExams);
    }

    @Override
    public Exam addPlannedExam(final PlannedExam plannedExam) {
        Ensure.that(plannedExam).isNotNull();
        Ensure.that(plannedExam.getExam()).isNotNull("There is no exam to this planned exam: %s", plannedExam);
        Ensure.that(plannedExam.getUser()).isNotNull("There is no user to this planned exam: %s", plannedExam);

        if (hasUserExam(plannedExam.getUser(), plannedExam.getExam())) {
            throw new IllegalStateException("User already has planned or accomplished this exam.");
        }

        plannedExam.getExam().addPlanned(plannedExam);
        Exam savedExam = examRepository.save(plannedExam.getExam());

        return savedExam;
    }

    @Override
    @Transactional
    public PlannedExam fetchFullData(final PlannedExam object) {
        Ensure.that(object).isNotNull();
        object.setExam(getTemplate().fetch(object.getExam()));
        object.setUser(getTemplate().fetch(object.getUser()));

        return object;
    }

    @Override
    public Collection<PlannedExam> findAllMyPlannedExams(final User user) {
        Ensure.that(user).isNotNull();
        List<PlannedExam> exams = fetchFullData(Lists.newArrayList(examRepository.findMyPlannedExams(user)));

        return exams;
    }

    private boolean hasUserExam(final User user, final Exam exam) {
        return examRepository.findPlannedExamOfUser(user, exam).iterator().hasNext()
                || examRepository.findAccomplishedExamOfUser(user, exam).iterator().hasNext();
    }

}

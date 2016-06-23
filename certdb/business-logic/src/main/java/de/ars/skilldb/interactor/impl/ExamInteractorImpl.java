package de.ars.skilldb.interactor.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import de.ars.skilldb.domain.Exam;
import de.ars.skilldb.domain.Path;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.ExamInteractor;
import de.ars.skilldb.repository.ExamRepository;
import de.ars.skilldb.util.Ensure;

/**
 * Implementiert den {@code ExamInteractor}.
 */
public class ExamInteractorImpl extends AbstractInteractor<Exam> implements ExamInteractor {

    private final ExamRepository examRepository;

    /**
     * Konstruiert einen neuen {@link ExamInteractorImpl}.
     *
     * @param examRepository
     *            das {@code Repository} für Tests.
     */
    @Autowired
    public ExamInteractorImpl(final ExamRepository examRepository) {
        super(examRepository);
        this.examRepository = examRepository;
    }

    @Override
    public Exam fetchFullData(final Exam exam) {
        Ensure.that(exam).isNotNull();

        exam.setAccomplished(getTemplate().fetch(exam.getAccomplished()));
        exam.setCertifications(getTemplate().fetch(exam.getCertifications()));
        exam.setPlanned(getTemplate().fetch(exam.getPlanned()));

        return exam;
    }

    @Override
    @Transactional
    public Collection<Exam> findAll(final int page, final int size) {
        Pageable pageable = new PageRequest(page, size);
        Iterable<Exam> foundExams = examRepository.findAll(pageable);

        return fetchFullData(Lists.newArrayList(foundExams));
    }

    @Override
    @Transactional
    public Collection<Exam> findAllMyExams(final User user) {
        Iterable<Exam> foundExams = examRepository.findMyExams(user);
        List<Exam> myexams = fetchFullData(Lists.newArrayList(foundExams));

        return myexams;
    }

    @Override
    public Collection<Exam> findByTitleContaining(final String searchQuery, final int page, final int count) {
        Ensure.that(searchQuery).isNotEmpty();
        Pageable pageable = new PageRequest(page, count);
        List<Exam> foundExams = examRepository.findByTitleContaining(searchQuery, pageable);

        return fetchFullData(foundExams);
    }

    @Override
    public Collection<Exam> findNecessaryExamsOfPath(final Path path) {
        Ensure.that(path).isNotNull();
        Iterable<Exam> necessaryExams = examRepository.findNecessaryOfPath(path);
        return Lists.newArrayList(necessaryExams);
    }

    @Override
    public Collection<Exam> findChoosableExamsOfPath(final Path path) {
        Ensure.that(path).isNotNull();
        Iterable<Exam> choosableExams = examRepository.findChoosableOfPath(path);
        return Lists.newArrayList(choosableExams);
    }

}

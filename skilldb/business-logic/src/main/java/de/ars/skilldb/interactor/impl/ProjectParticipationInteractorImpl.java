/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.ProjectParticipation;
import de.ars.skilldb.domain.SkillProofEdge;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.ProjectParticipationInteractor;
import de.ars.skilldb.repository.ProjectParticipationRepository;
import de.ars.skilldb.repository.SkillProofEdgeRepository;
import de.ars.skilldb.util.Ensure;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Implementierung eines Interactors für Projektarbeiten.
 */
public class ProjectParticipationInteractorImpl extends AbstractInteractor<ProjectParticipation> implements ProjectParticipationInteractor {


    private ProjectParticipationRepository projectParticipationRepository;

    @Autowired
    private SkillProofEdgeRepository skillProofEdgeRepository;

    /**
     * Erzeugt ein neues Objekt der Klasse ProjectParticipationInteractorImpl.
     *
     * @param projectParticipationRepository
     *            Repository für den Datenbankzugriff
     */
    @Autowired
    public ProjectParticipationInteractorImpl(final ProjectParticipationRepository projectParticipationRepository) {
        super(ProjectParticipation.class, projectParticipationRepository);
        this.projectParticipationRepository = projectParticipationRepository;
    }

    @Override
    @Transactional
    public ProjectParticipation add(final ProjectParticipation projectParticipation) {
        checkProjectParticipation(projectParticipation);

        ProjectParticipation sameProject = projectParticipationRepository.findByProjectAndUser(projectParticipation.getProject(), projectParticipation.getUser());
        checkExisting(sameProject);

        projectParticipation.setCreated(new Date());
        return projectParticipationRepository.save(projectParticipation);
    }

    @Override
    @Transactional
    public ProjectParticipation update(final ProjectParticipation projectParticipation) {
        checkProjectParticipation(projectParticipation);

        ProjectParticipation sameProject = projectParticipationRepository.findByProjectAndUserWithoutId(projectParticipation.getProject(), projectParticipation.getUser(), projectParticipation.getId());
        checkExisting(sameProject);

        projectParticipation.setLastModified(new Date());

        skillProofEdgeRepository.save(projectParticipation.getSkillProofEdges());
        return projectParticipationRepository.save(projectParticipation);
    }

    @Override
    public Collection<ProjectParticipation> findByUser(final User user) {
        Ensure.that(user).isNotNull("User must not be empty.");
        return projectParticipationRepository.findAllProjectParticipationsByUser(user);
    }

    @Override
    public Set<Knowledge> findAllKnowledges(final ProjectParticipation projectParticipation) {
        Ensure.that(projectParticipation).isNotNull("Project participation must not be null.");
        return projectParticipationRepository.findAllKnowledges(projectParticipation);
    }

    private void checkProjectParticipation(final ProjectParticipation projectParticipation) {
            Ensure.that(projectParticipation).isNotNull("Project must not be null.");
            Ensure.that(projectParticipation.getUser()).isNotNull("User must not be null.");

            Map<String, String> errorMap = new HashMap<String, String>();

            if (projectParticipation.getProject() == null) {
                errorMap.put("project", "Das Projekt darf nicht leer sein.");
            }
            if (projectParticipation.getDuration() <= 0) {
                errorMap.put("duration", "Die Dauer darf nicht 0 oder kleiner sein.");
            }
            for (SkillProofEdge edge : projectParticipation.getSkillProofEdges()) {
                if (edge.getHours() <= 0) {
                    errorMap.put("hours", "Die Stunden dürfen nicht 0 oder kleiner sein.");
                }
            }

            if (!errorMap.isEmpty()) {
                throw new IllegalUserArgumentsException(errorMap);
            }
    }

    private void checkExisting(final ProjectParticipation sameProject) {
        Map<String, String> errorMap = new HashMap<String, String>();

        if (sameProject != null) {
            errorMap.put("project", String.format("Es existiert bereits eine Projektarbeit zum Projekt %s.", sameProject.getProject().getName()));
        }

        if (!errorMap.isEmpty()) {
            throw new IllegalUserArgumentsException(errorMap);
        }
    }
}

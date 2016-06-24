/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.Project;
import de.ars.skilldb.interactor.ProjectInteractor;
import de.ars.skilldb.repository.ProjectRepository;
import de.ars.skilldb.util.Ensure;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Implementierung eines Interactors für Projekte.
 */
public class ProjectInteractorImpl extends AbstractInteractor<Project> implements ProjectInteractor {


    private ProjectRepository projectRepository;

    /**
     * Erzeugt ein neues Objekt der Klasse ProjectInteractorImpl.
     *
     * @param projectRepository
     *            Repository für den Datenbankzugriff
     */
    @Autowired
    public ProjectInteractorImpl(final ProjectRepository projectRepository) {
        super(Project.class, projectRepository);
        this.projectRepository = projectRepository;
    }


    @Override
    public Collection<Project> findByCustomer(final Long customerId, final Sort sort) {
        Ensure.that(customerId).isNotNull("ID of the customer must not be null.");

        return projectRepository.findByCustomer(customerId, sort);
    }

    @Override
    @Transactional
    public Project add(final Project project) {
        checkProject(project);

        Project sameName = projectRepository.findByNameCaseInsensitive(project.getName());
        checkExisting(sameName, project);

        project.setCreated(new Date());
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public Project update(final Project project) {
        checkProject(project);

        Project samerName = projectRepository.findByNameCaseInsensitiveWithoutId(project.getId(), project.getName());
        checkExisting(samerName, project);

        project.setLastModified(new Date());
        return projectRepository.save(project);
    }

    private void checkProject(final Project project) {
            Ensure.that(project).isNotNull("Project must not be null.");

            Map<String, String> errorMap = new HashMap<String, String>();

            if (project.getName().trim().isEmpty()) {
                errorMap.put("name", "Der Name darf nicht leer sein.");
            }
            if (project.getCustomer() == null) {
                errorMap.put("customer", "Der Kunde darf nicht leer sein.");
            }
            if (project.getStart() == null) {
                errorMap.put("start", "Das Anfangsdatum darf nicht leer sein.");
            }
            if (project.getEnd() == null) {
                errorMap.put("end", "Das Enddatum darf nicht leer sein.");
            }
            if (!errorMap.isEmpty()) {
                throw new IllegalUserArgumentsException(errorMap);
            }
    }

    private void checkExisting(final Project sameName, final Project newProject) {
        Map<String, String> errorMap = new HashMap<String, String>();

        if (sameName != null) {
            errorMap.put("name", String.format("Es existiert bereits ein Projekt mit dem Namen %s.", sameName.getName()));
        }
        if (newProject.getStart().after(newProject.getEnd())) {

            errorMap.put("start", "Das Startdatum darf nicht nach dem Enddatum sein.");
            errorMap.put("end", "");
        }

        if (!errorMap.isEmpty()) {
            throw new IllegalUserArgumentsException(errorMap);
        }
    }
}

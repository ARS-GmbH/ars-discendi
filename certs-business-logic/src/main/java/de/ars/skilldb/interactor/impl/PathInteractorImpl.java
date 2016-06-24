/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.domain.Path;
import de.ars.skilldb.exception.InvalidModelException;
import de.ars.skilldb.interactor.PathInteractor;
import de.ars.skilldb.repository.CertificationRepository;
import de.ars.skilldb.repository.PathRepository;
import de.ars.skilldb.util.Ensure;

/**
 * Implementiert den {@link PathInteractor}.
 */
public class PathInteractorImpl extends AbstractInteractor<Path> implements PathInteractor {

    private final PathRepository paths;

    @Autowired
    private CertificationRepository certifications;

    /**
     * Konstruiert einen neuen {@link PathInteractorImpl}.
     *
     * @param paths
     *            das {@code Repository} für Pfade.
     */
    @Autowired
    public PathInteractorImpl(final PathRepository paths) {
        super(paths);
        this.paths = paths;
    }

    @Override
    public Iterable<Path> save(final Iterable<Path> objects) {
        ArrayList<Path> savedPaths = new ArrayList<>();
        for (Path path : objects) {
            savedPaths.add(save(path));
        }
        return savedPaths;
    }

    @Override
    public Path save(final Path path) {

        for (Certification necessary : path.getNecessaryCertifications()) {
            if (paths.pathDestinatesNecessaryCertification(necessary, path)) {
                new InvalidModelException.Builder().withModelError(
                        "necessaryCertifications",
                        "Die Zertifizierung, für die dieser Pfad konfiguriert wird, kann selbst nicht notwendig sein.")
                        .throwIt();
            }
        }

        return super.save(path);
    }

    @Override
    public Path fetchFullData(final Path object) {
        Ensure.that(object).isNotNull();

        object.setChoosableExams(getTemplate().fetch(object.getChoosableExams()));
        object.setDestination(getTemplate().fetch(object.getDestination()));
        object.setNecessaryCertifications(getTemplate().fetch(object.getNecessaryCertifications()));
        object.setNecessaryExams(getTemplate().fetch(object.getNecessaryExams()));

        return object;
    }

    @Override
    public Collection<Path> findPathsToCertification(final Certification certification) {
        Set<Path> pathsToCertification;

        if (certification.getId() == null) {
            pathsToCertification = new HashSet<>();
            pathsToCertification.add(new Path(0));
            return pathsToCertification;
        }

        pathsToCertification = paths.findAllPathsOfCertification(certification);

        if (pathsToCertification.isEmpty()) {
            pathsToCertification.add(new Path(0));
            return pathsToCertification;
        }

        return fetchFullData(pathsToCertification);
    }

}

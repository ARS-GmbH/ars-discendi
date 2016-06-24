package de.ars.skilldb.interactor.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.KnowledgeCategory;
import de.ars.skilldb.interactor.KnowledgeCategoryInteractor;
import de.ars.skilldb.repository.KnowledgeCategoryRepository;
import de.ars.skilldb.util.Ensure;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Implementierung eines Interactors für Wissensgebiete.
 *
 */
public class KnowledgeCategoryInteractorImpl extends AbstractInteractor<KnowledgeCategory> implements KnowledgeCategoryInteractor {

    private final KnowledgeCategoryRepository knowledgeCategoryRepository;

    /**
     * Erzeugt eine Instanz der Klasse KnowledgeCategoryInteractorImpl.
     *
     * @param knowledgeCategoryRepository
     *            Repository für den Zugriff auf die Datenbank
     */
    @Autowired
    public KnowledgeCategoryInteractorImpl(final KnowledgeCategoryRepository knowledgeCategoryRepository) {
        super(KnowledgeCategory.class, knowledgeCategoryRepository);
        this.knowledgeCategoryRepository = knowledgeCategoryRepository;
    }

    @Override
    @Transactional
    public KnowledgeCategory add(final KnowledgeCategory category) {
        validateCategory(category);

        KnowledgeCategory existingCategory = knowledgeCategoryRepository.findByNameCaseInsensitive(category.getName());
        checkExisting(existingCategory);

        category.setCreated(new Date());
        return knowledgeCategoryRepository.save(category);
    }

    @Override
    @Transactional
    public KnowledgeCategory update(final KnowledgeCategory category) {
        validateCategory(category);
        Ensure.that(category.getId()).isNotNull("The id of the knowledge category must not be null.");

        KnowledgeCategory existingCategory = knowledgeCategoryRepository.findByNameWithoutOne(category.getName(), category.getId());
        checkExisting(existingCategory);

        checkParentIncludedInChildrenCategories(category);

        category.setLastModified(new Date());
        return knowledgeCategoryRepository.save(category);
    }

    @Override
    public KnowledgeCategory findRoot() {
        return knowledgeCategoryRepository.findRoot();
    }

    private void validateCategory(final KnowledgeCategory category) {
        Ensure.that(category).isNotNull("The category must not be null.");
        Ensure.that(category.getParents()).isNotEmpty("The parent of the category must not be empty or null.");
        if (category.getName().trim().isEmpty()) {
            throw new IllegalUserArgumentsException("name", "Der Name darf nicht leer sein.");
        }
    }

    private void checkExisting(final KnowledgeCategory existingCategory) {
        if (existingCategory != null) {
            throw new IllegalUserArgumentsException("name",
                    "Es existiert bereits eine Wissensgebiets-Kategorie mit dem Namen '%s'", existingCategory.getName());
        }
    }

    private void checkParentIncludedInChildrenCategories(final KnowledgeCategory category) {
        Set<KnowledgeCategory> parent = category.getParents();
        Set<KnowledgeCategory> children = findChildren(category);

        if (children.remove(parent)) {
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put(
                    "parents",
                    "Mindestens eine der übergeordneten Kategorien ist auch in den untergeordneten Kategorien enthalten. Es kann nicht als übergeordnete Kategorie angelegt werden.");
            throw new IllegalUserArgumentsException(errorMap);
        }
    }

    private Set<KnowledgeCategory> findChildren(final KnowledgeCategory category) {
        return knowledgeCategoryRepository.findChildren(category);
    }
}

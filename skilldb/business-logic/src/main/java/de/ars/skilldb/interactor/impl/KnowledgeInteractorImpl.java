package de.ars.skilldb.interactor.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.interactor.KnowledgeInteractor;
import de.ars.skilldb.repository.KnowledgeRepository;
import de.ars.skilldb.util.Ensure;
import de.ars.skilldb.util.IllegalUserArgumentsException;

/**
 * Implementierung eines Interactors für Wissensgebiete.
 *
 */
public class KnowledgeInteractorImpl extends AbstractInteractor<Knowledge> implements KnowledgeInteractor {

    private final KnowledgeRepository knowledgeRepository;

    /**
     * Erzeugt eine Instanz der Klasse KnowledgeInteractorImpl.
     *
     * @param knowledgeRepository
     *            Repository für den Zugriff auf die Datenbank
     */
    @Autowired
    protected KnowledgeInteractorImpl(final KnowledgeRepository knowledgeRepository) {
        super(Knowledge.class, knowledgeRepository);
        this.knowledgeRepository = knowledgeRepository;
    }

    @Override
    @Transactional
    public Knowledge add(final Knowledge knowledge) {
        validateKnowledge(knowledge);

        Knowledge existingKnowledge = knowledgeRepository.findByNameCaseInsensitive(knowledge.getName());
        checkNameExisting(existingKnowledge);

        knowledge.setCreated(new Date());
        return knowledgeRepository.save(knowledge);
    }

    @Override
    @Transactional
    public Knowledge update(final Knowledge knowledge) {
        validateKnowledge(knowledge);
        Ensure.that(knowledge.getId()).isNotNull("Die ID des Wissensgebiets darf nicht null sein.");

        Knowledge existingKnowledge = knowledgeRepository.findByNameWithoutOne(knowledge.getName(), knowledge.getId());
        checkNameExisting(existingKnowledge);

        knowledge.setLastModified(new Date());
        return knowledgeRepository.save(knowledge);
    }

    private void validateKnowledge(final Knowledge knowledge) {
        Ensure.that(knowledge).isNotNull("Das übergebene Wissensgebiet darf nicht null sein.");
        Map<String, String> errorMap = new HashMap<String, String>();
        if (knowledge.getName().trim().isEmpty()) {
            errorMap.put("name", "Der Name des Wissensgebiets darf nicht leer sein.");
        }
        if (knowledge.getCategories().isEmpty()) {
            errorMap.put("categories", "Das Wissensgebiet muss mindestens einer Kategorie zugeordnet sein.");
        }
        if (!errorMap.isEmpty()) {
            throw new IllegalUserArgumentsException(errorMap);
        }
    }

    private void checkNameExisting(final Knowledge existingKnowledge) {
        if (existingKnowledge != null) {
            throw new IllegalUserArgumentsException("name", "Es existiert bereits ein Wissensgebiet mit dem Namen %s.",
                    existingKnowledge.getName());
        }
    }
}

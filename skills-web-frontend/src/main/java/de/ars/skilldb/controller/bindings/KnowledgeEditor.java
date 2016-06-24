package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.interactor.KnowledgeInteractor;

/**
 * Klasse zur Konvertierung von Strings in Wissensgebiete.
 */
public class KnowledgeEditor extends PropertyEditorSupport {

    private final KnowledgeInteractor knowledgeInteractor;

    /**
     * Erzeugt einen neuen Knowledge-Editor.
     *
     * @param knowledgeInteractor Interactor, der zur Konvertierung benötigt wird.
     */
    public KnowledgeEditor(final KnowledgeInteractor knowledgeInteractor) {
        super();
        this.knowledgeInteractor = knowledgeInteractor;
    }

    @Override
    public void setAsText(final String text) {
        Long id = Long.parseLong(text);
        setValue(knowledgeInteractor.findOne(id));
    }

    @Override
    public String getAsText() {
        return getValue() == null ? "" : ((Knowledge) getValue()).getName();
    }
}

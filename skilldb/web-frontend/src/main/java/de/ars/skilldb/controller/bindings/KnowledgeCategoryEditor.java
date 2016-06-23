package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.KnowledgeCategory;
import de.ars.skilldb.interactor.KnowledgeCategoryInteractor;

/**
 * Klasse zur Konvertierung von Strings in Wissensgebiets-Kategorien.
 */
public class KnowledgeCategoryEditor extends PropertyEditorSupport {

    private final KnowledgeCategoryInteractor knowledgeCategoryInteractor;

    /**
     * Erzeugt einen neuen KnowledgeCategory-Editor.
     * @param knowledgeCategoryInteractor Interactor, der zur Konvertierung benötigt wird.
     */
    public KnowledgeCategoryEditor(final KnowledgeCategoryInteractor knowledgeCategoryInteractor) {
        super();
        this.knowledgeCategoryInteractor = knowledgeCategoryInteractor;
    }

    @Override
    public void setAsText(final String text) {
        Long id = Long.parseLong(text);
        setValue(knowledgeCategoryInteractor.findOne(id));
    }

    @Override
    public String getAsText() {
        return getValue() == null ? "" : ((KnowledgeCategory) getValue()).getName();
    }
}

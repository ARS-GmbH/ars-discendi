package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.interactor.SkillInteractor;

/**
 * Klasse zur Konvertierung von Strings in Wissensgebiets-Objekte.
 */
public class SkillEditor extends PropertyEditorSupport {

    private final SkillInteractor skillInteractor;

    /**
     * Erzeugt einen neuen Skill-Editor.
     *
     * @param skillInteractor Interactor, der zur Konvertierung benötigt wird.
     */
    public SkillEditor(final SkillInteractor skillInteractor) {
        super();
        this.skillInteractor = skillInteractor;
    }

    @Override
    public void setAsText(final String text) {
        Long id = Long.parseLong(text);
        setValue(skillInteractor.findOne(id));
    }

    @Override
    public String getAsText() {
        return getValue() == null ? "" : ((Skill) getValue()).getKnowledge().getName();
    }
}

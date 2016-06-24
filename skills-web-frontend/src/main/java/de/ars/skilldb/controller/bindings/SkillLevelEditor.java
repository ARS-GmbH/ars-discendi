package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;

import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.interactor.SkillLevelInteractor;

/**
 * Klasse zur Konvertierung von Strings in Skill-Level.
 */
public class SkillLevelEditor extends PropertyEditorSupport {

    private final SkillLevelInteractor skillLevelInteractor;

    /**
     * Erzeugt einen neuen Skill-Level-Editor.
     *
     * @param skillLevelInteractor Interactor, der zur Konvertierung benötigt wird.
     */
    public SkillLevelEditor(final SkillLevelInteractor skillLevelInteractor) {
        super();
        this.skillLevelInteractor = skillLevelInteractor;
    }

    @Override
    public void setAsText(final String text) {
        Long level = Long.parseLong(text);
        setValue(skillLevelInteractor.findOne(level));
    }

    @Override
    public String getAsText() {
        return getValue() == null ?
                "" :
                    ((SkillLevel) getValue())
                    .getInternalValue()
                    .toString() + " "
                + ((SkillLevel) getValue()).getName();
    }
}

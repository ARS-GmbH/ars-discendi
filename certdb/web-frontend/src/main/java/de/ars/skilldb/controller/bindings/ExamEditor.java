package de.ars.skilldb.controller.bindings;

import java.beans.PropertyEditorSupport;
import java.util.Collection;

import de.ars.skilldb.domain.Exam;
import de.ars.skilldb.interactor.ExamInteractor;

/**
 * Editor zur Konvertierung von Strings in Domänenobjekte für die Klasse {@code Brand} .
 */
public class ExamEditor extends PropertyEditorSupport {

    private final ExamInteractor exams;

    /**
     * Erzeugt einen neuen {@code ExamEditor}.
     *
     * @param exams
     *            der {@code ExamInteractor}, der zur Konvertierung genutzt wird.
     */
    public ExamEditor(final ExamInteractor exams) {
        super();
        this.exams = exams;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        Collection<Exam> examList = exams.findByTitleContaining(text, 0, 10);
        if (examList.size() > 1) {
            throw new IllegalArgumentException("More than one exam found. Cannot resolve.");
        }
        setValue(examList.iterator().next());
    }

    @Override
    public String getAsText() {
        return getValue() == null ? "" : ((Exam) getValue()).getTitle();
    }
}

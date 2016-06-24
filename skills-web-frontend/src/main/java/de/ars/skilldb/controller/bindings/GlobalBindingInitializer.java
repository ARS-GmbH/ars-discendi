/**
 *
 */
package de.ars.skilldb.controller.bindings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import de.ars.skilldb.domain.Customer;
import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.KnowledgeCategory;
import de.ars.skilldb.domain.Project;
import de.ars.skilldb.domain.ProjectParticipation;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.domain.User;

/**
 * Registriert alle Editoren.
 */
@ControllerAdvice
public class GlobalBindingInitializer {

    @Autowired
    private KnowledgeEditor knowledgeEditor;

    @Autowired
    private KnowledgeCategoryEditor knowledgeCategoryEditor;

    @Autowired
    private SkillEditor skillEditor;

    @Autowired
    private SkillLevelEditor skillLevelEditor;

    @Autowired
    private UserEditor userEditor;

    @Autowired
    private CustomerEditor customerEditor;

    @Autowired
    private ProjectEditor projectEditor;

    @Autowired
    private ProjectParticipationEditor projectParticipationEditor;

    /**
     * Registriert alle Custom-Editoren global.
     * @param webDataBinder WebDataBinder, zu dem die Editoren hinzugefügt werden
     * @param request WebRequest
     */
    @InitBinder
    public void registerCustomEditors(final WebDataBinder webDataBinder, final WebRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        dateFormat.setLenient(false);

        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.registerCustomEditor(Knowledge.class, knowledgeEditor);
        webDataBinder.registerCustomEditor(KnowledgeCategory.class, knowledgeCategoryEditor);
        webDataBinder.registerCustomEditor(Skill.class, skillEditor);
        webDataBinder.registerCustomEditor(SkillLevel.class, skillLevelEditor);
        webDataBinder.registerCustomEditor(User.class, userEditor);
        webDataBinder.registerCustomEditor(Customer.class, customerEditor);
        webDataBinder.registerCustomEditor(Project.class, projectEditor);
        webDataBinder.registerCustomEditor(ProjectParticipation.class, projectParticipationEditor);
    }

}

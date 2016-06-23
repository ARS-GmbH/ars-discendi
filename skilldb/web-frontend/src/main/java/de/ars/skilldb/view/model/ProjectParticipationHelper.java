/**
 *
 */
package de.ars.skilldb.view.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.ars.skilldb.domain.ProjectParticipation;
import de.ars.skilldb.domain.SkillProofEdge;

/**
 * Hilfsobjekt für die Verwaltung von Projektarbeiten und deren Verknüpfung zu Skills. Es wird
 * verwendet um den Knoten {@link ProjectParticipation} sowie die Kanten
 * {@code PROVES} ({@link SkillProofEdge}), welche zum Skill führen, in einem
 * Formular verwalten zu können. Daher enthält es eine Projektarbeit und deren
 * Nachweiskanten.
 *
 */
public class ProjectParticipationHelper {
    @Override
    public String toString() {
        final int maxLen = 10;
        return "ProjectParticipationHelper [projectParticipation="
                + projectParticipation
                + ", skillProofEdges="
                + (skillProofEdges != null ? skillProofEdges.subList(0, Math.min(skillProofEdges.size(), maxLen))
                        : null) + "]";
    }

    private ProjectParticipation projectParticipation;
    private List<SkillProofEdge> skillProofEdges = new LinkedList<SkillProofEdge>();

    /**
     * Erzeugt ein Objekt der Klasse {@link ProjectParticipationHelper}.
     */
    public ProjectParticipationHelper() {
        super();
    }

    /**
     * Erzeugt ein Objekt der Klasse {@link ProjectParticipationHelper} anhand
     * der übergebenen Projektarbeit.
     *
     * @param projectParticipation
     *            Projektarbeit, zu der ein ProjectParticipationHelper-Objekt
     *            erzeugt werden soll.
     */
    public ProjectParticipationHelper(final ProjectParticipation projectParticipation) {
        this.projectParticipation = projectParticipation;
        skillProofEdges = Lists.newLinkedList(projectParticipation.getSkillProofEdges());

    }

    public ProjectParticipation getProjectParticipation() {
        return projectParticipation;
    }
    public void setProjectParticipation(final ProjectParticipation projectParticipation) {
        this.projectParticipation = projectParticipation;
    }
    public List<SkillProofEdge> getSkillProofEdges() {
        return skillProofEdges;
    }
    public void setSkillProofEdges(final List<SkillProofEdge> skillProofEdges) {
        this.skillProofEdges = skillProofEdges;
    }

    /**
     * Konvertiert das Helper-Objekt in eine komplette Projektarbeit.
     * Setzt dazu bei jeder Kante die Projektarbeit als Nachweis und setzt die Nachweiskanten in der Projektarbeit.
     * @return komplette Projektarbeit
     */
    public ProjectParticipation convertToProjectParticipation() {

        for (SkillProofEdge proofEdge : skillProofEdges) {
            proofEdge.setProof(projectParticipation);
        }

        Set<SkillProofEdge> set = Sets.newHashSet(skillProofEdges);
        projectParticipation.setSkillProofEdges(set);
        return projectParticipation;
    }
}
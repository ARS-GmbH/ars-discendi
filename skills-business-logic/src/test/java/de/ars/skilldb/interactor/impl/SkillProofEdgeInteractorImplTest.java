/**
 *
 */
package de.ars.skilldb.interactor.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import de.ars.skilldb.domain.Knowledge;
import de.ars.skilldb.domain.ProjectParticipation;
import de.ars.skilldb.domain.Proof;
import de.ars.skilldb.domain.Skill;
import de.ars.skilldb.domain.SkillLevel;
import de.ars.skilldb.domain.SkillProofEdge;
import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.SkillProofEdgeInteractor;
import de.ars.skilldb.repository.SkillProofEdgeRepository;

/**
 * Testklasse zum Skill-Interactor.
 *
 */
public class SkillProofEdgeInteractorImplTest {

    private SkillProofEdgeInteractor interactor;
    private SkillProofEdgeRepository skillProofEdgeRepository;
    private static final String KNOWLEDGE_NAME = "Java";
    private static final String USERNAME = "alfred.alfredo";
    private static final int SKILL_LEVEL = 50;

    /**
     * Initiales erzeugen von Objekten, die in den meisten Testfällen benötigt werden.
     */
    @Before
    public void initObjects() {
        skillProofEdgeRepository = Mockito.mock(SkillProofEdgeRepository.class);
        interactor = new SkillProofEdgeInteractorImpl(skillProofEdgeRepository);
        ReflectionTestUtils.setField(interactor, "skillProofs", skillProofEdgeRepository);
    }

    /**
     * Testet das Finden aller Skills.
     */
    @Test
    public void testFindBySkill() {
        Collection<Proof> expected = new ArrayList<Proof>();
        expected.add(new ProjectParticipation());
        Skill skill = generateSkill();

        Mockito.when(skillProofEdgeRepository.findBySkill(skill)).thenReturn(expected);

        Collection<Proof> actual = interactor.findBySkill(skill);

        assertEquals("Response is not equal.", expected, actual);
    }

    /**
     * Testet das Finden aller Kanten.
     */
    @Test
    public void testFindEdgesBySkill() {
        Set<SkillProofEdge> expected = new HashSet<SkillProofEdge>();
        expected.add(new SkillProofEdge());
        Skill skill = generateSkill();

        Mockito.when(skillProofEdgeRepository.findEdgesBySkill(skill)).thenReturn(expected);

        Set<SkillProofEdge> actual = interactor.findEdgesBySkill(skill);

        assertEquals("Response is not equal.", expected, actual);
    }

    private Skill generateSkill() {
        Skill skill = new Skill();
        skill.setKnowledge(generateKnowledge());
        skill.setSubmittedSkillLevel(generateSkillLevel(10));
        skill.setUser(generateUser());
        return skill;
    }

    private Knowledge generateKnowledge() {
        Knowledge knowledge = new Knowledge();
        knowledge.setName(KNOWLEDGE_NAME);
        knowledge.setDescription("Java halt.");
        return knowledge;
    }

    private SkillLevel generateSkillLevel(final int internalValue) {
      SkillLevel skillLevel = new SkillLevel();
      skillLevel.setInternalValue(internalValue);
      skillLevel.setName("Wissen");
      skillLevel.setDescription("Erlerntes in unveränderter Weise erkennen,...");
      skillLevel.setTags("erkennen, ...");
      return skillLevel;
    }

    private User generateUser() {
        User user = new User();
        user.setFirstName("Alfred");
        user.setLastName("Alfredo");
        user.setUserName(USERNAME);
        return user;
    }

}

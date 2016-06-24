package de.ars.skilldb.testfixtures;

import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Abstrakte Testklasse für das Testen von {@code Repositories}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-beans.xml")
public abstract class AbstractIntegrationTest {

    @Autowired
    private ApplicationContext ctx;

    /**
     * Bereitet jeden Tesfall vor.
     */

    @Before
    @SuppressWarnings("rawtypes")
    public void clearAllGraphRepositories() {
        Map<String, CrudRepository> graphRepositories = ctx.getBeansOfType(CrudRepository.class);
        for (CrudRepository graphRepository : graphRepositories.values()) {
            graphRepository.deleteAll();
        }
    }
}

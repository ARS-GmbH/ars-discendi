package de.ars.skilldb.repository;

import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Abstrakte Testklasse, die von allen Repository-Testklassen implementiert wird.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/testBeans.xml")
public abstract class AbstractRepositoryTest {

    @Autowired
    private ApplicationContext context;

    /**
     * Bereitet alle Testfälle vor.
     */
    @Before
    @SuppressWarnings("rawtypes")
    public void clearRepositories() {
        Map<String, CrudRepository> repositories = context.getBeansOfType(CrudRepository.class);
        for (CrudRepository repository : repositories.values()) {
            repository.deleteAll();
        }
    }

}


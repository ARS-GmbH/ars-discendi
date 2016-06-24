/**
 *
 */
package de.ars.skilldb.interactor.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import de.ars.skilldb.interactor.Interactor;
import de.ars.skilldb.util.Ensure;

/**
 * Abstrakte Implementierung eines {@code Interactor}. Implementiert Methoden, die in allen
 * {@code Interactor}-Klassen funktional identisch sind.
 *
 * @param <T>
 *            der Entitäts-Typ, der von dem abstrakten {@code Interactor} verwaltet werden soll.
 */
public abstract class AbstractInteractor<T> implements Interactor<T> {

    @Autowired
    private Neo4jTemplate template;

    private final PagingAndSortingRepository<T, Long> repository;

    /**
     * Instanziiert einen neuen {@code AbstractInteractor}. Damit dieser die Standard-Methoden
     * implementieren kann, wird diesem das {@code Class}-Objekt der zu behandelnden Entität
     * übergeben.
     *
     * @param repository
     *            das {@code Repository}, das für den Datenbankzugriff verwendet werden soll.
     */
    protected AbstractInteractor(final PagingAndSortingRepository<T, Long> repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Achtung: In der Standardimplementierung werden dem Objekt keine weiteren Daten hinzugefügt.
     * Es wird dringend empfohlen, diese Methode zu überschreiben.
     * </p>
     */
    @Override
    public T fetchFullData(final T object) {
        return object;
    }

    public Neo4jTemplate getTemplate() {
        return template;
    }

    public void setTemplate(final Neo4jTemplate template) {
        this.template = template;
    }

    @Override
    public <E extends Collection<T>> E fetchFullData(final E objects) {
        Ensure.that(objects).isNotNull();
        for (T object : objects) {
            fetchFullData(object);
        }
        return objects;
    }

    @Override
    @Transactional
    public T save(final T object) {
        Ensure.that(object).isNotNull("The given object must not be null.");

        return repository.save(object);
    }

    @Override
    public Collection<T> findAll() {
        Iterable<T> result = repository.findAll();
        return Lists.newArrayList(result);
    }

    @Override
    public Collection<T> findAll(final int page, final int count) {
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        Pageable pageable = new PageRequest(page, count, sort);

        return Lists.newArrayList(repository.findAll(pageable));
    }

    @Override
    public T findOne(final Long id) {
        Ensure.that(id).isNotNull();
        Ensure.that(id).isPositive();

        return repository.findOne(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public long count() {
        return repository.count();
    }

}

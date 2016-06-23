package de.ars.skilldb.interactor.impl;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.google.common.collect.Lists;

import de.ars.skilldb.interactor.Interactor;
import de.ars.skilldb.util.Ensure;

/**
 * Abstrakte Implementierung eines {@code Interactor}. Implementiert Methoden,
 * die in allen {@code Interactor}-Klassen funktional identisch sind.
 * @param <T>
 */
public abstract class AbstractInteractor<T> implements Interactor<T> {

    @Autowired
    private Neo4jTemplate template;
    private final Class<T> entityClass;

    private final PagingAndSortingRepository<T, Long> repository;

    /**
     * Instanziiert einen neuen {@code AbstractInteractor}. Damit dieser die
     * Standard-Methoden implementieren kann, wird diesem das {@code Class}
     * -Objekt der zu behandelnden Entität übergeben.
     *
     * @param entityClass
     *            das {@code Class}-Objekt der zu behandelnden Entität.
     *
     * @param repository
     *            das {@code Repository}-Objekt der zu behandelnden Entität.
     */
    protected AbstractInteractor(final Class<T> entityClass, final PagingAndSortingRepository<T, Long> repository) {
        this.entityClass = entityClass;
        this.repository = repository;
    }

    @Override
    public T fetchFullData(final T object) {
        return template.fetch(object);
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
    public Collection<T> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Collection<T> findAll(final Sort sort) {
        return Lists.newArrayList(repository.findAll(sort));
    }

    @Override
    public Collection<T> findAll(final int page, final int size) {
        Pageable pageable = new PageRequest(page, size);
        return Lists.newArrayList(repository.findAll(pageable));
    }

    @Override
    public Collection<T> findAll(final int page, final int size, final Sort sort) {
        Pageable pageable = new PageRequest(page, size, sort);
        return Lists.newArrayList(repository.findAll(pageable));
    }

    @Override
    public Collection<T> findAllWithoutOne(final Long id) {
        Result<T> result = findAllWithoutOneQuery(id);

        return Lists.newArrayList(result);
    }

    @Override
    public Collection<T> findAllWithoutOne(final Long id, final int page, final int size, final Sort sort) {
        Result<T> result = findAllWithoutOneQuery(id);
        Pageable pageable = new PageRequest(page, size, sort);
        Slice<T> slice = result.slice(pageable);

        return Lists.newArrayList(slice.iterator());
    }

    @Override
    public T findOne(final Long id) {
        Ensure.that(id).isNotNull();

        return repository.findOne(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    private Result<T> findAllWithoutOneQuery(final Long id) {
        String query = "START n=node(" + id.toString() + ") MATCH c WHERE NOT c = n RETURN c";
        Result<Map<String, Object>> resultMap = template.query(query, null);
        return resultMap.to(entityClass);
    }

}

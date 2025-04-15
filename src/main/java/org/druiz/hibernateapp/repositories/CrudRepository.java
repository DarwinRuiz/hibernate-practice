package org.druiz.hibernateapp.repositories;

import java.util.List;

public interface CrudRepository<T> {
    List<T> getAll();

    T getById(Integer id);

    void save(T t);

    void delete(T t);
}

package com.amr.project.service.abstracts;

import java.util.List;

public interface ReadWriteService<T, K> {
    void persist(T obj);

    void update(T obj);

    void delete(T obj);

    void deleteByKeyCascadeEnable(K key);

    void deleteByKeyCascadeIgnore(K key);

    boolean existsById(K id);

    T getByKey(K key);

    List<T> getAll();
}

package com.amr.project.dao.abstracts;

import java.util.List;

public interface ReadWriteDao<T,K>{
     List<T> getAll();
     void persist(T obj);
     void update(T obj);
     void delete(T key);
     void deleteByKeyCascadeEnable(K key);
     void deleteByKeyCascadeIgnore(K key);
     boolean existsById(K key);
     T getByKey(K key);
}

package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public abstract class ReadWriteServiceImpl<T, K> implements ReadWriteService<T, K> {

    private ReadWriteDao<T, K> dao;

    @Autowired
    protected ReadWriteServiceImpl(ReadWriteDao<T, K> dao) {
        this.dao = dao;
    }

    protected ReadWriteServiceImpl() {
    }

    @Override
    @Transactional
    public void persist(T obj) {
        dao.persist(obj);
    }

    @Override
    @Transactional
    public void update(T obj) {
        dao.update(obj);
    }

    @Override
    @Transactional
    public void delete(T obj) {
        dao.delete(obj);
    }

    @Override
    @Transactional
    public void deleteByKeyCascadeEnable(K key) {
        dao.deleteByKeyCascadeEnable(key);
    }

    @Override
    @Transactional
    public void deleteByKeyCascadeIgnore(K key) {
        dao.deleteByKeyCascadeIgnore(key);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(K id) {
        return dao.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public T getByKey(K key) {
        return dao.getByKey(key);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return dao.getAll();
    }

}

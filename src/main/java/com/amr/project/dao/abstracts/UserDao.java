package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.User;
import javax.persistence.NoResultException;
import java.util.List;

public interface UserDao extends ReadWriteDao<User, Long> {
    User findByUsername(String username) throws NoResultException;

    User findByEmail(String email) throws NoResultException;

    User findByPhone(String phone) throws NoResultException;

    List<User> findByRole(String role) throws NoResultException;

}


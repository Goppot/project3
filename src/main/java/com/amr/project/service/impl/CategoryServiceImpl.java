package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.dao.impl.CategoryDaoImpl;
import com.amr.project.model.entity.Category;
import com.amr.project.model.entity.Mail;
import com.amr.project.service.abstracts.CategoryService;
import com.amr.project.service.email.EmailSenderService;
import com.amr.project.util.TrackedEmailCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CategoryServiceImpl extends ReadWriteServiceImpl<Category, Long> implements CategoryService {

    private final CategoryDao categoryDao;

    private final TrackedEmailCategory trackedEmailCategory;

    private final EmailSenderService emailSenderService;

    @Autowired
    protected CategoryServiceImpl(CategoryDao categoryDao, TrackedEmailCategory trackedEmailCategory, EmailSenderService emailSenderService) {
        super(categoryDao);
        this.categoryDao = categoryDao;
        this.trackedEmailCategory = trackedEmailCategory;
        this.emailSenderService = emailSenderService;
    }

    @Transactional
    public Category getCategory(String nameCategory) {
        return categoryDao.getCategory(nameCategory);
    }

    @Override
    @Transactional
    public void persist(Category category) {
        emailSenderService.sendSimpleEmail(trackedEmailCategory.trackedEmailCategoryPersist(category));
        categoryDao.persist(category);
    }

    @Override
    @Transactional
    public void update(Category category) {
        Mail mail = trackedEmailCategory.trackedEmailCategoryUpdate(category);
        if (mail.getMessage() != null)
            emailSenderService.sendSimpleEmail(mail);
        categoryDao.update(category);
    }

    @Override
    @Transactional
    public void deleteByKeyCascadeIgnore(Long key) {
        emailSenderService.sendSimpleEmail(trackedEmailCategory.trackedEmailCategoryDelete(key));
        categoryDao.deleteByKeyCascadeIgnore(key);
    }
}
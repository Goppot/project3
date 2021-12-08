package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Category;

public interface CategoryService extends ReadWriteService<Category, Long> {
    public Category getCategory(String nameCategory);

}

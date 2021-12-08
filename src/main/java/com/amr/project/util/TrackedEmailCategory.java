package com.amr.project.util;

import com.amr.project.model.entity.Category;
import com.amr.project.model.entity.Mail;
import com.amr.project.service.impl.TrackedEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackedEmailCategory {

    private final TrackedEmailService trackedEmailService;

    public Mail trackedEmailCategoryPersist(Category category) {
        Mail mail = new Mail();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        mail.setTo(userEmail);
        mail.setMessage("Создалась новая категория:  " + category.getName());
        return mail;
    }

    public Mail trackedEmailCategoryUpdate(Category category) {
        Mail mail = new Mail();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        mail.setTo(userEmail);
        mail.setMessage("Изменилась категория: " + category.getName());
        return mail;
    }

    public Mail trackedEmailCategoryDelete(Long id) {
        Mail mail = new Mail();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        mail.setTo(userEmail);
        mail.setMessage("Удалена категория" + trackedEmailService.getCategoryDao().getByKey(id).getName());
        return mail;
    }

}

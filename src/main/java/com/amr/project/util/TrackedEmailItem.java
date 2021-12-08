package com.amr.project.util;

import com.amr.project.model.entity.Category;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Mail;
import com.amr.project.service.impl.TrackedEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrackedEmailItem {

    private final TrackedEmailService trackedEmailService;

    public Mail trackedEmailItemUpdateDelete(Item item) {

        Mail mail = new Mail();

        //Delete
        if (item.isPretendentToBeDeleted() == true) {
            String message = "Товар удален: " + item.getName() + "в магазине " + item.getShop().getEmail();
            mail.setTo(item.getShop().getEmail());
            mail.setMessage(message);
            return mail;
        }

        //Update
        Item originalItem = trackedEmailService.getItemDao().getByKey(item.getId());
        String message = "Изменился товар: ";

        if (!item.getName().equals(originalItem.getName()))
            message += "name на " + item.getName() + ". ";

        if (!item.getCount().equals(originalItem.getCount()))
            message += "count на " + item.getCount() + ". ";

        if (!item.getPrice().equals(originalItem.getPrice()))
            message += "price на " + item.getPrice() + ". ";

        if (!item.getShop().getName().equals(originalItem.getShop().getName()))
            message += "shopName на " + item.getShop().getName() + ". ";

        if (!item.getDescription().equals(originalItem.getDescription()))
            message += "description на " + item.getDescription() + ". ";

        List<Category> categories = item.getCategories();

        List<Category> categoryOriginal = trackedEmailService.getItemDao().getByKey(item.getId()).getCategories();

        for (Category category : categories) {
            if (!categoryOriginal.contains(category)) {
                message += "Была добавлена category " + category.getName();
            }
        }

        for (Category category : categoryOriginal) {
            if (!categories.contains(category)) {
                message += "Была удалена category " + category.getName();
            }
        }

        mail.setTo(item.getShop().getEmail());
        mail.setMessage(message.length() > 17 ? message : null);

        return mail;
    }

    public Mail trackedEmailItemPersist(Item item) {
        Mail mail = new Mail();
        mail.setTo(item.getShop().getEmail());
        mail.setMessage("Добавлен товар: " + item.getName() + "\n" + item.getDescription());
        return mail;
    }
}
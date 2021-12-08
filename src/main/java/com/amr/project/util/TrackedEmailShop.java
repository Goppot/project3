package com.amr.project.util;

import com.amr.project.model.entity.*;
import com.amr.project.service.impl.TrackedEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrackedEmailShop {

    private final TrackedEmailService trackedEmailService;

    public Mail trackedEmailShopPersist(Shop shop) {
        Mail mail = new Mail();
        mail.setTo(shop.getUser().getEmail());
        mail.setMessage("Создан магазин: " + shop.getName());
        return mail;
    }

    public Mail trackedEmailShopUpdate(Shop shop) {
        Shop shopOriginal = trackedEmailService.getShopService().getByKey(shop.getId());
        Mail mail = new Mail();

        String message = "Изменились категории магазина: ";

        if (!shopOriginal.getName().equals(shop.getName()))
            message += "name " + shop.getName() + ". ";
        if (!shopOriginal.getEmail().equals(shop.getEmail()))
            message += "email " + shop.getEmail() + ". ";
        if (shopOriginal.getActivity() != shop.getActivity())
            message += "activity " + shop.getActivity() + ". ";
        if (!shopOriginal.getDescription().equals(shop.getDescription()))
            message += "description " + shop.getDescription() + ". ";
        if (!shopOriginal.getPhone().equals(shop.getPhone()))
            message += "phone " + shop.getPhone() + ". ";
        if (!shopOriginal.getLocation().getName().equals(shop.getLocation().getName()))
            message += "name " + shop.getName() + ". ";

        List<Discount> discountsOriginal = (List<Discount>) shopOriginal.getDiscounts();
        List<Discount> discounts = (List<Discount>) shop.getDiscounts();

        if (discounts != null) {
            for (Discount discount : discounts) {
                if (!discountsOriginal.contains(discount))
                    message += "Был добавлен discount " + discount.getShop().getName() + ". ";
            }
        }

        if (shop.getUser() != null) {
            mail.setTo(shop.getUser().getEmail());
        }
        mail.setMessage(message.length() > 31 ? message : null);

        return mail;
    }

    public Mail trackedEmailShopDelete(Shop shop) {
        Mail mail = new Mail();
        mail.setTo(shop.getUser().getEmail());
        mail.setMessage("Магазин удален: " + shop.getName());
        return mail;
    }


}

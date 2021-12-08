package com.amr.project.util;

import com.amr.project.model.entity.Coupon;
import com.amr.project.model.entity.Discount;
import com.amr.project.model.entity.Mail;
import com.amr.project.model.entity.User;
import com.amr.project.service.impl.TrackedEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrackedEmailUser {

    private final TrackedEmailService trackedEmailService;

    public Mail trackedEmailUserUpdate(User user, String password) {
        Mail mail = new Mail();
        String message = "Пользователь изменилься: ";

        User userOriginal = trackedEmailService.getUserDao().getByKey(user.getId());

        if (!userOriginal.getEmail().equals(user.getEmail()))
            message += "email: " + user.getEmail();
        if (!userOriginal.getAddress().equals(user.getAddress()))
            message += "address: " + user.getAddress();
        if (userOriginal.getAge() != user.getAge())
            message += "age: " + user.getAge();
        if (!userOriginal.getPassword().equals(user.getPassword()))
            message += "password: " + password;
        if (!userOriginal.getBirthday().equals(user.getBirthday()))
            message += "birthday: " + user.getBirthday();
        if (!userOriginal.getFirstName().equals(user.getFirstName()))
            message += "firstName: " + user.getFirstName();
        if (!userOriginal.getGender().name().equals(user.getGender().name()))
            message += "gender: " + user.getGender().name();
        if (!userOriginal.getUsername().equals(user.getUsername()))
            message += "username: " + user.getUsername();
        if (!userOriginal.getImages().getPicture().equals(user.getImages().getPicture()))
            message += "images: " + "*****";

        List<Coupon> couponsOriginal = userOriginal.getCoupons();
        List<Coupon> coupons = user.getCoupons();
        for (Coupon coupon : coupons) {
            if (!couponsOriginal.contains(coupon))
                message += "Был добавлен coupon: " + coupon;
        }

        for (Coupon coupon : couponsOriginal) {
            if (!coupons.contains(coupon))
                message += "Был удален coupon: " + coupon;
        }

        List<Discount> discountsOriginal = userOriginal.getDiscounts();
        List<Discount> discounts = user.getDiscounts();

        for (Discount discount : discounts) {
            if (!discountsOriginal.contains(discount))
                message += "Был добавлин discount: " + discount.toString();
        }

        for (Discount discount : discountsOriginal) {
            if (!discounts.contains(discount))
                message += "Был удален discount: " + discount.toString();
        }

        mail.setTo(user.getEmail());
        mail.setMessage(message);
        return mail;
    }

    public Mail trackedEmailUserDelete(User user) {
        Mail mail = new Mail();
        String message = "Вы были удалены: " + user.getEmail();
        mail.setTo(user.getEmail());
        mail.setMessage(message);
        return mail;
    }


}

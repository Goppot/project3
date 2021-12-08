package com.amr.project.util;

import com.amr.project.model.entity.Mail;
import com.amr.project.model.entity.Order;
import com.amr.project.service.impl.TrackedEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackedEmailOrder {

    private final TrackedEmailService trackedEmailService;


    public Mail trackedEmailOrderPersist(Order order) {
        Mail mail = new Mail();
        mail.setTo(order.getUser().getEmail());
        mail.setMessage("Был оформлен заказа " + order.getDate() + order.getId() + "Статутс заказа" + order.getStatus());
        return mail;
    }

    public Mail trackedEmailOrderUpdate(Order order) {
        Mail mail = new Mail();
        Order orderOriginal = trackedEmailService.getOrderDao().getByKey(order.getId());
        String message = "";
        if (!orderOriginal.getStatus().equals(order.getStatus()))
            message += "Изменился статус заказа на" + order.getStatus();
        mail.setTo(order.getUser().getEmail());
        mail.setMessage(message.length() > 5 ? message : null);
        return mail;
    }

    public Mail trackedEmailOrderDelete(Order order) {
        Mail mail = new Mail();
        mail.setTo(order.getUser().getEmail());
        mail.setMessage("Заказ был отменен" + order.getId());
        return mail;
    }

}

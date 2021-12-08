package com.amr.project.webapp.controller;

import com.amr.project.model.entity.Order;
import com.amr.project.service.abstracts.OrderService;
import com.amr.project.service.impl.OrderServiceImpl;
import com.amr.project.webapp.handler.PaymentApi;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentApi paymentApi;
    private final OrderServiceImpl orderServiceImlp;

    @Autowired
    public PaymentController(PaymentApi paymentApi,
                             OrderServiceImpl orderServiceImlp) {
        this.paymentApi = paymentApi;
        this.orderServiceImlp = orderServiceImlp;
    }
    @GetMapping("/{id}")
    public String getPayment(@PathVariable Long id) {
        Order order = orderServiceImlp.getByKey(id);
        return "redirect: " + paymentApi.payUrl(order);
    }

}

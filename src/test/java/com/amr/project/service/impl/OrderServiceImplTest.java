package com.amr.project.service.impl;

import com.amr.project.AbstractIntegrationTest;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.OrderService;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@DBRider
@DBUnit(caseSensitiveTableNames = true, allowEmptyFields = true, schema = "platform")
@DataSet(value = {"Orders.xml","User-orders.xml", "Orders-item.xml"})
class OrderServiceImplTest extends AbstractIntegrationTest {
    private final OrderService orderService;

    @Autowired
    public OrderServiceImplTest(OrderService orderService) {
        this.orderService = orderService;
    }

    @Test
    void changeStatusToPaid() {
        long idTestingOrder = 1L;
        orderService.changeStatusToPaid(idTestingOrder);
        Assertions.assertEquals(Status.PAID, orderService.getByKey(idTestingOrder).getStatus());
    }

    @Test
    void changeStatusToSent() {
        long idTestingOrder = 1L;
        orderService.changeStatusToSent(idTestingOrder);
        Assertions.assertEquals(Status.SENT, orderService.getByKey(idTestingOrder).getStatus());
    }

    @Test
    void changeStatusToDelivered() {
        long idTestingOrder = 1L;
        orderService.changeStatusToDelivered(idTestingOrder);
        Assertions.assertEquals(Status.DELIVERED, orderService.getByKey(idTestingOrder).getStatus());
    }

    @Test
    void changeStatusToCompleted() {
        long idTestingOrder = 1L;
        orderService.changeStatusToCompleted(idTestingOrder);
        Assertions.assertEquals(Status.COMPLETE, orderService.getByKey(idTestingOrder).getStatus());
    }
}
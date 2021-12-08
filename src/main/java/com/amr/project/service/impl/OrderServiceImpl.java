package com.amr.project.service.impl;

import com.amr.project.converter.ItemMapper;
import com.amr.project.dao.abstracts.OrderDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.impl.OrderDaoImpl;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.*;
import com.amr.project.model.enums.PaymentMethod;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.OrderService;
import com.amr.project.service.email.EmailSenderService;
import com.amr.project.util.TrackedEmailOrder;
import com.amr.project.webapp.paypalsettings.CheckOutInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl extends ReadWriteServiceImpl<Order, Long> implements OrderService {

    private final OrderDao orderDao;
    private final EmailSenderService emailSenderService;
    private final TrackedEmailOrder trackedEmailOrder;
    private ItemMapper itemMapper;

    public OrderServiceImpl(@Qualifier("userPageOrderDaoImpl") ReadWriteDao<Order, Long> dao,
                            OrderDao orderDao,
                            EmailSenderService emailSenderService,
                            TrackedEmailOrder trackedEmailOrder) {
        super(dao);
        this.orderDao = orderDao;
        this.emailSenderService = emailSenderService;
        this.trackedEmailOrder = trackedEmailOrder;
        this.itemMapper = itemMapper;
    }

    @Override
    public void persist(Order order) {
        emailSenderService.sendSimpleEmail(trackedEmailOrder.trackedEmailOrderPersist(order));
        orderDao.persist(order);
    }

    @Override
    public void update(Order order) {
//        Mail mail = trackedEmailOrder.trackedEmailOrderUpdate(order);
//        if (mail != null)
//            emailSenderService.sendSimpleEmail(mail);
        orderDao.update(order);
    }

    @Override
    public void delete(Order order) {
        emailSenderService.sendSimpleEmail(trackedEmailOrder.trackedEmailOrderDelete(order));
        orderDao.delete(order);
    }

    @Override
    public void changeStatusToPaid(long order_id) {
        Order order = orderDao.getByKey(order_id);
        order.setStatus(Status.PAID);
        this.update(order);
    }

    @Override
    public void changeStatusToSent(long order_id) {
        Order order = orderDao.getByKey(order_id);
        order.setStatus(Status.SENT);
        this.update(order);
    }

    @Override
    public void changeStatusToDelivered(long order_id) {
        Order order = orderDao.getByKey(order_id);
        order.setStatus(Status.DELIVERED);
        this.update(order);
    }

    @Override
    public void changeStatusToCompleted(long order_id) {
        Order order = orderDao.getByKey(order_id);
        order.setStatus(Status.COMPLETE);
        this.update(order);
    }

    @Override
    public void updateAddressAndUserInfo(Long id, OrderDto orderDto) {

    }

    @Override
    public void deleteItemInOrder(Long orderId, Long itemId) {

    }

    //метод для страницы order_page
    @Override
    public Order collectOrderByUserAndItems(List<ItemDto> items, User user) {
        Order order = new Order();
        order.setItems(itemMapper.toItems(items));
        order.setAddress(user.getAddress());
        order.setUser(user);
        order.setBuyerName(user.getFirstName());
        order.setBuyerPhone(user.getPhone());

        BigDecimal total = items.stream()
                .map(i -> i.getPrice())
                .reduce((s1, s2) -> s1.add(s2))
                .get();

        order.setTotal(total);
        super.persist(order);
        return order;
    }

    /**
     * Метод размещения заказа с учетом платежной системы
     * TODO: реализовать с учетом возможной пошлины, стоимости доставки, отслеживания статуса
     */

    @Override
    public Order createOrder(List<ItemDto> items, User user, PaymentMethod paymentMethod,
                             CheckOutInfo checkOutInfo, Address address) {

        Order order = new Order();

        order.setItems(itemMapper.toItems(items));
        order.setUser(user);
        order.setBuyerName(user.getFirstName());
        order.setBuyerPhone(user.getPhone());

        if (address == null) {
            order.setAddress(user.getAddress());
        } else {
            order.setAddress(address);
        }

        order.setTax(0.0f);
        order.setShippingCost(0.0f);
        order.setDeliverDate(checkOutInfo.getDeliverDate());

        BigDecimal total = items.stream()
                .map(i -> i.getPrice())
                .reduce((s1, s2) -> s1.add(s2))
                .get();

        if (paymentMethod.equals(PaymentMethod.PAYPAL)) {
            order.setStatus(Status.PAID);
        } else {
            order.setStatus(Status.START);
        }

        order.setTotal(total);
        order.setDate(Calendar.getInstance());
        super.persist(order);
        return order;
    }
}

package com.amr.project.service.abstracts;

import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;

import java.util.List;

public interface UserPageOrderService extends ReadWriteService<Order, Long> {
    List<OrderDto> getOrdersByUserId(Long userId);
}

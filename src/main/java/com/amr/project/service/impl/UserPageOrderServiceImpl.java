package com.amr.project.service.impl;

import com.amr.project.converter.OrderMapper;
import com.amr.project.dao.abstracts.UserPageOrderDao;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;
import com.amr.project.service.abstracts.UserPageOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserPageOrderServiceImpl extends ReadWriteServiceImpl<Order, Long> implements UserPageOrderService {

    private final UserPageOrderDao userPageOrderDao;
    private final OrderMapper orderMapper;

    public UserPageOrderServiceImpl(UserPageOrderDao userPageOrderDao, OrderMapper orderMapper) {
        this.userPageOrderDao = userPageOrderDao;
        this.orderMapper = orderMapper;
    }


    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderMapper.listOrderToDto(userPageOrderDao.getOrdersByUserId(userId));
    }
}

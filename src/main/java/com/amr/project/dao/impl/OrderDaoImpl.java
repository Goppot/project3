package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.OrderDao;
import com.amr.project.model.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends ReadWriteDaoImpl<Order, Long> implements OrderDao {
}

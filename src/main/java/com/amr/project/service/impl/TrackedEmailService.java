package com.amr.project.service.impl;

import com.amr.project.dao.impl.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class TrackedEmailService {

    private final ItemDaoImpl itemDao;

    private final CategoryDaoImpl categoryDao;

    private final ReviewDaoImpl reviewDao;

    private final ShopDaoImpl shopService;

    private final UserDaoImpl userDao;

    private final OrderDaoImpl orderDao;

}

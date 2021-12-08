package com.amr.project.inserttestdata.repository;

import com.amr.project.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    public Optional<CartItem> findCartItemByUser_IdAndItem_Id(Long usrId, Long itmId);
}

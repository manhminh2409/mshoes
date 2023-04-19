package com.mshoes.mshoes.repositories;

import com.mshoes.mshoes.models.OrderDetail;
import com.mshoes.mshoes.models.OrderItem;
import com.mshoes.mshoes.models.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    OrderItem findBySizeAndOrderDetail(Size size, OrderDetail orderDetail);
}

package com.mshoes.mshoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mshoes.mshoes.models.OrderDetail;
import com.mshoes.mshoes.models.User;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	// Lấy thông tin giỏ hàng chưa thanh toán ( type = 0)
	OrderDetail findByUserAndType(User user, int type);
}

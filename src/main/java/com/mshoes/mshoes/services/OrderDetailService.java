package com.mshoes.mshoes.services;

import com.mshoes.mshoes.models.requested.OrderItemRequest;
import com.mshoes.mshoes.models.response.OrderItemResponse;

import java.util.List;

public interface OrderDetailService {

    //Thêm mới item vào order khi người dùng đã đăng nhập
    OrderItemResponse addToOrder(Long userId, OrderItemRequest orderItemRequest);

    //Lấy danh sách giỏ hàng (orderDetail có type = 0)
    List<OrderItemResponse> getItemFromCart(Long userId);
}

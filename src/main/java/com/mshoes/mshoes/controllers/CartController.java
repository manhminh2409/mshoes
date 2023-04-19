package com.mshoes.mshoes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mshoes.mshoes.models.requested.OrderItemRequest;
import com.mshoes.mshoes.repositories.UserRepository;
import com.mshoes.mshoes.services.OrderDetailService;
import com.mshoes.mshoes.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private final OrderDetailService orderDetailService;

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final JwtUtils jwtUtils;

	public CartController(OrderDetailService orderDetailService, UserRepository userRepository, JwtUtils jwtUtils) {
		this.orderDetailService = orderDetailService;
		this.userRepository = userRepository;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/addToCart")
	public ResponseEntity<?> addToCart(@RequestBody OrderItemRequest orderItemRequest, HttpServletRequest request) {
		String token = jwtUtils.getTokenFromCookie(request); // Lấy token từ cookie
		if (token == null) {
			return new ResponseEntity<>("Đăng nhập để thêm vào giỏ hàng của bạn", HttpStatus.OK);
		} else {
			Long userId = jwtUtils.getUserIdFromToken(token); // Lấy id người dùng từ token
			// Sử dụng id người dùng để thêm sản phẩm vào giỏ hàng
			return ResponseEntity.ok(orderDetailService.addToOrder(userId, orderItemRequest));
		}
	}

}

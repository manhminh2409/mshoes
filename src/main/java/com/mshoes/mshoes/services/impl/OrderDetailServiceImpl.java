package com.mshoes.mshoes.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mshoes.mshoes.exception.ResourceNotFoundException;
import com.mshoes.mshoes.libraries.Utilities;
import com.mshoes.mshoes.mapper.OrderItemMapper;
import com.mshoes.mshoes.mapper.ProductMapper;
import com.mshoes.mshoes.models.Color;
import com.mshoes.mshoes.models.OrderDetail;
import com.mshoes.mshoes.models.OrderItem;
import com.mshoes.mshoes.models.Product;
import com.mshoes.mshoes.models.Size;
import com.mshoes.mshoes.models.User;
import com.mshoes.mshoes.models.requested.OrderItemRequest;
import com.mshoes.mshoes.models.response.OrderItemResponse;
import com.mshoes.mshoes.repositories.OrderDetailRepository;
import com.mshoes.mshoes.repositories.OrderItemRepository;
import com.mshoes.mshoes.repositories.SizeRepository;
import com.mshoes.mshoes.repositories.UserRepository;
import com.mshoes.mshoes.services.OrderDetailService;

import jakarta.transaction.Transactional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	private final OrderDetailRepository orderDetailRepository;

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final SizeRepository sizeRepository;

	@Autowired
	private final OrderItemRepository orderItemRepository;

	@Autowired
	private final ProductMapper productMapper;

	@Autowired
	private final OrderItemMapper orderItemMapper;

	@Autowired
	private final Utilities utilities;

	public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, UserRepository userRepository,
			SizeRepository sizeRepository, OrderItemRepository orderItemRepository, ProductMapper productMapper,
			OrderItemMapper orderItemMapper, Utilities utilities) {
		this.orderDetailRepository = orderDetailRepository;
		this.userRepository = userRepository;
		this.sizeRepository = sizeRepository;
		this.orderItemRepository = orderItemRepository;
		this.productMapper = productMapper;
		this.orderItemMapper = orderItemMapper;
		this.utilities = utilities;
	}

	@Transactional
	@Override
	public OrderItemResponse addToOrder(Long userId, OrderItemRequest orderItemRequest) {

		// mapper requestedOrderItem với OrderItem
		OrderItem orderItem = new OrderItem();

		// Lưu thông tin mới
		Size size = sizeRepository.findById(orderItemRequest.getSizeId())
				.orElseThrow(() -> new ResourceNotFoundException("Size", "id", orderItemRequest.getSizeId()));

		orderItem.setSize(size);
		orderItem.setQuantity(orderItemRequest.getQuantity());

		// Lấy thông tin người dùng đang đăng nhập
		User user = userRepository.findById(userId).orElseThrow();

		// Lấy thông tin của color
		Color color = size.getColor();

		// lấy thông tin product
		Product product = color.getProduct();
		// lưu và trả về kết quả

		// Lấy danh sách order của user trên và type = 0: chưa thanh toán
		OrderDetail checkOrderDetail = orderDetailRepository.findByUserAndType(user, 0);

		if (checkOrderDetail == null) {
			// trường hợp không còn giỏ hàng nào chưa thanh toán
			// Tạo 1 giỏ hàng mới với user sở hữu hiện tại, createdDate và type = 0
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setUser(user);
			orderDetail.setCreatedDate(utilities.getCurrentDate());
			orderDetail.setType(0);

			// Lưu giỏ hàng mới vào csdl
			OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);

			// Lưu thông tin OrderDetail cho OrderItem
			orderItem.setOrderDetail(newOrderDetail);
			OrderItem newOrderItem = orderItemRepository.save(orderItem);

			OrderItemResponse orderItemResponse = new OrderItemResponse();
			orderItemResponse.setQuantity(newOrderItem.getQuantity());
			orderItemResponse.setProductItemResponse(productMapper.mapModelToProductItemResponse(product));
			orderItemResponse.setSize(size.getValue());
			orderItemResponse.setColor(color.getValue());

			return orderItemResponse;
		} else {
			// Kiểm tra giỏ hàng đã tồn tại OrderItem này chưa
			Optional<OrderItem> checkOrderItem = Optional
					.ofNullable(orderItemRepository.findBySizeAndOrderDetail(size, checkOrderDetail));

			if (checkOrderItem.isEmpty()) {
				// Nếu chưa có Item đó trong giỏ hàng
				// Lưu thông tin OrderDetail cho OrderItem
				orderItem.setOrderDetail(checkOrderDetail);
				OrderItem newOrderItem = orderItemRepository.save(orderItem);

				OrderItemResponse orderItemResponse = new OrderItemResponse();
				orderItemResponse.setQuantity(newOrderItem.getQuantity());
				orderItemResponse.setProductItemResponse(productMapper.mapModelToProductItemResponse(product));
				orderItemResponse.setSize(size.getValue());
				orderItemResponse.setColor(color.getValue());

				return orderItemResponse;
			} else {
				OrderItem oldOrderItem = checkOrderItem
						.orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", -1L));
				// Nếu đã tồn tại Item trong giỏ hàng
				// Cập nhật lại số lượng
				int quantity = oldOrderItem.getQuantity() + orderItemRequest.getQuantity();

				oldOrderItem.setQuantity(quantity);

				OrderItem newOrderItem = orderItemRepository.save(oldOrderItem);

				OrderItemResponse orderItemResponse = new OrderItemResponse();
				orderItemResponse.setQuantity(newOrderItem.getQuantity());
				orderItemResponse.setProductItemResponse(productMapper.mapModelToProductItemResponse(product));
				orderItemResponse.setSize(size.getValue());
				orderItemResponse.setColor(color.getValue());

				return orderItemResponse;
			}
		}
	}

	@Override
	public List<OrderItemResponse> getItemFromCart(Long userId) {
		//Lấy thông tin giỏ hàng đang được set type = 0: định dạng chu
		return null;
	}
}

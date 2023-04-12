package com.mshoes.mshoes.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ORDER_ITEM")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private int quality;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "detail_id")
	private OrderDetail orderDetail;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "size_id")
	private  Size size;
}

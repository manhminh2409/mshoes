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
	@JoinColumn(name = "variant_value_id", referencedColumnName = "id")
	private VariantValue variantValue;
}

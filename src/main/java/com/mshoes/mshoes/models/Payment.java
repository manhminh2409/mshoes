package com.mshoes.mshoes.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "PAYMENT")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String type;

	@OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private OrderDetail orderDetail;
}

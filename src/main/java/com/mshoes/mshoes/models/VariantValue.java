package com.mshoes.mshoes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@Table(name = "VARIANT_VALUE")
public class VariantValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private int price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_variant_id", referencedColumnName = "id")
	private ProductVariant productVariant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_value_id", referencedColumnName = "id")
	private OptionValue optionValue;

	@ManyToOne
	@JoinColumn(name = "product_option_id", referencedColumnName = "option_id")
	private ProductOption productOption;

	@OneToOne(mappedBy = "variantValue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private OrderItem orderItem;

	@OneToOne(mappedBy = "variantValue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Inventory inventory;
}

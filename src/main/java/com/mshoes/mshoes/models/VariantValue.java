package com.mshoes.mshoes.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

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
	@JoinColumn(name = "option_id", referencedColumnName = "id")
	private Option option;

	@OneToOne(mappedBy = "variantValue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private OrderItem orderItem;

	@OneToOne(mappedBy = "variantValue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Inventory inventory;
}

package com.mshoes.mshoes.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "PRODUCT_VARIANT")
public class ProductVariant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String sku;

	@Column
	private int price;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "option_id")
	private  Option option;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "option_value_id")
	private  OptionValue optionValue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL)
	private Set<VariantValue> variantValues = new HashSet<>();

}

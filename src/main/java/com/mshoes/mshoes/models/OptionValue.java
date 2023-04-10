package com.mshoes.mshoes.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
@Table(name = "OPTION_VALUE")
public class OptionValue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_id")
	private Option option;

	@OneToMany(mappedBy = "optionValue", cascade = CascadeType.ALL)
	private List<ProductVariant> productVariants = new ArrayList<>();

	@OneToMany(mappedBy = "optionValue", cascade = CascadeType.ALL)
	private List<VariantValue> variantValues = new ArrayList<>();

}

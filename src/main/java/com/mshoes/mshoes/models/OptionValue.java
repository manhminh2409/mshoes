package com.mshoes.mshoes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
	private Set<VariantValue> variantValues = new HashSet<>();

}

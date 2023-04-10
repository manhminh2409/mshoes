package com.mshoes.mshoes.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OPTION")
public class Option {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToMany(mappedBy = "option", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ProductVariant> productVariants = new ArrayList<>();

	@OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
	private List<OptionValue> optionValues = new ArrayList<>();
}

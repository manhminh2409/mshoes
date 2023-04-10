package com.mshoes.mshoes.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "PRODUCT")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	@Column
	private String description;

	@Column
	private int visited;

	@Column(nullable = false)
	private String createdDate;

	@Column
	private String modifiedDate;

	@Column
	private int status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_category_id")
	private Category category;

	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Image> images = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_author_id")
	private User user;

	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Option> options = new ArrayList<>();

	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ProductVariant> productVariants = new HashSet<>();
}

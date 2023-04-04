package com.mshoes.mshoes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "OPTION")
public class Option {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String name;

	@ManyToMany(mappedBy = "options")
	private Set<Product> products = new HashSet<>();

	@OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
	private List<OptionValue> optionValues = new ArrayList<>();
}

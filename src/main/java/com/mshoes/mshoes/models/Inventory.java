package com.mshoes.mshoes.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "INVENTORY")
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private int total;

	@Column
	private int sold;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "variant_value_id", referencedColumnName = "id")
	private VariantValue variantValue;
}

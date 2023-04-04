package com.mshoes.mshoes.repositories;

import com.mshoes.mshoes.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAll();
	List<Product> findByCategoryId(long categoryId);

	@Transactional
	@Modifying
	@Query("UPDATE Product p SET p.visited = p.visited + 1 WHERE p.id = :id")
	void incrementVisitedById(@Param("id") Long id);

}

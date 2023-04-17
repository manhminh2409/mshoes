package com.mshoes.mshoes.repositories;

import com.mshoes.mshoes.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findAll(Pageable pageable);

	Page<Product> findByCategoryId(long categoryId, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE "+
			"p.name LIKE CONCAT('%', :search, '%') OR "+
			"p.description LIKE CONCAT('%', :search, '%')")
	List<Product> searchProducts(@Param("search") String search);

	@Transactional
	@Modifying
	@Query("UPDATE Product p SET p.visited = p.visited + 1 WHERE p.id = :id")
	void incrementVisitedById(@Param("id") Long id);

	@Query("select max(p.id) from Product p")
	Long findNewestId();
}

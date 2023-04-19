package com.mshoes.mshoes.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mshoes.mshoes.models.requested.ProductRequest;
import com.mshoes.mshoes.models.response.ProductResponse;
import com.mshoes.mshoes.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	// Create product POST
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {

		return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
	}

	// get All products
	@GetMapping
	public ResponseEntity<Page<ProductResponse>> findAllProducts(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		Page<ProductResponse> products = productService.getAllProducts(pageNumber, pageSize, sortBy);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	// get All products
	@GetMapping("/category/{id}")
	public ResponseEntity<Page<ProductResponse>> getProductsByCategoryId(@PathVariable(name = "id") Long categoryId,
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		Page<ProductResponse> productResponses = productService.getProductsByCategoryId(categoryId, pageNumber,
				pageSize, sortBy);
		return new ResponseEntity<>(productResponses, HttpStatus.OK);
	}

	// get products by searching
	@GetMapping("/search")
	public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String search) {
		Optional<List<ProductResponse>> productResponses = productService.searchProducts(search);
		return productResponses.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// get Product by ID
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable(name = "id") Long productId) {
		return ResponseEntity.ok(productService.getProductById(productId));
	}

	// Update Product by ID
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest,
			@PathVariable(name = "id") long productId) {
		ProductResponse productResponse = productService.updateProduct(productRequest, productId);

		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	// Delete Product by ID
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") long productId) {

		productService.deleteProductById(productId);

		return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
	}
}

package com.mshoes.mshoes.controllers.admin;

import com.mshoes.mshoes.models.dtos.ProductDTO;
import com.mshoes.mshoes.models.requested.RequestedProduct;
import com.mshoes.mshoes.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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
	public ResponseEntity<ProductDTO> createProduct(@RequestBody RequestedProduct requestedProduct) {

		return new ResponseEntity<>(productService.createProduct(requestedProduct), HttpStatus.CREATED);
	}

	// get All products
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAllProducts(
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		Page<ProductDTO> products = productService.getAllProducts(pageNumber, pageSize, sortBy);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	// get All products
	@GetMapping("/category/{id}")
	public ResponseEntity<Page<ProductDTO>> getProductsByCategoryId(
			@PathVariable(name = "id") Long categoryId,
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		Page<ProductDTO> productDTOS = productService.getProductsByCategoryId(categoryId,pageNumber, pageSize, sortBy);
		return new ResponseEntity<>(productDTOS,HttpStatus.OK);
	}

	//get products by searching
	@GetMapping("/search")
	public  ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String search){
		Optional<List<ProductDTO>> products = productService.searchProducts(search);
		if (products.isPresent()) {
			return ResponseEntity.ok(products.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	// get Product by ID
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") Long productId) {
		return ResponseEntity.ok(productService.getProductById(productId));
	}

	// Update Product by ID
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody RequestedProduct requestedProduct,
			@PathVariable(name = "id") long productId) {
		ProductDTO productResponse = productService.updateProduct(requestedProduct, productId);

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

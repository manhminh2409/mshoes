package com.mshoes.mshoes.controllers;

import com.mshoes.mshoes.models.DTO.CategoryDTO;
import com.mshoes.mshoes.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@GetMapping
	public List<CategoryDTO> getAllCategory() {
		return categoryService.getAllcategorys();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(name = "id") Long categoryId) {
		return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
		return new ResponseEntity<>(categoryService.createCategory(categoryDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable(name = "id") long categoryId,
			@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO responseUser = categoryService.updateCategory(categoryDTO, categoryId);

		return new ResponseEntity<>(responseUser, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") long categoryId) {

		try {
			categoryService.deleteCategory(categoryId);

			return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("Delete fail!!", HttpStatus.OK);
		}
	}
}

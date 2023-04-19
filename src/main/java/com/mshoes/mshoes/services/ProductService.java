package com.mshoes.mshoes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.mshoes.mshoes.models.requested.ProductRequest;
import com.mshoes.mshoes.models.response.ProductResponse;

public interface ProductService {
	/**
	 * Method get all product is enable(product_status=1) in database <br>
	 * <u><i>Update: 02/03/2023</i></u>
	 *
	 * @return ProductDTO
	 */
	Page<ProductResponse> getAllProducts(int pageNumber, int pageSize, String sortBy);

	/**
	 * Method get all product is enabled (product_status=1) by category in database
	 * <br>
	 * <u><i>Update: 06/03/2023</i></u>
	 *
	 * @return ProductDTO
	 */
	Page<ProductResponse> getProductsByCategoryId(long categoryId, int pageNumber, int pageSize, String sortBy);

	/**
	 * Method get products with String "search" <br>
	 * <u><i>Update: 16/04/2023</i></u>
	 * 
	 * @param search
	 * @return
	 */
	Optional<List<ProductResponse>> searchProducts(String search);

	/**
	 * Method get a product by product_id in database <br>
	 * <u><i>Update: 02/03/2023</i></u>
	 *
	 * @param productID
	 * @return
	 */
	ProductResponse getProductById(long productID);

	/**
	 * Method create new product<br>
	 * <u><i>Update: 02/03/2023</i></u>
	 *
	 * @param productRequest
	 * @return
	 */
	ProductResponse createProduct(ProductRequest productRequest);

	/**
	 * Method update detail a product with new infomation and product_id<br>
	 * <u><i>Update: 02/03/2023</i></u>
	 *
	 * @param productRequest
	 * @param productId
	 * @return
	 */
	ProductResponse updateProduct(ProductRequest productRequest, long productId);

	/**
	 * Method delete (change product_status to value 0) change enable product to
	 * disable product<br>
	 * <u><i>Update: 02/03/2023</i></u>
	 *
	 * @param productId
	 * @return
	 */
	void deleteProductById(long productId);
}

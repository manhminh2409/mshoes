package com.mshoes.mshoes.services;

import com.mshoes.mshoes.models.dtos.ProductDTO;
import com.mshoes.mshoes.models.dtos.RequestedProduct;

import java.util.List;

public interface ProductService {

	/**
	 * Method get all product is enable(product_status=1) in database <br>
	 * <u><i>Update: 02/03/2023</i></u>
	 *
	 * @return
	 */
	List<ProductDTO> getAllProducts();

	/**
	 * Method get all product is enabled (product_status=1) by category in database
	 * <br>
	 * <u><i>Update: 06/03/2023</i></u>
	 *
	 * @return
	 */
	List<ProductDTO> getProductsByCategoryId(Long categoryId);

	/**
	 * Method get a product by product_id in database <br>
	 * <u><i>Update: 02/03/2023</i></u>
	 *
	 * @param productID
	 * @return
	 */
	ProductDTO getProductById(Long productID);

	/**
	 * Method create new product<br>
	 * <u><i>Update: 02/03/2023</i></u>
	 *
	 * @param requestedProduct
	 * @return
	 */
	ProductDTO createProduct(RequestedProduct requestedProduct);

	/**
	 * Method update detail a product with new infomation and product_id<br>
	 * <u><i>Update: 02/03/2023</i></u>
	 *
	 * @param requestedProduct
	 * @param productId
	 * @return
	 */
	ProductDTO updateProduct(RequestedProduct requestedProduct, long productId);

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

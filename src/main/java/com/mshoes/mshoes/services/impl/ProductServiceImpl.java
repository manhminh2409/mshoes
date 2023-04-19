package com.mshoes.mshoes.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mshoes.mshoes.exception.ResourceNotFoundException;
import com.mshoes.mshoes.libraries.Utilities;
import com.mshoes.mshoes.mapper.ColorMapper;
import com.mshoes.mshoes.mapper.ImageMapper;
import com.mshoes.mshoes.mapper.ProductMapper;
import com.mshoes.mshoes.mapper.SizeMapper;
import com.mshoes.mshoes.models.Color;
import com.mshoes.mshoes.models.Image;
import com.mshoes.mshoes.models.Product;
import com.mshoes.mshoes.models.Size;
import com.mshoes.mshoes.models.requested.ColorRequest;
import com.mshoes.mshoes.models.requested.ImageRequest;
import com.mshoes.mshoes.models.requested.ProductRequest;
import com.mshoes.mshoes.models.requested.SizeRequest;
import com.mshoes.mshoes.models.response.ProductResponse;
import com.mshoes.mshoes.repositories.ColorRepository;
import com.mshoes.mshoes.repositories.ImageRepository;
import com.mshoes.mshoes.repositories.ProductRepository;
import com.mshoes.mshoes.repositories.SizeRepository;
import com.mshoes.mshoes.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private final ProductRepository productRepository;

	@Autowired
	private final ImageRepository imageRepository;

	@Autowired
	private final ColorRepository colorRepository;

	@Autowired
	private final SizeRepository sizeRepository;

	@Autowired
	private final ProductMapper productMapper;

	@Autowired
	private final ImageMapper imageMapper;

	@Autowired
	private final ColorMapper colorMapper;

	@Autowired
	private final SizeMapper sizeMapper;
	@Autowired
	private final Utilities utilities;

	public ProductServiceImpl(ProductRepository productRepository, ImageRepository imageRepository,
			ColorRepository colorRepository, SizeRepository sizeRepository, ProductMapper productMapper,
			ImageMapper imageMapper, ColorMapper colorMapper, SizeMapper sizeMapper, Utilities utilities) {
		this.productRepository = productRepository;
		this.imageRepository = imageRepository;
		this.colorRepository = colorRepository;
		this.sizeRepository = sizeRepository;
		this.productMapper = productMapper;
		this.imageMapper = imageMapper;
		this.colorMapper = colorMapper;
		this.sizeMapper = sizeMapper;
		this.utilities = utilities;
	}

	private void saveColorsAndSizes(List<ColorRequest> colorRequests, long productId) {
		if (colorRequests != null) {
			for (ColorRequest colorRequest : colorRequests) {
				colorRequest.setProductId(productId);
				Color color = colorMapper.mapRequestedToModel(colorRequest);
				color.setSizes(null);
				Color newColor = colorRepository.save(color);
				long newColorId = newColor.getId();
				if (newColorId == 0) {
					break;
				} else {
					List<SizeRequest> sizeRequests = colorRequest.getSizes().stream().toList();
					for (SizeRequest sizeRequest : sizeRequests) {
						sizeRequest.setColorId(newColorId);
						Size size = sizeMapper.mapRequestedToModel(sizeRequest);
						size.setSold(0);
						sizeRepository.save(size);
					}
				}

			}
		}

	}

	private void saveImages(List<String> imageUrls, long productId) {
		if (imageUrls != null) {
			for (String imageUrl : imageUrls) {
				ImageRequest imageRequest = new ImageRequest();
				imageRequest.setUrl(imageUrl);
				imageRequest.setProductId(productId);

				Image image = imageMapper.mapRequestedToModel(imageRequest);

				imageRepository.save(image);
			}
		}
	}

	@Override
	public ProductResponse createProduct(ProductRequest productRequest) {

		Product product = productMapper.mapRequestedToModel(productRequest);

		// set current date
		product.setCreatedDate(utilities.getCurrentDate());
		product.setModifiedDate(utilities.getCurrentDate());

		product.setVisited(-1);
		product.setStatus(1);

		product.setImages(null);
		// Save new product to database
		Product newProduct = productRepository.save(product);

		// Get id of product create recently
		long productId = newProduct.getId();

		List<ColorRequest> colorRequests = productRequest.getColors();
		this.saveColorsAndSizes(colorRequests, productId);

		// Save image into table image
		List<String> imageUrls = productRequest.getImageUrls();
		saveImages(imageUrls, productId);

		return this.getProductById(productId);
	}

	// Save color and size into database

	/**
	 * Lấy danh sách tất cả sản phẩm, phân trang theo
	 */
	@Override
	public Page<ProductResponse> getAllProducts(int pageNumber, int pageSize, String sortBy) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
		Page<Product> products = productRepository.findAll(pageable);
		return products.map(productMapper::toProductResponse);
	}

	/**
	 * Lấy danh sách sản phẩm theo mã danh mục
	 */
	@Override
	public Page<ProductResponse> getProductsByCategoryId(long categoryId, int pageNumber, int pageSize, String sortBy) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
		Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);
		return products.map(productMapper::toProductResponse);
	}

	@Override
	public Optional<List<ProductResponse>> searchProducts(String search) {
		List<Product> products = productRepository.searchProducts(search);

		if (!products.isEmpty()) {
			List<ProductResponse> productResponses = productMapper.mapModelsToResponses(products);
			return Optional.of(productResponses);
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Lấy thông tin một sản phẩm theo product_id
	 */
	@Override
	public ProductResponse getProductById(long productId) {
		productRepository.incrementVisitedById(productId);
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
		return productMapper.toProductResponse(product);
	}

	@Override
	public ProductResponse updateProduct(ProductRequest productRequest, long productId) {
		// get product by id from the database
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

		productMapper.updateModel(product, productRequest);
		product.setModifiedDate(utilities.getCurrentDate());

		Product responseProduct = productRepository.save(product);

		return productMapper.toProductResponse(responseProduct);
	}

	@Override
	public void deleteProductById(long productId) {
		// get product by id from the database
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
		try {
			productRepository.delete(product);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("Ex: " + e);
		}

	}

}

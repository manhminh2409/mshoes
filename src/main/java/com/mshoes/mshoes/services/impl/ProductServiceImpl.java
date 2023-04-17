package com.mshoes.mshoes.services.impl;

import com.mshoes.mshoes.exception.ResourceNotFoundException;
import com.mshoes.mshoes.libraries.Utilities;
import com.mshoes.mshoes.mapper.ColorMapper;
import com.mshoes.mshoes.mapper.ImageMapper;
import com.mshoes.mshoes.mapper.ProductMapper;
import com.mshoes.mshoes.mapper.SizeMapper;
import com.mshoes.mshoes.models.Color;
import com.mshoes.mshoes.models.Size;
import com.mshoes.mshoes.models.dtos.ProductDTO;
import com.mshoes.mshoes.models.requested.RequestedColor;
import com.mshoes.mshoes.models.requested.RequestedImage;
import com.mshoes.mshoes.models.requested.RequestedProduct;
import com.mshoes.mshoes.models.Image;
import com.mshoes.mshoes.models.Product;
import com.mshoes.mshoes.models.requested.RequestedSize;
import com.mshoes.mshoes.repositories.ColorRepository;
import com.mshoes.mshoes.repositories.ImageRepository;
import com.mshoes.mshoes.repositories.ProductRepository;
import com.mshoes.mshoes.repositories.SizeRepository;
import com.mshoes.mshoes.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
							  ColorRepository colorRepository, SizeRepository sizeRepository, ProductMapper productMapper, ImageMapper imageMapper, ColorMapper colorMapper, SizeMapper sizeMapper, Utilities utilities) {
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

	private void saveColorsAndSizes(List<RequestedColor> requestedColors, long productId){
		if (requestedColors != null) {
			for (RequestedColor requestedColor : requestedColors){
				requestedColor.setProductId(productId);
				Color color = colorMapper.mapRequestedToModel(requestedColor);
				color.setSizes(null);
				Color newColor = colorRepository.save(color);
				if(newColor != null){
					long newColorId = newColor.getId();
					if (newColorId == 0){
						break;
					}else {
						List<RequestedSize> requestedSizes = requestedColor.getSizes().stream().collect(Collectors.toList());
						for (RequestedSize requestedSize : requestedSizes){
							requestedSize.setColorId(newColorId);
							Size size = sizeMapper.mapRequestedToModel(requestedSize);
							size.setSold(0);
							sizeRepository.save(size);
						}
					}

				}

			}
		}

	}

	private void saveImages(List<String> imageUrls, long productId) {
		if (imageUrls != null) {
			for (String imageUrl : imageUrls) {
				RequestedImage requestedImage = new RequestedImage();
				requestedImage.setUrl(imageUrl);
				requestedImage.setProductId(productId);

				Image image = imageMapper.mapRequestedToModel(requestedImage);

				imageRepository.save(image);
			}
		}
	}
	@Override
	public ProductDTO createProduct(RequestedProduct requestedProduct) {

		Product product = productMapper.mapRequestedToModel(requestedProduct);

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

		List<RequestedColor> requestedColors = requestedProduct.getColors();
		this.saveColorsAndSizes(requestedColors,productId);

		// Save image into table image
		List<String> imageUrls = requestedProduct.getImageUrls();
		saveImages(imageUrls, productId);


		return this.getProductById(productId);
	}

	//Save color and size into database


	/**
	 * Lấy danh sách tất cả sản phẩm, phân trang theo
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page<ProductDTO> getAllProducts(int pageNumber, int pageSize, String sortBy) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
		Page<Product> products = productRepository.findAll(pageable);
		Page<ProductDTO> productDTOS = products.map(product -> productMapper.mapModelToDTO(product));
		return productDTOS;
	}

	/**
	 * Lấy danh sách sản phẩm theo mã danh mục
	 * @param categoryId
	 * @return
	 *
	 * */
	@Override
	public Page<ProductDTO> getProductsByCategoryId(long categoryId, int pageNumber, int pageSize, String sortBy) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
		Page<Product> products = productRepository.findByCategoryId(categoryId,pageable);
		Page<ProductDTO> productDTOS = products.map(product -> productMapper.mapModelToDTO(product));
		return productDTOS;
	}

	@Override
	public Optional<List<ProductDTO>> searchProducts(String search) {
		List<Product> products = productRepository.searchProducts(search);

		if (!products.isEmpty()) {
			List<ProductDTO> productDTOS = productMapper.mapModelToDTOs(products);
			return Optional.of(productDTOS);
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Lấy thông tin một sản phẩm theo product_id
	 * @param productId
	 * @return
	 */
	@Override
	public ProductDTO getProductById(long productId) {
		productRepository.incrementVisitedById(productId);
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
		return productMapper.mapModelToDTO(product);
	}


	@Override
	public ProductDTO updateProduct(RequestedProduct requestedProduct, long productId) {
		// get product by id from the database
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

		productMapper.updateModel(product, requestedProduct);
		product.setModifiedDate(utilities.getCurrentDate());

		Product responseProduct = productRepository.save(product);

		return productMapper.mapModelToDTO(responseProduct);
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

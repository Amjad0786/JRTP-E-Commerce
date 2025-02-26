package in.amjadIT.service;

import java.util.List;

import in.amjadIT.dto.ProductCategoryDTO;
import in.amjadIT.dto.ProductDTO;

public interface ProductService {

	public List<ProductCategoryDTO> findAllCategories();
	
	public List<ProductDTO> findProductsByCategoryId(Long categoryId);
	
	public ProductDTO findByProductId(Long productId);
	
	public List<ProductDTO> findByProductName(String productName);
}


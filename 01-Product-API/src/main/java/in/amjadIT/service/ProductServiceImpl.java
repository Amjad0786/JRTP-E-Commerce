package in.amjadIT.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.amjadIT.Repo.ProductCategoryRepo;
import in.amjadIT.Repo.ProductRepo;
import in.amjadIT.dto.ProductCategoryDTO;
import in.amjadIT.dto.ProductDTO;
import in.amjadIT.mapper.ProductCategoryMapper;
import in.amjadIT.mapper.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ProductCategoryRepo categoryRepo;

	@Override
	public List<ProductCategoryDTO> findAllCategories() {
		/*
		 * List<ProductCategory> categories = categoryRepo.findAll();
		 * 
		 * List<ProductCategoryDTO> dtoList = new ArrayList<>();
		 * 
		 * for (ProductCategory category : categories) { ProductCategoryDTO dto =
		 * ProductCategoryMapper.convertToDTO(category); dtoList.add(dto);
		 * 
		 * } return dtoList;
		 */

		return categoryRepo.findAll().stream().map(ProductCategoryMapper::convertToDTO).collect(Collectors.toList());

	}

	@Override
	public List<ProductDTO> findProductsByCategoryId(Long categoryId) {
		return productRepo.findByCategoryId(categoryId)
		           .stream()
		           .map(ProductMapper::convertToDto)
		           .collect(Collectors.toList());
		
	}

	@Override
	public ProductDTO findByProductId(Long productId) {
		
//		Optional<Product> byId = productRepo.findById(productId);
//		if(byId.isPresent()) {
//			Product product = byId.get();
//			return ProductMapper.convertToDto(product);
//		}
//		
//		
//		return null;
		
		return productRepo.findById(productId)
				          .map(ProductMapper::convertToDto)
				          .orElse(null);
				          
	}

	@Override
	public List<ProductDTO> findByProductName(String productName) {

		return productRepo.findByNameContaining(productName).stream().map(ProductMapper::convertToDto)
				.collect(Collectors.toList());

	}

}

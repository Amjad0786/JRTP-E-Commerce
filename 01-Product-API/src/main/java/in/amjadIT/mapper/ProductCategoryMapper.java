package in.amjadIT.mapper;

import org.modelmapper.ModelMapper;

import in.amjadIT.dto.ProductCategoryDTO;
import in.amjadIT.entities.ProductCategory;

public class ProductCategoryMapper {

public static final ModelMapper modelMapper = new ModelMapper();
	
	public static ProductCategoryDTO convertToDTO(ProductCategory entity) {
		
		return modelMapper.map(entity, ProductCategoryDTO.class);
	}
	
	public static ProductCategory convertToEntity(ProductCategoryDTO categoryDto) {
		
		return modelMapper.map(categoryDto, ProductCategory.class);
			
		}
}

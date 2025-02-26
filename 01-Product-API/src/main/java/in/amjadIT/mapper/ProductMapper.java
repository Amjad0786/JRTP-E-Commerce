package in.amjadIT.mapper;

import org.modelmapper.ModelMapper;

import in.amjadIT.dto.ProductDTO;
import in.amjadIT.entities.Product;

public class ProductMapper {
	
	public static final ModelMapper modelMapper = new ModelMapper();
	
	public static ProductDTO convertToDto(Product entity) {
		
		return modelMapper.map(entity, ProductDTO.class);
	}
	
	public static Product convertToProduct(ProductDTO productDto) {
		
		return modelMapper.map(productDto, Product.class);
			
		}
	}



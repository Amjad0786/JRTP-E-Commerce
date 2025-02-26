package in.amjadIT.mapper;

import org.modelmapper.ModelMapper;

import in.amjadIT.dto.CustomerDTO;
import in.amjadIT.entities.Customer;


public class CustomerMapper {

	private static final ModelMapper mapper = new ModelMapper();

	public static CustomerDTO convertToDto(Customer entity) {
		return mapper.map(entity, CustomerDTO.class);
	}

	public static Customer convertToEntity(CustomerDTO dto) {
		return mapper.map(dto, Customer.class);
	}

}


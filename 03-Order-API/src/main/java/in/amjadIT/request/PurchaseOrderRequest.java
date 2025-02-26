package in.amjadIT.request;

import java.util.List;

import in.amjadIT.dto.AddressDTO;
import in.amjadIT.dto.CustomerDTO;
import in.amjadIT.dto.OrderDTO;
import in.amjadIT.dto.OrderItemDTO;
import lombok.Data;

@Data
public class PurchaseOrderRequest {

	private CustomerDTO customerDto;
	
	private AddressDTO addressDTO;
	
	private OrderDTO orderDTO;
	
	private List<OrderItemDTO> orderItemsDto;
}

package in.amjadIT.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.amjadIT.dto.OrderDTO;
import in.amjadIT.dto.PaymentCallBackDTO;
import in.amjadIT.request.PurchaseOrderRequest;
import in.amjadIT.response.PurchaseOrderResponse;

@Service
public interface OrderService {

	public PurchaseOrderResponse createOrder(PurchaseOrderRequest orderRequest);
	
	public PurchaseOrderResponse updateOrder(PaymentCallBackDTO paymentCallBackDTO);
	
	public List<OrderDTO> getOrdersByEmail(String email);
}


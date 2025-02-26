package in.amjadIT.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.amjadIT.dto.OrderDTO;
import in.amjadIT.dto.PaymentCallBackDTO;
import in.amjadIT.request.PurchaseOrderRequest;
import in.amjadIT.response.ApiResponse;
import in.amjadIT.response.PurchaseOrderResponse;
import in.amjadIT.service.OrderService;

@RestController
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<ApiResponse<PurchaseOrderResponse>> createOrder(
			@RequestBody PurchaseOrderRequest orderRequest) {

		ApiResponse<PurchaseOrderResponse> response = new ApiResponse<>();

		PurchaseOrderResponse orderResp = orderService.createOrder(orderRequest);

		if (orderResp != null) {
			response.setStatus(200);
			response.setMessage("Order Created");
			response.setData(orderResp);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMessage("Order Creation Failed");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PutMapping("/order")
	public ResponseEntity<ApiResponse<PurchaseOrderResponse>> updateOrder(
			@RequestBody PaymentCallBackDTO orderRequest) {

		ApiResponse<PurchaseOrderResponse> response = new ApiResponse<>();

		PurchaseOrderResponse orderResp = orderService.updateOrder(orderRequest);

		if (orderResp != null) {
			response.setStatus(200);
			response.setMessage("Order Updated");
			response.setData(orderResp);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMessage("Order Updation Failed");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/orders/{email}")
	public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrders(@PathVariable String email) {

		ApiResponse<List<OrderDTO>> response = new ApiResponse<>();

		List<OrderDTO> ordersByEmail = orderService.getOrdersByEmail(email);

		if (!ordersByEmail.isEmpty()) {
			response.setStatus(200);
			response.setMessage("Fatched Orders");
			response.setData(ordersByEmail);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMessage("Failed to Fatched orders");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}

package in.amjadIT.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import in.amjadIT.dto.AddressDTO;
import in.amjadIT.dto.CustomerDTO;
import in.amjadIT.dto.OrderDTO;
import in.amjadIT.dto.OrderItemDTO;
import in.amjadIT.dto.PaymentCallBackDTO;
import in.amjadIT.entities.Address;
import in.amjadIT.entities.Customer;
import in.amjadIT.entities.Order;
import in.amjadIT.entities.OrderItem;
import in.amjadIT.repo.AddressRepo;
import in.amjadIT.repo.CustomerRepo;
import in.amjadIT.repo.OrderItemRepo;
import in.amjadIT.repo.OrderRepo;
import in.amjadIT.request.PurchaseOrderRequest;
import in.amjadIT.response.PurchaseOrderResponse;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private OrderItemRepo orderItemRepo;

	@Autowired
	private RazorpayService razorpayService;
	
	@Autowired
	private EmailService emailService;

	@Override
	public PurchaseOrderResponse createOrder(PurchaseOrderRequest orderRequest) {

		CustomerDTO customerDto = orderRequest.getCustomerDto();
		AddressDTO addressDTO = orderRequest.getAddressDTO();
		OrderDTO orderDTO = orderRequest.getOrderDTO();
		List<OrderItemDTO> orderItemsList = orderRequest.getOrderItemsDto();

		// check customer presence in DB and save If Required
		Customer c = customerRepo.findByEmail(customerDto.getEmail());
		if (c == null) {
			// TODO: Inter Service Communication(feign client)
			c = new Customer();
			c.setName(customerDto.getName());
			c.setEmail(customerDto.getEmail());
			c.setPhno(customerDto.getPhno());
			customerRepo.save(c);
		}

		// save address data
		Address address = new Address();
		address.setHouseNum(addressDTO.getHouseNum());
		address.setStreet(addressDTO.getStreet());
		address.setCity(addressDTO.getCity());
		address.setState(addressDTO.getState());
		address.setZipCode(addressDTO.getZipCode());
		address.setCustomer(c);// association mapping
		addressRepo.save(address);

		// saveOrder
		Order newOrder = new Order();
		String orderTrackingNum = generatedOrderTrackingNum();
		newOrder.setOrderTrackingNum(orderTrackingNum);

		// create razorpay order and get order details
		com.razorpay.Order paymentOrder = razorpayService.createPaymentOrder(orderDTO.getTotalPrice());
		newOrder.setRazorPayOrderId(paymentOrder.get("id"));
		newOrder.setOrderStatus(paymentOrder.get("status"));
		newOrder.setTotalPrice(orderDTO.getTotalPrice());
		newOrder.setTotalQuantity(orderDTO.getTotalQuantity());
		newOrder.setEmail(c.getEmail());
		newOrder.setCustomer(c); // association mapping
		newOrder.setAddress(address); // association mapping

		orderRepo.save(newOrder);

		// save order items
		for (OrderItemDTO itemDto : orderItemsList) {
			OrderItem item = new OrderItem();
			BeanUtils.copyProperties(itemDto, item);
			item.setOrder(newOrder);// association mapping
			orderItemRepo.save(item);
		}

		// Prepare and Return Response
		return PurchaseOrderResponse.builder().razorpayOrderId(orderTrackingNum).orderStatus(orderTrackingNum)
				.orderTrackingNumber(orderTrackingNum).build();

	}

	@Override
	public PurchaseOrderResponse updateOrder(PaymentCallBackDTO paymentCallBackDTO) {

		Order order = orderRepo.findByRazorPayOrderId(paymentCallBackDTO.getRazorpayOrderId());

		if (order != null) {
			order.setOrderStatus("CONFIRMED");
			order.setDeliveryDate(LocalDate.now().plusDays(3));
			order.setRazorPayPaymentId(paymentCallBackDTO.getRazorpayPaymentId());
			orderRepo.save(order);
			
			String subject="Your Order Is Confirmed";
			String body = "Thankyou, you will recevied your order on " + order.getDeliveryDate();
			
			emailService.sendEmail(order.getEmail(), subject, body);
			
			
		}
		return PurchaseOrderResponse.builder().razorpayOrderId(paymentCallBackDTO.getRazorpayOrderId())
				.orderStatus(order.getOrderStatus()).orderTrackingNumber(order.getOrderTrackingNum()).build();

	}

	@Override
	public List<OrderDTO> getOrdersByEmail(String email) {

		List<OrderDTO> dtoList = new ArrayList<>();

		List<Order> orderList = orderRepo.findByEmail(email);

		for (Order order : orderList) {

			OrderDTO orderDTO = new OrderDTO();
			BeanUtils.copyProperties(order, orderDTO);
			dtoList.add(orderDTO);
		}

		return dtoList;
	}

	private String generatedOrderTrackingNum() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());

		String randomUuId = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

		// combine timestamp and uuid to form order tracking number

		return "OD" + timestamp + randomUuId;
	}
}

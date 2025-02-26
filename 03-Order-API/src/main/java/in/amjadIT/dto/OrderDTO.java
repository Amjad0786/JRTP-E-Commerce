package in.amjadIT.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderDTO {

	private Long orderId;

	private String orderTrackingNum;

	private String razorPayOrderId; // payment initiated

	private String email;

	private String orderStatus;

	private Double totalPrice;

	private int totalQuantity;

	private String razorPayPaymentId; // payment completed

	private String invoiceUrl;

	private LocalDate deliveryDate;

}

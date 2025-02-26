package in.amjadIT.service;

import com.razorpay.Order;

public interface RazorpayService {
	
	public Order createPaymentOrder(double amount);

}

package in.amjadIT.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amjadIT.dto.OrderDTO;
import in.amjadIT.entities.Order;
import java.util.List;


public interface OrderRepo extends JpaRepository<Order, Long> {

	public Order findByRazorPayOrderId(String razorPayOrderId);
	
	public List<Order> findByEmail(String email);
}

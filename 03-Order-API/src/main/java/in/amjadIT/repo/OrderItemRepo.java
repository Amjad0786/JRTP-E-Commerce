package in.amjadIT.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amjadIT.entities.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

}

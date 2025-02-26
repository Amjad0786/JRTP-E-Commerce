package in.amjadIT.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amjadIT.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	public Customer findByEmail(String email);
}

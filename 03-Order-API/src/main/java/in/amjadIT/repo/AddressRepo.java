package in.amjadIT.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amjadIT.entities.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{

}

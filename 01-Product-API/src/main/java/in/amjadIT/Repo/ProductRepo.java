package in.amjadIT.Repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amjadIT.dto.ProductCategoryDTO;
import in.amjadIT.entities.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
	
	//select * from products where category_id = categoryId
	public List<Product> findByCategoryId(Long categoryId);

	//select * from products where name like '%name%'
	public List<Product> findByNameContaining(String productName);
}

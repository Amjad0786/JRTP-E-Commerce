package in.amjadIT.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amjadIT.entities.ProductCategory;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {

}

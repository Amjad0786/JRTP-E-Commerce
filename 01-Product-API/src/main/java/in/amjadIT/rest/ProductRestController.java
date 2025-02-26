package in.amjadIT.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in.amjadIT.dto.ProductCategoryDTO;
import in.amjadIT.dto.ProductDTO;
import in.amjadIT.reponse.ApiResponse;
import in.amjadIT.service.ProductService;

@RestController
public class ProductRestController {

	@Autowired
	private ProductService productService;

	@GetMapping("/categories")
	public ResponseEntity<ApiResponse<List<ProductCategoryDTO>>> productCategories() {

		ApiResponse<List<ProductCategoryDTO>> response = new ApiResponse<>();

		List<ProductCategoryDTO> allCategories = productService.findAllCategories();

		if (!allCategories.isEmpty()) {

			response.setStatus(200);
			response.setMassage("Fetched Categories successfully");
			response.setData(allCategories);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {

			response.setStatus(500);
			response.setMassage("Failed to Fetched Categories ");
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/products/{categoryId}")
	public ResponseEntity<ApiResponse<List<ProductDTO>>> products(@PathVariable Long categoryId) {

		ApiResponse<List<ProductDTO>> response = new ApiResponse<>();

		List<ProductDTO> products = productService.findProductsByCategoryId(categoryId);

		if (!products.isEmpty()) {
			response.setStatus(200);
			response.setMassage("Fatched Products successfully");
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMassage("Failed to Fatched Products");
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/productsByName/{name}")
	public ResponseEntity<ApiResponse<List<ProductDTO>>> products(@PathVariable String name) {

		ApiResponse<List<ProductDTO>> response = new ApiResponse<>();

		List<ProductDTO> products = productService.findByProductName(name);

		if (!products.isEmpty()) {
			response.setStatus(200);
			response.setMassage("Fatched Products successfully");
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMassage("Failed to Fatched Products");
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<ApiResponse<ProductDTO>> product(@PathVariable Long productId) {

		ApiResponse<ProductDTO> response = new ApiResponse<>();

		ProductDTO product = productService.findByProductId(productId);

		if (product != null) {
			response.setStatus(200);
			response.setMassage("Fatched Products successfully");
			response.setData(product);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMassage("Failed to Fatched Products");
			response.setData(product);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

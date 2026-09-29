package cl.ufro.dci.api.controller;

import cl.ufro.dci.api.domain.Product;
import cl.ufro.dci.api.service.ProductService;
import cl.ufro.dci.api.util.StringValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@CrossOrigin
public class SearchProductsController {


    private final ProductService ProductService;
    private final String INVALID_SEARCH_FIELD = "Search field must contain only letters, numbers and spaces";
    private final String EMPTY_PRODUCTS_LIST = "No products found";
    private final String ERROR_SEARCHING_PRODUCTS = "Error searching products: ";

    public SearchProductsController(ProductService productService) {
        this.ProductService = productService;
    }

    /**
     * Given a search field, returns a list of products that match the search field.
     * @param searchField
     * @return
     */

    @GetMapping("/search/{searchField}")
    public ResponseEntity<?> getProductsBySearchField(@PathVariable String searchField) {
        try {
            if (!StringValidator.validateSearchField(searchField)) {
                return ResponseEntity.badRequest()
                        .body(INVALID_SEARCH_FIELD);
            }
            List<Product> products = ProductService.getProductsBySearchField(searchField);
            if (products.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(EMPTY_PRODUCTS_LIST);
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ERROR_SEARCHING_PRODUCTS + e.getMessage());
        }
    }


}

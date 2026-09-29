package cl.ufro.dci.api.controller;

import cl.ufro.dci.api.domain.Product;
import cl.ufro.dci.api.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@CrossOrigin
public class GetProductController {


    private final ProductService ProductService;
    private final String PRODUCT_NOT_FOUND = "Product not found";
    private final String ERROR_FINDING_PRODUCT = "Error finding product: ";

    public GetProductController(ProductService productService) {
        this.ProductService = productService;
    }

    /**
     * Gets product by id.
     * @param id
     * @return
     */

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Optional<Product> product = ProductService.getProductById(id);
            if (product.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(PRODUCT_NOT_FOUND);
            }
            Product foundProduct = product.get();
            return ResponseEntity.ok(foundProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ERROR_FINDING_PRODUCT + e.getMessage());
        }
    }


}

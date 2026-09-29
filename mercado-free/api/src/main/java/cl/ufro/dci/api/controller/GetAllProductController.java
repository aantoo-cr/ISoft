package cl.ufro.dci.api.controller;

import cl.ufro.dci.api.domain.Product;
import cl.ufro.dci.api.domain.dto.ErrorDto;
import cl.ufro.dci.api.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@CrossOrigin
public class GetAllProductController {


    private final ProductService ProductService;
    private final String EMPTY_LIST = "Empty list";
    private final String ERROR_GETTING_LIST = "Error getting list: ";

    public GetAllProductController(ProductService productService) {
        this.ProductService = productService;
    }


    @GetMapping("/product")
    public ResponseEntity<?> getAllProduct() {
        try {
            List<Product> products = ProductService.getAllProduct();

            if (products.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorDto(EMPTY_LIST, null));
            }

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorDto(ERROR_GETTING_LIST, e.getMessage()));
        }
    }


}

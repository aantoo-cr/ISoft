package cl.ufro.dci.api.service;

import cl.ufro.dci.api.domain.Product;
import cl.ufro.dci.api.repository.ProductRepository;
import cl.ufro.dci.api.util.StringValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private final ProductRepository ProductRepository;
    private final String INVALLID_ID = "Id must be greater than 0";
    private final String INVALLID_SEARCH_FIELD = "Search field must contain only letters, numbers and spaces";

    public ProductService(ProductRepository productRepository) {
        this.ProductRepository = productRepository;
    }

    /**
     * Gets all products.
     */

    public List<Product> getAllProduct() {
        return ProductRepository.findAll();
    }

    /**
     * Gets product by id.
     *
     * @param id the id
     * @return Product
     */

    public Optional<Product> getProductById(Long id) throws Exception {
        if (id < 0) {
            throw new Exception(INVALLID_ID);
        }
        return ProductRepository.findById(id);
    }

    /**
     * Given a search field, returns a list of products that match the search field.
     * @param searchField
     */

    public List<Product> getProductsBySearchField(String searchField) throws Exception {
        if (StringValidator.startsWithNumber(searchField) ||
                !StringValidator.hasOnlyLettersNumbersSpaces(searchField) ||
                !StringValidator.validateStringLength(searchField, 1, 64) ||
                !StringValidator.isNotEmpty(searchField)) {
            throw new Exception(INVALLID_SEARCH_FIELD);
        }
        return ProductRepository.findByProNameContainingIgnoreCaseOrProDescriptionContainingIgnoreCaseOrProBrandContainingIgnoreCase(searchField,
                                                                                                                                     searchField,
                                                                                                                                     searchField);
    }


}
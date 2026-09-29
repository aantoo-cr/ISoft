package cl.ufro.dci.api.service;

import cl.ufro.dci.api.domain.Product;
import cl.ufro.dci.api.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {


    @Autowired
    private ProductRepository ProductRepository;
    @Autowired
    private ProductService ProductService;

    @BeforeEach
    void setUp() {
        ProductRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        ProductRepository.deleteAll();
    }

    @Test
    @DisplayName("When entered an invallid Id, throws Exception")
    void wrongIdThrowsException() {
        assertThrows(Exception.class, () -> {
            ProductService.getProductById(-1L);
        });
    }

    @Test
    @DisplayName("When entered a valid Id, returns Product")
    void validIdReturnsProduct() throws Exception {
        Product product = new Product();
        product.setProName("Product Name");
        product.setProDescription("Product Description");
        product.setProPrice(100L);
        product.setProBrand("Product Brand");

        Product expected = ProductRepository.save(product);
        Long expectedId = expected.getProId();

        assertEquals(expected, ProductService.getProductById(expectedId).get());
    }

    @Test
    @DisplayName("When invallid search field is used, throws Exception")
    void wrongSearchFieldThrowsException() {
        assertThrows(Exception.class, () -> {
            ProductService.getProductsBySearchField("1");
        });
    }

    @Test
    @DisplayName("When valid search field is used, returns List<Product>")
    void validSearchFieldReturnsListProduct() throws Exception {
        Product product = new Product();
        product.setProName("Product Name");
        product.setProDescription("Product Description");
        product.setProPrice(100L);
        product.setProBrand("Product Brand");

        ProductRepository.save(product);

        assertEquals(1, ProductService.getProductsBySearchField("Product").size());
    }

    @Test
    @DisplayName("When valid, but not equal search field is used, returns empty List<Product>")
    void validButNotEqualSearchFieldReturnsEmptyListProduct() throws Exception {
        Product product = new Product();
        product.setProName("Product Name");
        product.setProDescription("Product Description");
        product.setProPrice(100L);
        product.setProBrand("Product Brand");

        ProductRepository.save(product);

        assertEquals(0, ProductService.getProductsBySearchField("Product2").size());
    }

    @Test
    @DisplayName("When valid and correct, but not equal search field is used, returns List<Product>")
    void validAndCorrectButNotEqualSearchFieldReturnsListProduct() throws Exception {
        Product product = new Product();
        product.setProName("Product Name");
        product.setProDescription("Product Description");
        product.setProPrice(100L);
        product.setProBrand("Product Brand");

        ProductRepository.save(product);

        assertEquals(1, ProductService.getProductsBySearchField("ProDucT brAND").size());
    }


}
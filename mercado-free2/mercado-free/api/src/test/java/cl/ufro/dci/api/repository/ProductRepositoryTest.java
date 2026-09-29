package cl.ufro.dci.api.repository;

import cl.ufro.dci.api.domain.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {


    @Autowired
    private ProductRepository ProductRepository;

    @BeforeEach
    void setUp() {
        ProductRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        ProductRepository.deleteAll();
    }

    @Test
    @DisplayName("When a valid and matching ID is used, the product should be found and not null.")
    void existingProductFoundNotNull() {

        Product product = new Product();
        product.setProName("test");
        product.setProDescription("test");
        product.setProBrand("test");
        product.setProPrice(1L);
        product.setProImage("test");

        Product expected = ProductRepository.save(product);
        Long id = expected.getProId();

        Optional<Product> actual = ProductRepository.findById(id);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertNotNull(actual.get());
    }

    @Test
    @DisplayName("When a valid but non-matching ID is used, the"
               + "product should not be found and optional should be empty.")
    void nonExistentProductNotFoundNull() {
        Long testId = 1L;
        Long wrongId = 2L;

        Product product = new Product();
        product.setProId(testId);
        product.setProName("test");
        product.setProDescription("test");
        product.setProBrand("test");
        product.setProPrice(1L);
        product.setProImage("test");

        ProductRepository.save(product);

        Assertions.assertTrue(ProductRepository.findById(wrongId).isEmpty());
    }

    @Test
    @DisplayName("When given a proper search field, returns products that "
               + "matches once on the search field.")
    void getProductsByOneCoincidence() {
        String searchField = "test1";

        Product product1 = new Product();
        product1.setProName(searchField);

        Product product2 = new Product();
        product2.setProDescription(searchField);

        Product product3 = new Product();
        product3.setProBrand(searchField);

        List<Product> expected = List.of(product1, product2, product3);
        ProductRepository.saveAll(expected);

        List<Product> actual = ProductRepository
                .findByProNameContainingIgnoreCaseOrProDescriptionContainingIgnoreCaseOrProBrandContainingIgnoreCase(searchField,
                                                         searchField,
                                                         searchField);

        assertThat(actual).hasSize(3);
        assertThat(actual).containsAnyElementsOf(expected);
    }

    @Test
    @DisplayName("When given a proper search field, returns products that "
               + "matches twice on the search field.")
    void getProductsByTwoCoincidences() {
        String searchField = "test2";

        Product product1 = new Product();
        product1.setProName(searchField);
        product1.setProDescription(searchField);

        Product product2 = new Product();
        product2.setProDescription(searchField);
        product2.setProBrand(searchField);

        Product product3 = new Product();
        product3.setProBrand(searchField);
        product3.setProName(searchField);

        List<Product> expected = List.of(product1, product2, product3);
        ProductRepository.saveAll(expected);

        List<Product> actual = ProductRepository
                .findByProNameContainingIgnoreCaseOrProDescriptionContainingIgnoreCaseOrProBrandContainingIgnoreCase(searchField,
                                                         searchField,
                                                         searchField);

        assertThat(actual).hasSize(3);
        assertThat(actual).containsAnyElementsOf(expected);
    }

    @Test
    void getProductsByThreeCoincidences() {
        String searchField = "test3";

        Product product1 = new Product();
        product1.setProName(searchField);
        product1.setProDescription(searchField);
        product1.setProBrand(searchField);

        Product product2 = new Product();
        product2.setProDescription(searchField);
        product2.setProBrand(searchField);
        product2.setProName(searchField);

        List<Product> expected = List.of(product1, product2);
        ProductRepository.saveAll(expected);

        List<Product> actual = ProductRepository
                .findByProNameContainingIgnoreCaseOrProDescriptionContainingIgnoreCaseOrProBrandContainingIgnoreCase(searchField,
                                                         searchField,
                                                         searchField);

        assertThat(actual).hasSize(2);
        assertThat(actual).containsAnyElementsOf(expected);
    }

    @Test
    @DisplayName("Given a search field, only returns products that matches "
               + "the search field, omiting products that doesn't match.")
    void getProductsByCoincidenceOmitingNonMatchingProducts() {
        String searchField = "test";

        Product product1 = new Product();
        product1.setProName(searchField);

        Product product2 = new Product();
        product2.setProDescription(searchField);

        Product product3 = new Product();
        product3.setProBrand(searchField);

        Product product4 = new Product();
        product4.setProName("non matching");

        Product product5 = new Product();
        product5.setProDescription("non matching");

        Product product6 = new Product();
        product6.setProBrand("non matching");

        List<Product> allProducts = List.of(product1,
                                         product2,
                                         product3,
                                         product4,
                                         product5,
                                         product6);

        ProductRepository.saveAll(allProducts);

        List<Product> actual = ProductRepository
                .findByProNameContainingIgnoreCaseOrProDescriptionContainingIgnoreCaseOrProBrandContainingIgnoreCase(searchField,
                                                         searchField,
                                                         searchField);

        List<Product> expectedProducts = List.of(product1,
                                                 product2,
                                                 product3);

        List<Product> nonExpectedProducts = List.of(product4,
                                                    product5,
                                                    product6);

        assertThat(actual).hasSize(3);
        assertThat(actual).containsAnyElementsOf(expectedProducts);
        assertThat(actual).doesNotContainAnyElementsOf(nonExpectedProducts);
    }


}
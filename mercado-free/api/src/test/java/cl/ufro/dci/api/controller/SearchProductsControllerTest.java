package cl.ufro.dci.api.controller;

import cl.ufro.dci.api.domain.Product;
import cl.ufro.dci.api.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class SearchProductsControllerTest {


    @Autowired
    private MockMvc MockMvc;
    @MockBean
    private ProductService ProductService;

    @Test
    @DisplayName("[UC-011] When a valid and matching name is used, the " +
                 "product should be found")
    void validNameTest() throws Exception {
        String value = "test";
        String searchField = "test";

        Product product1 = new Product();
        product1.setProName(value);

        Product product2 = new Product();
        product2.setProDescription(value);

        Product product3 = new Product();
        product3.setProBrand("wrong");

        when(ProductService.getProductsBySearchField(searchField))
                           .thenReturn(List.of(product1, product2));

        MockMvc.perform(MockMvcRequestBuilders.get("/search/" + searchField))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                            .json("[{\"proId\":null, " +
                                    "\"proName\":\"test\", " +
                                    "\"proDescription\":null, " +
                                    "\"proPrice\":null, " +
                                    "\"proBrand\":null, " +
                                    "\"proImage\":null}" +
                                    ", {\"proId\":null, " +
                                    "\"proName\":null, " +
                                    "\"proDescription\":\"test\", " +
                                    "\"proPrice\":null, " +
                                    "\"proBrand\":null, " +
                                    "\"proImage\":null}]"));
    }

    @Test
    @DisplayName("[UC-012] Given an empty search field, should return a bad " +
                 "request that mentions the error")
    void emptySearchFieldTest() throws Exception {
        String searchField = "";

        MockMvc.perform(MockMvcRequestBuilders.get("/search/" + searchField))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                            .string("Search field must not be empty"));
    }

    @Test
    @DisplayName("[UC-013] Given an invallid search field that starts with a " +
                 "number, return a bad request that mentions the error")
    void invalidSearchFieldStartsWithNumberTest() throws Exception {
        String searchField = "1test";

        MockMvc.perform(MockMvcRequestBuilders.get("/search/" + searchField))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                            .string("Search field must contain only letters, numbers and spaces"));
    }

    @Test
    @DisplayName("[UC-014] Given an invallid search field, should return a bad " +
                 "request that mentions that error")
    void invalidSearchFieldTest() throws Exception {
        String searchField = "test!#!_a";

        MockMvc.perform(MockMvcRequestBuilders.get("/search/" + searchField))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                            .string("Search field must contain only letters, numbers and spaces"));
    }

    @Test
    @DisplayName("[UC-015] Given an search field, should return a bad request " +
                 "because it hasn't got any coincidences in the repository.")
    void noCoincidencesTest() throws Exception {
        String searchField = "test";

        when(ProductService.getProductsBySearchField(searchField))
                           .thenReturn(List.of());

        MockMvc.perform(MockMvcRequestBuilders.get("/search/" + searchField))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                            .string("No products found"));
    }

    @Test
    @DisplayName("[UC-016] Given a vallid, but not completly equal searh " +
                 "field, should return all partial coincidences present in " +
                 "the database.")
    void partialCoincidencesTest() throws Exception {
        String value = "test";
        String searchField = "tes";

        Product product1 = new Product();
        product1.setProName(value);

        Product product2 = new Product();
        product2.setProDescription(value);

        Product product3 = new Product();
        product3.setProBrand("wrong");

        when(ProductService.getProductsBySearchField(searchField))
                .thenReturn(List.of(product1, product2));

        MockMvc.perform(MockMvcRequestBuilders.get("/search/" + searchField))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("[{\"proId\":null, " +
                                "\"proName\":\"test\", " +
                                "\"proDescription\":null, " +
                                "\"proPrice\":null, " +
                                "\"proBrand\":null, " +
                                "\"proImage\":null}" +
                                ", {\"proId\":null, " +
                                "\"proName\":null, " +
                                "\"proDescription\":\"test\", " +
                                "\"proPrice\":null, " +
                                "\"proBrand\":null, " +
                                "\"proImage\":null}]"));
    }


}
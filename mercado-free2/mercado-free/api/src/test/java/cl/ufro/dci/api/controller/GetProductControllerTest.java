package cl.ufro.dci.api.controller;


import cl.ufro.dci.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import cl.ufro.dci.api.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class GetProductControllerTest {


    @Autowired
    private MockMvc MockMvc;
    @MockBean
    private ProductService ProductService;

    @Test
    @DisplayName("[UC-031] When a valid and matching ID is used, the product should be found")
    public void validIdTest() throws Exception {
        Long id = 1L;
        Product product = new Product();
        product.setProId(id);
        product.setProName("Product Name");
        product.setProDescription("Product Description");
        product.setProPrice(100L);
        product.setProBrand("Product Brand");
        product.setProImage("Product Image");
        when(ProductService.getProductById(id)).thenReturn(Optional.of(product));

        MockMvc.perform(MockMvcRequestBuilders.get("/product/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"proId\":1, \"proName\":\"Product Name\", \"proDescription\":\"Product Description\", \"proPrice\":100, \"proBrand\":\"Product Brand\", \"proImage\":\"Product Image\"}"));
    }

    @Test
    @DisplayName("[UC-032] When a valid but non-matching ID is used, the product should not be found")
    public void nonExistentIdTest() throws Exception {
        Long id = 1L;
        when(ProductService.getProductById(id)).thenReturn(Optional.empty());

        MockMvc.perform(MockMvcRequestBuilders.get("/product/" + id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Product not found"));
    }

    @Test
    @DisplayName("[UC-033] When an invalid ID is used, there should be an error finding the product")
    public void invalidIdTest() throws Exception {
        Long id = -1L;
        when(ProductService.getProductById(id)).thenThrow(new Exception("ID must be greater than 0"));

        MockMvc.perform(MockMvcRequestBuilders.get("/product/" + id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Error finding product: ID must be greater than 0"));
    }


}
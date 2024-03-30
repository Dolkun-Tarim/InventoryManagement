package com.dolkuntarim.test;

import com.dolkuntarim.product.Product;
import com.dolkuntarim.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTests {
    @Autowired
   private ProductService productService;


    @Test
    public void givenListOfProducts_whenGetAllProducts_thenReturnProductsList() {
        List<Product> listOfProducts=productService.listAll();
        listOfProducts.stream().forEach(product -> System.out.println(product.toString()));
        Assert.isTrue(listOfProducts.size()>1);

    }
}

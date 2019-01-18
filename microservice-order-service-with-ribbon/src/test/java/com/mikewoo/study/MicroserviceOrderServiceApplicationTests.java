package com.mikewoo.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikewoo.study.domain.Product;
import com.mikewoo.study.domain.ShoppingList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MicroserviceOrderServiceApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void jsonTest() throws JsonProcessingException {
        ShoppingList shoppingList = new ShoppingList();
        Product product01 = new Product(10001L, "Java编程思想", 3);
        Product product02 = new Product(10002L, "Kafka权威指南", 2);
        List<Product> products = Arrays.asList(product01, product01);
        shoppingList.setUserId(0L);
        shoppingList.setProducts(products);
        ObjectMapper objectMapper = new ObjectMapper();
        String shoppingListStr = objectMapper.writeValueAsString(shoppingList);
        System.out.println(shoppingListStr);
    }

    @Test
    public void restTemplateTest() {
        Product result = restTemplate.getForObject("http://microservice-product-service/api/v1/product/{id}", Product.class, 10001L);
        System.out.println(result);
    }

}


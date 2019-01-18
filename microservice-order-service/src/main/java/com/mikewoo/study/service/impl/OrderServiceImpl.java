package com.mikewoo.study.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikewoo.study.domain.Order;
import com.mikewoo.study.domain.Product;
import com.mikewoo.study.domain.ShoppingList;
import com.mikewoo.study.service.OrderService;
import com.mikewoo.study.utils.OrderNumberGenerator;
import com.mikewoo.study.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Order save(ShoppingList shoppingList) throws IOException {
        List<Product> products = shoppingList.getProducts();
        Integer totalPay = 0;
        for (Product product : products) {
            String result = restTemplate.getForObject("http://microservice-product-service/api/v1/product/{id}", String.class, product.getId());
            log.info("microservice-product-service response: {}", result);
            if (Objects.nonNull(result)) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(result);
                JsonNode data = jsonNode.get("data");
                if (Objects.nonNull(data)) {
                    Product p = objectMapper.readValue(data.toString(), Product.class);
                    product.setPrice(p.getPrice());
                    product.setName(p.getName());
                    totalPay = totalPay + product.getPrice() * product.getCount();
                }
            }
        }
        Order order = new Order();
        order.setId(System.currentTimeMillis());
        order.setOrderNumber(OrderNumberGenerator.getInstance().generate(shoppingList.getUserId()));
        order.setUserId(shoppingList.getUserId());
        order.setUsername("Eric Gui");
        order.setProducts(products);
        order.setTotalPay(totalPay);
        order.setCreateTime(new Date());
        return order;
    }
}

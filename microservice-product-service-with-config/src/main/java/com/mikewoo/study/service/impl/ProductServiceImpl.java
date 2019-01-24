package com.mikewoo.study.service.impl;

import com.mikewoo.study.domain.Product;
import com.mikewoo.study.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Map<Long, Product> productMap = new HashMap<>();

    static {
        Product product01 = new Product(10001L, "Java编程思想", 88000, 1000);
        Product product02 = new Product(10002L, "Kafka权威指南", 45000, 800);
        Product product03 = new Product(10003L, "Java并发编程的艺术", 51000, 560);
        Product product04 = new Product(10004L, "GO语言实战", 47000, 780);
        Product product05 = new Product(10005L, "Spring Boot实战", 51000, 400);
        Product product06 = new Product(10006L, "Spring Cloud微服务实战", 62000, 640);
        productMap.put(product01.getId(), product01);
        productMap.put(product02.getId(), product02);
        productMap.put(product03.getId(), product03);
        productMap.put(product04.getId(), product04);
        productMap.put(product05.getId(), product05);
        productMap.put(product06.getId(), product06);
    }

    @Override
    public List<Product> listProduct() {
        Collection<Product> products = productMap.values();
        return new ArrayList<>(products);
    }

    @Override
    public Product findById(Long id) {
        return productMap.get(id);
    }
}

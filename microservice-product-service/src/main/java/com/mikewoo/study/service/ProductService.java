package com.mikewoo.study.service;

import com.mikewoo.study.domain.Product;

import java.util.List;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
public interface ProductService {

    List<Product> listProduct();

    Product findById(Long id);
}

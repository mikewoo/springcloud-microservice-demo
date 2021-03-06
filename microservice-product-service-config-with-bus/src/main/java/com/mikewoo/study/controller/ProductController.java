package com.mikewoo.study.controller;

import com.mikewoo.study.domain.Product;
import com.mikewoo.study.service.ProductService;
import com.mikewoo.study.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Random;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Value("${server.port}")
    private Integer port;

    @Value("${server.env}")
    private String env;

    @Autowired
    private ProductService productService;

    /**
     * 获取所有商品信息
     * @return {@link ResponseResult}
     */
    @GetMapping("")
    public Object list() {
        return ResponseResult.build(200, "product service port: " + port + ", env: " + env, productService.listProduct());
    }

    /**
     * 根据商品ID查找商品信息
     * @param id 商品ID
     * @return {@link ResponseResult}
     */
    @GetMapping("/{id}")
    public Object findById(@PathVariable("id") Long id) {
        try {
            Thread.sleep(new Random().nextInt(40) * 30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Product product = productService.findById(id);
        if (Objects.nonNull(product)) {
            return ResponseResult.build(200, "product service port: " + port + ", env: " + env, product);
        }
        return ResponseResult.build(200, "用户不存在, product service port: " + port + ", env: " + env);
    }
}

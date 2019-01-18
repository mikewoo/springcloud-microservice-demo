package com.mikewoo.study.service;

import com.mikewoo.study.utils.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 商品服务Feign客户端
 * @author Eric Gui
 * @date 2019/1/18
 */
@FeignClient(name = "microservice-product-service")
public interface ProductClient {

    @GetMapping("/api/v1/product/{id}")
    public ResponseResult findById(@PathVariable("id") Long id);

}

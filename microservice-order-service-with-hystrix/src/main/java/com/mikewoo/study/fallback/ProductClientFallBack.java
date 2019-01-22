package com.mikewoo.study.fallback;

import com.mikewoo.study.service.ProductClient;
import com.mikewoo.study.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Eric Gui
 * @date 2019/1/22
 */
@Component
@Slf4j
public class ProductClientFallBack implements ProductClient {

    @Override
    public ResponseResult findById(Long id) {
        log.warn("feign 调用microservice-product-service中findById接口异常");
        return null;
    }
}

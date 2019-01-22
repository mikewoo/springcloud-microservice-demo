package com.mikewoo.study.controller;

import com.mikewoo.study.domain.Order;
import com.mikewoo.study.domain.ShoppingList;
import com.mikewoo.study.service.OrderService;
import com.mikewoo.study.utils.ResponseResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "saveOrderFailed")
    public Object save(@PathVariable("userId") Long userId, @RequestBody ShoppingList shoppingList) throws IOException {
        if (Objects.nonNull(shoppingList) && Objects.nonNull(userId)) {
            shoppingList.setUserId(userId);
            Order order = orderService.save(shoppingList);
            return ResponseResult.ok(order);
        }

        return ResponseResult.build(400, "请求参数不合法");
    }

    /**
     * 故障降级方法，参数必须可对应实际调用方法参数一致
     * @param userId
     * @param shoppingList
     * @return
     */
    private Object saveOrderFailed(Long userId, ShoppingList shoppingList) {
        return ResponseResult.build(-1, "服务暂时不可用，请稍后重试");
    }

}

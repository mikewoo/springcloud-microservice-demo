package com.mikewoo.study.controller;

import com.mikewoo.study.domain.Order;
import com.mikewoo.study.domain.ShoppingList;
import com.mikewoo.study.service.OrderService;
import com.mikewoo.study.utils.ResponseResult;
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
    public Object save(@PathVariable("userId") Long userId, @RequestBody ShoppingList shoppingList) throws IOException {
        if (Objects.nonNull(shoppingList) && Objects.nonNull(userId)) {
            shoppingList.setUserId(userId);
            Order order = orderService.save(shoppingList);
            return ResponseResult.ok(order);
        }

        return ResponseResult.build(400, "请求参数不合法");
    }

}

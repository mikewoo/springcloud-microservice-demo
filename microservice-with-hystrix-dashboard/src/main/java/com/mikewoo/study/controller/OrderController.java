package com.mikewoo.study.controller;

import com.mikewoo.study.domain.Order;
import com.mikewoo.study.domain.ShoppingList;
import com.mikewoo.study.service.OrderService;
import com.mikewoo.study.utils.ResponseResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ExecutorService msgSenderExecutorService;

    @PostMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "saveOrderFailed")
    public Object save(@PathVariable("userId") Long userId, @RequestBody ShoppingList shoppingList, HttpServletRequest request) throws IOException {
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
    private Object saveOrderFailed(Long userId, ShoppingList shoppingList, HttpServletRequest request) {
        final String remoteAddr = request.getRemoteAddr();
        msgSenderExecutorService.submit(() -> {
            String key = "ORDER:SAVE:FAILED:KEY";
            String value = redisTemplate.opsForValue().get(key);
            if (StringUtils.isBlank(value)) {
                log.info("紧急通知，服务调用失败，发送报警通知，客户端地址：{}", remoteAddr);
                // TODO: 调用短信服务，发送告警短信
                redisTemplate.opsForValue().set(key, "oder-save-failed", 30, TimeUnit.SECONDS);
            } else {
                log.info("服务调用失败，30内不再重复发送告警通知");
            }
        });
        return ResponseResult.build(-1, "服务暂时不可用，请稍后重试");
    }

}

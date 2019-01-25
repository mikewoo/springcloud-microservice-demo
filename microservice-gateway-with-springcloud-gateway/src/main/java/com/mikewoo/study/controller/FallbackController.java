package com.mikewoo.study.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局断路器Controller
 * @author Eric Gui
 * @date 2019/1/25
 */
@RestController
public class FallbackController {

    @RequestMapping(value = "/fallback")
    public Map<String, String> fallBackController() {
        Map<String, String> res = new HashMap();
        res.put("code", "-1");
        res.put("data", "service not available");
        return res;
    }
}

package com.mikewoo.study.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局鉴权过滤器
 * @author Eric Gui
 * @date 2019/1/25
 */
@Component
@Slf4j
public class TokenAuthorizationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Token");
        log.info("Token: {}", token);
        if (StringUtils.isNotBlank(token)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse response = exchange.getResponse();
        //设置headers
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        //设置body
        Map<String, String> res = new HashMap();
        res.put("code", "-1");
        res.put("data", "用户身份不合法");
        ObjectMapper objectMapper = new ObjectMapper();
        DataBuffer bodyDataBuffer = null;
        try {
            bodyDataBuffer = response.bufferFactory().wrap(objectMapper.writeValueAsString(res).getBytes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response.writeWith(Mono.just(bodyDataBuffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

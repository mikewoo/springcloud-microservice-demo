package com.mikewoo.study.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author Eric Gui
 * @date 2019/1/25
 */
@Configuration
public class GatewayConfiguration {

    /**
     * 接口限流操作
     * @return
     */
    @Bean(name="apiKeyResolver")
    public KeyResolver apiKeyResolver() {

        //根据api接口来限流
        return exchange -> Mono.just(exchange.getRequest().getPath().value());

    }

    /**
     * ip限流操作
     * @return
     */
    @Bean(name = "remoteAddrKeyResolver")
    public KeyResolver remoteAddrKeyResolver () {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

}

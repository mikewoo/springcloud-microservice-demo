package com.mikewoo.study.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author Eric Gui
 * @date 2019/1/23
 */
@Slf4j
@Component
public class LoginFilter extends ZuulFilter {

    /**
     * Filter类型：PRE_TYPE，ROUTE_TYPE，POST_TYPE
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * Filter执行顺序，值越小，优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 4;
    }

    /**
     * 过滤器是否生效
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info("request uri: {}", request.getRequestURI());
        log.info("request url: {}", request.getRequestURL());

        // 查看商品信息接口，不使用Filter拦截
        if ("/microservice/products/api/v1/product".equals(request.getRequestURI())) {
            return false;
        }

        return true;
    }

    /**
     * 业务逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        log.info("login filter running...");
        // 模拟token信息验证
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader("Token");
        log.info("request token: {}", token);

        if (StringUtils.isBlank(token)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        } else {

        }
        return null;
    }
}

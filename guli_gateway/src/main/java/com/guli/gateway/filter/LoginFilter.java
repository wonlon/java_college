package com.guli.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class LoginFilter extends ZuulFilter {
    /**
     * 过滤器类型，前置过滤器
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器顺序，越小越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }
    /**
     * 过滤器是否生效
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //鉴权URL(项目中我们会在数据库或缓存中取出ACL列表)
        String authUrl = "/vod-api/vod/get-play-auth/";
        //当前接口URI
        String requestURI = request.getRequestURI();
        if(!StringUtils.isEmpty(requestURI) && requestURI.toLowerCase().contains(authUrl)){
            //执行过滤方法
            return true;
        }
        //放行(不执行过滤方法)
        return false;
    }
    //过滤逻辑-在这块写
    @Override
    public Object run() throws ZuulException {
        System.out.println("执行过滤");
        return null;
    }
}

package com.fc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;

/**
 * @author 890170
 * @date 2017-03-09 14:55
 * @description 登录拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{

    private List<String> excludedUrls;

    /**
     * @author 890170
     * @date 2017-03-09 14:59
     * @description 预处理(进入action前的处理)
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();

        //如果url在excludedUrls列表中,则不用拦截,否则需要拦截
        for (String s : excludedUrls) {
            if (requestUri.endsWith(s)) {
                return true;
            }
        }

        Integer uid =  (Integer)request.getSession().getAttribute("uid");
        if(uid == null){ //如果没有登录,则重定向到登录页面
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            return false;
        }else{ //如果已登录,则无需再次登录,所以不用拦截
            return true;
        }
    }

    /**
     * @author 890170
     * @date 2017-03-09 15:00
     * @description 后处理（调用了Service并返回ModelAndView，但进行页面渲染前调用）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * @author 890170
     * @date 2017-03-09 15:01
     * @description 返回处理（已经渲染了页面后的处理）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    public List<String> getExcludedUrls() {
        return excludedUrls;
    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }
}

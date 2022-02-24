package com.crowd.mvc.interceptor;

import com.crowd.constant.CrowdConstant;
import com.crowd.entity.Admin;
import com.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
// 1.把session是域对象，通过request可以获取session
        HttpSession session = httpServletRequest.getSession();
// 2.尝试从Session中获取登录的Admin对象
        Admin admin = (Admin)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
// 3.判断是否为空,为空抛异常--->响应登录的数据
        if(admin == null){
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
        }
// 4.不为空，放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

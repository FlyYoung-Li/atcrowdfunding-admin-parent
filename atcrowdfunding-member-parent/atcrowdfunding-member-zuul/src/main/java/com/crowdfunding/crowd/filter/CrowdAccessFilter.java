package com.crowdfunding.crowd.filter;

import com.crowd.constant.AccessPassResources;
import com.crowd.constant.CrowdConstant;

import com.crowdfunding.crowd.entity.vo.MemberLoginVO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-02 08:41
 **/
@Component
public class CrowdAccessFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 1.获取RequestContext
        RequestContext context = RequestContext.getCurrentContext();
        // 2.线程本地化,框架底层是通过ThreadLocal获取当前线程中请求(线程对应请求)
        HttpServletRequest request = context.getRequest();
        // 3..获取当前请求的servletPath
        String servletPath = request.getServletPath();
        // 4.判断是否是特定放行的非静态资源
         boolean whetherContainsResources = AccessPassResources.PASS_RES_SET.contains(servletPath);
        if (whetherContainsResources) {
            return false;
        }
        // 5.判断是否是放行的静态资源
        boolean whetherContainsStaticResources = AccessPassResources.judgeCurrentServletPathWhetherStaticResources(servletPath);
        if (whetherContainsStaticResources) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        // 1.获取请求上下文
        RequestContext requestContext = RequestContext.getCurrentContext();

        // 2.获取请求(通过线程本地化技术)
        HttpServletRequest request = requestContext.getRequest();

        // 3.获取session(Redis中)
        HttpSession session = request.getSession();

        // 4.取出登录对象进行判断
        MemberLoginVO loginMember = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        // 5.没有携带错误信息,返回登录页面(携带信息就是属性域来完成,两个微服务,通过session来进行信息的传递,前提(session共享))
        if(loginMember == null){

            session.setAttribute(
                    CrowdConstant.ATTR_NAME_MESSAGE,
                    CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);

//            return "redirect:/auth/member/to/login/page";

            HttpServletResponse response = requestContext.getResponse();
            try {
                response.sendRedirect("/auth/member/to/login/page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

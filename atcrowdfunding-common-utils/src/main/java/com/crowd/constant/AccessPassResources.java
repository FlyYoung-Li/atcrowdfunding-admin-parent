package com.crowd.constant;


import java.util.HashSet;
import java.util.Set;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-28 16:20
 **/
public class AccessPassResources {
    //放行的非静态资源
    public static final Set<String> PASS_RES_SET = new HashSet<>();

    static {
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/auth/member/to/register/page");
        PASS_RES_SET.add("/auth/member/do/register");
        PASS_RES_SET.add("/auth/member/to/login/page");
        PASS_RES_SET.add("/auth/member/do/login");
        PASS_RES_SET.add("/auth/member/send/short/message");
        PASS_RES_SET.add("/auth/member/logout");

    }
    // 放行的静态资源
    public static final Set<String> STATIC_RES_SET = new HashSet<>();

    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    /**
     * 判断servlet值是否是静态资源
     * @param servletPath
     * @return
     *      true:静态资源
     *      false:非静态资源
     */
    public static Boolean judgeCurrentServletPathWhetherStaticResources(String servletPath){
        // 1.判断字符串无效的情况(就是"",首页的servletPath是"/",不一样)
        if( servletPath == null|| "".equals(servletPath)){
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        // 2.根据"/"拆分servletPath(注意路径是"/",最少有两个)
        String[] split = servletPath.split("/");

        // 3.servletPath,要带"/",分割后第一个元素是空串
        String firstLevelPath = split[1];
        // 4.判断当前请求的servletPath是否在集合中
        return STATIC_RES_SET.contains(firstLevelPath);
    }
//main方法,测试一下
//    public static void main(String[] args) {
//        String servletPath1 = "/aa/bb/cc";
//        String servletPath2 = "/css/bb/cc";
//
//        Boolean aBoolean = judgeCurrentServletPathWhetherStaticResources(servletPath1);
//        Boolean aBoolean1 = judgeCurrentServletPathWhetherStaticResources(servletPath2);
//
//        System.out.println(aBoolean);
//        System.out.println("*************************");
//        System.out.println(aBoolean1);
//    }
}

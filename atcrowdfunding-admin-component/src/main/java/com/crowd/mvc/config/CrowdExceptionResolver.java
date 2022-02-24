package com.crowd.mvc.config;

import com.crowd.exception.AccessForbiddenException;
import com.crowd.exception.LoginAcctAlreadyInUseException;
import com.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.crowd.exception.LoginFailedException;
import com.crowd.util.CrowdUtil;
import com.crowd.util.ResultEntity;
import com.crowd.constant.CrowdConstant;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/9/4 20:38
 */

@ControllerAdvice
public class CrowdExceptionResolver {
    private Logger logger= LoggerFactory.getLogger(CrowdExceptionResolver.class);

    /**
     * 处理LoginAcctAlreadyInUseForUpdateException异常的方法
     * @param request 引起LoginAcctAlreadyInUseForUpdateException异常的请求
     * @param response 如果为ajax请求，通过response响应ResultEntity
     * @param exception LoginAcctAlreadyInUseForUpdateException
     * @return modelAndView(也可能返回null,如果返回hull，说明是ajax请求，已经通过response响应了ResultEntity json字符串)
     * @throws IOException
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseForUpdateException(
            HttpServletRequest request,
            HttpServletResponse response,
            LoginAcctAlreadyInUseForUpdateException exception) throws IOException {

        String view ="system-error";
        ModelAndView modelAndView = commonResolve(request, response, exception, view);
        return modelAndView;
    }
    /**
     * 处理LoginAcctAlreadyInUseException异常的方法
     * @param request 引起LoginAcctAlreadyInUseException异常的请求
     * @param response  如果为ajax请求，通过response响应ResultEntity
     * @param exception LoginAcctAlreadyInUseException
     * @return modelAndView(也可能返回null,如果返回hull，说明是ajax请求，已经通过response响应了ResultEntity json字符串)
     * @throws IOException
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(
            HttpServletRequest request,
            HttpServletResponse response,
            LoginAcctAlreadyInUseException exception) throws IOException {
        String view ="admin-add";
        ModelAndView modelAndView = commonResolve(request, response, exception, view);
        return modelAndView;
    }
    /**
     *  处理AccessForbiddenException异常的方法
     * @param request 发生AccessForbiddenException异常的请求
     * @param response  如果为ajax请求，通过response响应ResultEntity
     * @param exception AccessForbiddenException
     * @return modelAndView(也可能返回null,如果返回hull，说明是ajax请求，已经通过response响应了ResultEntity json字符串)
     * @throws IOException
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Exception exception) throws IOException {

        String view ="system-error";

        ModelAndView modelAndView = commonResolve(request, response, exception, view);
        return modelAndView;
    }
    /**
     *  处理LoginFailedException异常的方法
     * @param request 抛出LoginFailedException异常的请求
     * @param response 该请求的最后处理，要进行响应（返回json字符串）
     * @param exception LoginFailedException
     * @return  modelAndView(也可能返回null,如果返回hull，说明是ajax请求，已经通过response响应了ResultEntity json字符串)
     * @throws IOException
     */
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(
            HttpServletRequest request,
            HttpServletResponse response,
            LoginFailedException exception) throws IOException {
        String view ="admin-login";
        ModelAndView modelAndView = commonResolve(request, response, exception, view);
        return modelAndView;
    }

    /**
     * 处理空指针异常的方法
     * @param request 抛出NullPointerException异常的请求
     * @param response 该请求的最后处理，要进行响应（返回json字符串）
     * @param exception NullPointerException
     * @return  modelAndView(也可能返回null,如果返回hull，说明是ajax请求，已经通过response响应了ResultEntity json字符串)
     * @throws IOException
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(
            //在判断请求类型的时候使用
            HttpServletRequest  request,
            //将json字符串作为响应体的使用使用
            HttpServletResponse response,
            //添加错误信息的时候使用
            NullPointerException exception) throws IOException {
        String view ="system-error";
        return commonResolve(request, response, exception,view);

    }

    /**
     * 处理算数异常的方法
     * @param request 抛出ArithmeticException异常的请求
     * @param response 该请求的最后处理，要进行响应（返回json字符串）
     * @param exception ArithmeticException
     * @return  modelAndView(也可能返回null,如果返回hull，说明是ajax请求，已经通过response响应了ResultEntity json字符串)
     * @throws IOException
     */
    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView resolveMathException(
            HttpServletRequest request,
            HttpServletResponse response,
            ArithmeticException exception
    ) throws IOException {
        String view = "system-error";
        System.out.println("算数异常发生，我进行处理");
        return commonResolve(request,response,exception,view);
    }

    //异常处理方法共同的步骤
    public ModelAndView commonResolve(
            //在判断请求类型的时候使用
            HttpServletRequest  request,
            //将json字符串作为响应体的使用使用
            HttpServletResponse response,
            //捕获到的异常
           Exception exception,
            //配置modelAndView对象返回的视图
            String viewName) throws IOException {

        //工具类判断是否为ajax请求
        boolean requestType = CrowdUtil.judgeRequestType(request);
        //如果是ajax请求，一定要重新设置返回json数据
        //1.创建返回的对象ResultEntity
        if (requestType){
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            //2.使对象变成json字符串
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            //3.将json字符串作为响应体返回
            response.getWriter().write(json);

            return null;
        }//不是ajax请求，就是普通的请求（设置modelAndView）
        //1.创建ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //2.添加Model对象(直接将捕获到的异常对象)
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);
        logger.info("异常对象的message"+exception.getMessage());
        logger.info("异常对象的toString"+exception.toString());
        //3.是指view对象（返回的页面，遵守视图解析器）
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

}

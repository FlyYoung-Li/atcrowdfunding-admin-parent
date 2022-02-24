package com.crowd.constant;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/9/5 11:01
 */
public class CrowdConstant {
    //全局常量(这里表示的是属性名称，也就是key)
    public static final String ATTR_NAME_EXCEPTION = "exception";
    public static final String ATTR_NAME_LOGIN_ADMIN="loginAdmin";
    public static final String ATTR_NAME_LOGIN_MEMBER="loginMember";
    public static final String ATTR_NAME_PAGE_INFO = "pageInfo";
    public static final String ATTR_NAME_UPDATE_ADMIN = "updateAdmin";
    public static final String REDIS_CODE_PREFIX ="redis_code_prefix_" ;
    public static final String ATTR_NAME_MESSAGE = "message";
    public static final String ATTR_NAME_TEMPORARY_PROJECT = "projectVO";
    public static final String ATTR_NAME_PORTAL_DATA = "portal_data";
    public static final String ATTR_NAME_ORDER_PROJECTVO = "order_projectVO";
    public static final String ATTR_NAME_ADDRESSVOLIST = "addressVOList";
    public static final String ATTR_NAME_ORDERVO = "orderVO";

    //全局常量（表示提示的信息）
    public static final String MESSAGE_LOGIN_FAILED="很抱歉，账号或密码错误，请重新登录";
    public static final String MESSAGE_LOGIN_ACCOUNT_ALREADY_IN_USE="很抱歉，账号已经被使用了";
    public static final String MESSAGE_ACCESS_FORBIDDEN= "请登录后再访问";
    public static final String MESSAGE_STRING_INVALIDATE="字符串不合法！请不要传入空字符串";
    public static final String MESSAGE_ACCT_OR_PSWD_IS_NULL = "账号或密码不能为空";
    public static final String MESSAGE_SYSTEM_ERROR_ACCOUNT_NOT_UNIQUE = "系统错误，账户不唯一";
    public static final String MESSAGE_REQUEST_FORBIDDEN_FOR_NO_AUTHORITY = "抱歉，你没有权限访问";
    public static final String MESSAGE_INVALIDATE_CODE = "验证码错误或者没有输入";
    public static final String MESSAGE_ERROR_PHONE_NUMBER = "请输入正确的手机号";
    public static final String MESSAGE_NULL_PHONE_NUMBER = "手机号为空";
    public static final String MESSAGE_CODE_NOT_EXISTS = "验证码已过期，请重新输入";
    public static final String MESSAGE_ERROR_ACCOUNT = "请输入正确的账号";
    public static final String MESSAGE_UPLOAD_PIC_INVALID = "上传图片失效";
    public static final String MESSAGE_NULL_HEAD_PIC = "头图不能为空";
    public static final String MESSAGE_NULL_DETAIL_PIC = "详情图片不能为空";
    public static final String MESSAGE_NULL_RETURN_PIC = "回报图片不能为空";
    public static final String MESSAGE_ERROR_CONTENT = "内容填写错误，请重新填入";
    public static final String MESSAGE_TEMPORARY_PROJECTVO_INVALID = "暂时存储的projectVO对象丢失";
    public static final String MESSAGE_ERROR_CREATE_PROJECT = "创建保存项目失败";
    public static final String MESSAGE_NULL_PROJECT_RECENTLY = "当前还没有项目上传";
}

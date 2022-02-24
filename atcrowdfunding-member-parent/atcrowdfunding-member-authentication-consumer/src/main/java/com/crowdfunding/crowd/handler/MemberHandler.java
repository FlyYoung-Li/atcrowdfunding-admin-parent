package com.crowdfunding.crowd.handler;

import com.crowd.constant.CrowdConstant;
import com.crowd.util.CrowdUtil;
import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.po.MemberPO;
import com.crowdfunding.crowd.entity.vo.MemberLoginVO;
import com.crowdfunding.crowd.entity.vo.MemberVO;
import com.crowdfunding.crowd.properties.ShortMessageProperties;
import com.crowdfunding.crowd.service.api.MysqlRemoteService;
import com.crowdfunding.crowd.service.api.RedisRemoteService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-24 14:15
 **/
@Controller
public class MemberHandler {
    // 取出配置类默认信息
    @Autowired
    private ShortMessageProperties shortMessageProperties;
    @Autowired
    private RedisRemoteService redisRemoteService;
    @Autowired
    private MysqlRemoteService mysqlRemoteService;
    /**
     * 处理发送验证码的请求映射方法
     * @param phoneNumber 用户接收验证码的手机
     * @return 返回ResultEntity
     *            成功：没有数据
     *            失败：错误信息
     */
    @ResponseBody
    @RequestMapping("/auth/member/send/short/message")
    public ResultEntity<String> sendShortMessage(@RequestParam String phoneNumber){
        // 1.先不着急返回页面，先是获取要返回的ResultEntity，进行一个判断
        ResultEntity<String> resultEntity = CrowdUtil.sendShortMessage(
                shortMessageProperties.getHost(),
                shortMessageProperties.getPath(),
                shortMessageProperties.getMethod(),
                shortMessageProperties.getAppcode(),
                phoneNumber,
                shortMessageProperties.getTemplateId());
        // 2.判断短信是否发送成功，发送成功将验证码存到Redis中
        if(ResultEntity.SUCCESS == resultEntity.getResult()){
            // 要存的验证码
            String value = resultEntity.getData();
            // 要存的验证码的key（常量）
            String key = CrowdConstant.REDIS_CODE_PREFIX+phoneNumber;
            redisRemoteService.setRedisKeyValueRemoteWithTimeout(key,value,6000L, TimeUnit.SECONDS);
            // 如果，短信发送成功，存入Redis中，响应给浏览器的时候，不用再带数据。
            // （相当于我们调用第三方接口返回的ResultEntity中的data数据是返回给Handler存入Redis中的，不是为了发到浏览器）
            return ResultEntity.successWithoutData();
        }else{
            // 如果失败，直接发送第三方接口返回的ResultEntity，携带错误信息
            return resultEntity;
        }

    }
    @RequestMapping("/auth/member/do/register")
    public String memberDoRegister( MemberVO memberVO, Model model){
// Redis-provider
        //后面添加的:保证输入用户、密码、邮箱不能为空
        String loginacct = memberVO.getLoginacct();
        String userpswd = memberVO.getUserpswd();
        String email = memberVO.getEmail();
        if(loginacct == null || "".equals(loginacct)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCT_OR_PSWD_IS_NULL);
            return "member-register";
        }
        if(userpswd == null || "".equals(userpswd)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCT_OR_PSWD_IS_NULL);
            return "member-register";
        }
        if(email == null || "".equals(email)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,"邮箱不能为空");
            return "member-register";
        }
        // 1.获取手机号
        String phoneNumber = memberVO.getPhoneNumber();
        if(phoneNumber == null || "".equals(phoneNumber)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_NULL_PHONE_NUMBER);
            return "member-register";
        }

        // 2.从Redis中获取验证码
        String key = CrowdConstant.REDIS_CODE_PREFIX+phoneNumber;
        ResultEntity<String> resultEntity = redisRemoteService.getRedisValueByKeyRemote(key);
        if("FAILED".equals(resultEntity.getResult())){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "member-register";
        }

        String redisCode = resultEntity.getData();
        if(redisCode == null || "".equals(redisCode)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-register";
        }
        // 3.获取MemberVO的验证码
        String formCode = memberVO.getCode();

        // 4.校验失败，返回注册页面
        if(!Objects.equals(formCode,redisCode)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_INVALIDATE_CODE);
            return "member-register";
        }
        // 5.校验成功，Redis删掉验证码(如果失败， 用户也解决不了，写进日志,自己解决，不影响用户)
        ResultEntity<String> stringResultEntity = redisRemoteService.removeRedisKeyValueRemote(key);

// mysql-provider
        // 6.MemberVO 转成 MemberPO（密码改成密文）
        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO,memberPO);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String beforeEncodeUserpswd = memberPO.getUserpswd();
        String afterEncoderUserpswd = passwordEncoder.encode(beforeEncodeUserpswd);

        memberPO.setUserpswd(afterEncoderUserpswd);
        // 7.直接存储
        ResultEntity<String> entity = mysqlRemoteService.addMemberPO(memberPO);
        // 8.失败，返回注册页面
        if("FAILED".equals(entity.getResult())){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,entity.getMessage());
           return "member-register";
        }
        return "redirect:http://www.crowd.com/auth/member/to/login/page";
    }

    @RequestMapping("/auth/member/do/login")
    public String memberDoLogin(@RequestParam("loginacct") String loginacct,
                                @RequestParam("userpswd") String userpswd ,
                                 Model model,
                                HttpSession session){
        // 1.拿账号去数据库查

        if(loginacct == null || "".equals(loginacct)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCT_OR_PSWD_IS_NULL);
            return "member-login";
        }
        ResultEntity<MemberPO> getMemberPOResultEntity = mysqlRemoteService.getMemberPO(loginacct);

        // 2.查不到（账号不存在），返回登录页面
        String result = getMemberPOResultEntity.getResult();
        if("FAILED".equals(result)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,getMemberPOResultEntity.getMessage());
            return "member-login";
        }
        // 3.调用mysql-provider查询操作后，判断是否查出memberPO对象
        MemberPO memberPO = getMemberPOResultEntity.getData();
        if(memberPO == null){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        // 3.查到，memberVO取出密码，加密后，与查到的memberPO中的密码比较
        if(userpswd == null || "".equals(userpswd)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCT_OR_PSWD_IS_NULL);
            return "member-login";
        }
        // 4.密码不等，返回登录页面
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        String poUserpswd = memberPO.getUserpswd();
        boolean matches = encoder.matches(userpswd, poUserpswd);

        if(!matches){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 5.相等，跳转到登陆后的页面(登录信息存入session)

        MemberLoginVO loginVO = new MemberLoginVO(memberPO.getId(),memberPO.getUsername(),memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER,loginVO);

        return "redirect:http://www.crowd.com/auth/member/to/main/page";
    }

    /**
     * 退出登录，session失效
     * @param session
     * @return portal首页
     */
    @RequestMapping("/auth/member/logout")
    public String memberDoLogout(HttpSession session){

        session.invalidate();

        return "redirect:http://www.crowd.com/";
    }
}

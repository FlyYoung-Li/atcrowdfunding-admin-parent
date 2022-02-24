package com.crowd.util;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.crowd.constant.CrowdConstant;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/9/4 19:22
 */
//前面还有一些思考，我现在要创建判断请求的工具类，工具类命名为什么写成这样，这个工具类就是针对于这个项目的
public class CrowdUtil {

    private static Logger logger = LoggerFactory.getLogger(CrowdUtil.class);

    /**
     * 工具方法：判断发过来的请求是ajax请求还是普通请求
     *
     * @param request 页面发过来的请求
     * @return 是ajax请求，返回true；普通请求，返回false；
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String xRequestedWith = request.getHeader("X-Requested-With");
        //直接根据请求头内容，非空并且包含指定内容，判断为ajax请求
        return (accept != null && accept.contains("application/json")) ||
                (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest"));
    }

    /**
     * 工具方法，对发过来的密码（或者String）进行MD5加密
     *
     * @param resource 要过来要明文加密的数据
     * @return 加密好的数据
     */
    public static String md5(String resource) {
        // 1.判断resource是否为空
        if (resource == null || resource.length() == 0) {
            // 2.如果为空，抛出异常(不用throws和try-catch)
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        // 3.不为空，获取MessageDigest对象（这个需要处理异常，我们try-catch）
        String algorithm = "md5";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 4.获取明文字符串对应的字节数组
            byte[] input = resource.getBytes();
            // 5.执行加密
            byte[] output = messageDigest.digest(input);
            // 6.创建BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            // 7.按照16进制，将bigInteger变成String类型
            int radix = 16;
            String encoded = bigInteger.toString(radix);
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  给远程第三方短信接口发送请求把验证码发送到用户手机上
     * @param host          短信接口调用的URL地址
     * @param path          具体发送短信功能的地址
     * @param method        请求方式
     * @param appcode       调用第三方短信API的appcode
     * @param phone_number  接收短信的手机号
     * @param template_id   发送的模板编号
     * @return              ResultEntity，成功返回验证码；失败返回错误消息
     */
  public static ResultEntity<String> sendShortMessage(
          String host,
          String path,
          String method,
          String appcode,
          String phone_number,
          String template_id){
      // 短信接口调用的URL地址
//      String host = "https://dfsns.market.alicloudapi.com";
      // 具体发送短信功能的地址
//      String path = "/data/send_sms";
      // 请求方式
//      String method = "POST";
      // 登录到阿里云云市场控制台已购买自己的APPCODE
//      String appcode = "cba5876432a3491c892bcaa7138f0823";

      Map<String, String> headers = new HashMap<String, String>();
      //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
      headers.put("Authorization", "APPCODE " + appcode);
      //根据API的要求，定义相对应的Content-Type
      headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
      Map<String, String> querys = new HashMap<String, String>();
      Map<String, String> bodys = new HashMap<String, String>();
      //生成随机的验证码
      StringBuilder builder = new StringBuilder();

      for (int i = 0; i < 4; i++) {
          int random = (int)(Math.random()*10);
          builder.append(random);
      }
      String code = builder.toString();

      bodys.put("content", "code:"+code+",expire_at:5");
      bodys.put("phone_number", phone_number);
      bodys.put("template_id", template_id);


      try {
          /**
           * 重要提示如下:
           * HttpUtils请从
           * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
           * 下载
           *
           * 相应的依赖请参照
           * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
           */
          HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);

          // 测试获取statuLine的响应消息和响应码
          StatusLine statusLine = response.getStatusLine();
          int statusCode = statusLine.getStatusCode();
          String reasonPhrase = statusLine.getReasonPhrase();

          //获取response结果（toString的结果）
          logger.debug(response.toString());
          //获取response的body（json的结果）
          logger.debug(EntityUtils.toString(response.getEntity()));

          // 返回ResultEntity结果一：没有抛出异常，响应码200
          if(statusCode == 200){
              return ResultEntity.successWithData(code);
          }  // 返回ResultEntity结果二：没有抛出异常，响应码不是200
          return ResultEntity.failed(reasonPhrase);
      } catch (Exception e) {
          // 返回ResultEntity结果三：抛出异常
          e.printStackTrace();
          return ResultEntity.failed(e.getMessage());
      }

  }

    /**
     *
     * @param endpoint          oss参数
     * @param bucketName        oss参数
     * @param bucketDomain      oss参数
     * @param accessKeyId       oss参数
     * @param accessKeySecret   oss参数
     * @param inputStream       上传的文件流
     * @param originalName      上传的原始文件名
     * @return
     */
    public static ResultEntity<String> uploadFileToOss(
            String endpoint,
            String bucketName,
            String bucketDomain,
            String accessKeyId,
            String accessKeySecret,
            InputStream inputStream,
            String originalName
    ){
      // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

      // 生成上传文件的目录
        String uploadFileDirectory=new SimpleDateFormat("yyyyMMdd").format(new Date());

      // 上传的原始文件名:beautifulGirl.jpg
      // 生成上传后文件名称的前缀
        String uploadPrefixFileName = UUID.randomUUID().toString().replace("-", "");

      // 生成上传后文件名称的后缀
        String uploadSuffixFileName = originalName.substring(originalName.lastIndexOf("."));

      // 拼成完整的上传文件名称
      String uploadFileName = uploadPrefixFileName+uploadSuffixFileName;

      // 拼成完整的对象名称(目录+文件名称)
      String ObjectName = uploadFileDirectory+"/"+uploadFileName;

      // 调用OSS客户端对象上传文件并获取响应的结果
        try {
            PutObjectResult objectResult = ossClient.putObject(bucketName, ObjectName, inputStream);

            ResponseMessage responseMessage = objectResult.getResponse();
            // bucket域名+文件对象(目录+文件)
            // 响应结果如果为null,说明成功;
            if(responseMessage == null){
                String ossFileAccessPath = bucketDomain +"/"+ ObjectName;

                return ResultEntity.successWithData(ossFileAccessPath);
            }else{
                // 还是先获取,然后再输出
                int statusCode = responseMessage.getStatusCode();
                String errorResponseAsString = responseMessage.getErrorResponseAsString();

                return ResultEntity.failed("错误信息:"+errorResponseAsString+"状态码:"+statusCode);
            }
        } catch (Exception e) {

            e.printStackTrace();

            return  ResultEntity.failed(e.getMessage());
        }finally{
            // 一定要关闭,也要防止空指针异常
            if(ossClient != null){
                ossClient.shutdown();
            }
        }

    }

}

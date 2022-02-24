package com.shortmessage;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-23 09:16
 **/
public class ShortMessageTest {
    public static void main(String[] args) {
//      总的地址：http(s)://dfsns.market.alicloudapi.com/data/send_sms

        // 短信接口调用的URL地址
        String host = "https://dfsns.market.alicloudapi.com";
        // 具体发送短信功能的地址
        String path = "/data/send_sms";
        // 请求方式
        String method = "POST";
        // 登录到阿里云云市场控制台已购买自己的APPCODE
        String appcode = "cba5876432a3491c892bcaa7138f0823";

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:1313,expire_at:5");
        bodys.put("phone_number", "13294542523");
        bodys.put("template_id", "TPL_0001");


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
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

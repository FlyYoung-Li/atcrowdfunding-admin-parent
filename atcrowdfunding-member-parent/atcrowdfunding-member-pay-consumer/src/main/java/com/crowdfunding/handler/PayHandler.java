package com.crowdfunding.handler;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.crowd.constant.CrowdConstant;
import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.vo.OrderProjectVO;
import com.crowdfunding.crowd.entity.vo.OrderVO;
import com.crowdfunding.crowd.service.api.MysqlRemoteService;
import com.crowdfunding.handler.config.PayProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-26 00:31
 **/
@Controller
public class PayHandler {

    private Logger logger = LoggerFactory.getLogger(PayHandler.class);
    @Autowired
    private  MysqlRemoteService mysqlRemoteService;


    @Autowired
    private PayProperties payProperties;
    @ResponseBody
    @RequestMapping("/to/generate/order")
    public String generateOrder(OrderVO orderVO,HttpSession session) throws AlipayApiException {

        // 1.给订单对象设置订单号
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String id = UUID.randomUUID().toString().replace("-", "");
        String orderNumber =time+id;

        orderVO.setOrderNumber(orderNumber);
        // 2.给订单对象设置orderProjectVO。（订单中的项目信息）
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_ORDER_PROJECTVO);
        orderVO.setOrderProjectVO(orderProjectVO);
        // 项目的orderVO对象中还有一个属性支付宝交易流水号，需要在支付宝处理完请求，响应的return方法中设置。
        session.setAttribute(CrowdConstant.ATTR_NAME_ORDERVO,orderVO);
        // 3.使用封装好的方法，给支付宝发请求
        return sendRequestToAliPay(orderVO.getOrderNumber(),orderVO.getOrderAmount(),orderProjectVO.getProjectName(),orderProjectVO.getReturnContent());
    }

    /**
     *
     * @param outTradeNo    商户订单号也就是我们自己生成的订单号
     * @param totalAmount   订单的总金额
     * @param subject       订单的名称，这里可以使用项目名称
     * @param body          商品的描述，这里可以使用回报信息
     * @return              返回到页面上支付宝的登陆界面
     * @throws AlipayApiException
     */
    private String sendRequestToAliPay(String outTradeNo, Double totalAmount, String subject, String body) throws AlipayApiException {

        // 获得默认初始化的alipayClient对象
        AlipayClient alipayClient = new DefaultAlipayClient(
                payProperties.getGatewayUrl(),
                payProperties.getAppId(),
                payProperties.getMerchantPrivateKey(),
                "json",
                payProperties.getCharset(),
                payProperties.getAlipayPublicKey(),
                payProperties.getSignType()
        );
        // 获取默认的请求参数
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();

        alipayTradePagePayRequest.setNotifyUrl(payProperties.getNotifyUrl());
        alipayTradePagePayRequest.setReturnUrl(payProperties.getReturnUrl());

        alipayTradePagePayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\","
                + "\"total_amount\":\""+ totalAmount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        // 请求发给AliPay，返回结果
        return alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
    }
    // 根据支付宝发过来请求，返回json数据（包括返回支付宝交易流水号），返回同步通知
    @ResponseBody
    @RequestMapping("/return")
    public String returnUrlMethod(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException, AlipayApiException {
//        获取支付宝get过来的信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        // 验签操作，就是解密
        boolean signVerified = AlipaySignature.rsaCheckV1(params, payProperties.getAlipayPublicKey(), payProperties.getCharset(), payProperties.getSignType());
        //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String orderNum = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String payOrderNum = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String orderAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            // 将支付宝交易号，存进orderVO对象中
            OrderVO orderVO = (OrderVO) session.getAttribute(CrowdConstant.ATTR_NAME_ORDERVO);
            orderVO.setPayOrderNumber(payOrderNum);
            // 此时支付宝完成支付，返回支付宝交易流水号，设置进orderVO中，设置进数据库中。（session中先更新，后面session的销毁）
            session.setAttribute(CrowdConstant.ATTR_NAME_ORDERVO,orderVO);

            ResultEntity saveOrderVOResult = mysqlRemoteService.saveOrderVO(orderVO);
                String success = null;
            if(ResultEntity.SUCCESS.equals( saveOrderVOResult.getResult())){
                success = "支付成功：";
            }

            return success+"\\ntrade_no:"+orderNum+"<br/>out_trade_no:"+payOrderNum+"<br/>total_amount:"+orderAmount;

        }else {
            return "验签失败,保存订单对象失败";
        }
    }
    @RequestMapping("/notify")
    public void notifyUrlMethod(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {

//获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, payProperties.getAlipayPublicKey(), payProperties.getCharset(), payProperties.getSignType());


        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

//            if(trade_status.equals("TRADE_FINISHED")){
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意：
//                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//            }else if (trade_status.equals("TRADE_SUCCESS")){
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意：
//                //付款完成后，支付宝系统发送该交易状态通知
//            }

            logger.info("success");
        }else {//验证失败
            logger.info("failed");

            //调试用，写文本函数记录程序运行情况是否正常
//		String sWord = AlipaySignature.getSignCheckContentV1(params);
//		AlipayConfig.logResult(sWord);
        }
    }
}

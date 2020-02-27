package com.chii.demo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.chii.demo.config.AlipayConfig;
import com.chii.demo.entity.AlipayBean;
import com.chii.demo.controller.XiaDanController;
import com.chii.demo.mapper.OrderMapper;
import com.chii.demo.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PayController {
    @Autowired
    OrderMapper orderMapper;

    @RequestMapping("/visitUrl")
    @ResponseBody
    public ModelAndView visitUrl(HttpServletRequest request){
        String userAgent = request.getHeader("user-agent");
        if (userAgent == null || (!userAgent.contains("AlipayClient")
        && !userAgent.contains("MicroMessenger"))){
            return new ModelAndView("visit_return_url", "msg", "请使用微信或支付宝扫码");
        }else {
            String desk_id = request.getParameter("desk_id");
            String appid = "2016102100729554";
            //商户应用私钥
            String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCeBpSvc226wCxREo5jS7U35daejX/fDrSofmIieu6tG85kqKrFc2tdvoDmd801kEmKUE8jrG2BWuM7u6cOVHMjoIlewU4N2RB+33FQtVs+KAEITv5Jou9su0DmWtHiH92fIqg3yh0NOhRBJbOJ1bx6MS1vOrrxbUHutkepuaoWkR60qXPJc8csFPGbNdqFHAfkuQV2vwLFRAU5Gw//FohN4JbqzvePsetAz63mRC65E4oS1vleb9YLKOt6fBicticF456u3XoImY3rH2zylXQSNKA3jEmn30NehJrlTPv/XJ48Ra/UND+7HdZeoa7leD9Rv50oRuFldq7HNKp8dpetAgMBAAECggEAQienHzxHd6Lz6ozGJzOOjfQeQQojuhHB97fRBXZJbRby9JjXxQlorToPZGxK8F2TK+ArAVgyD7Eo59zLNuiLuyJ937k1H/77NOH94jfKFt9Qb1YChnk7ml1Z8hWbP/rvIKu1mIV4XA0wZYWO/+kGmnD0AFip4mBG9dRBdABSAFffQka/kugmFwnqrKMo3BZi/9XCScOAfVzbCQ4dDKkSMWVCk9HV9YMUoVKiS4hCEVTs0E5/vnEO96yCfV593pi8tl0B+W5I5YgTUYibLTfql3loGkmTCPEjdd0FMfZyuspH6VdGNpR2JAcCSXK5mYRaiHGtVSd+IaoyzhtZWHT6UQKBgQDdTTWah9ADc3tV02tlKm8fcLq3Yt95wyDptppUHhm7B68yltuswW/jGzbE1ECZJnSEn825Le+6L4IBsWFyKRnsXqjxxvhByNsSQl9wI+ttJtb6fcRvWj+9sVrya+p/l4iUPuNtQtAW/FDQ++BFVF39mnytgT7qk9HlX2C1Kl1fdwKBgQC2zYzWhDCJ1yFf2lLJQ4rECs84eNDYnFgaTC/Vt8zaKj4xq5TZDPOFZlwm23+OnaQTxR3HLcSyHU71owZ3JyXiNKF/bGPymwcRm9/HwQMoGv2Bti6XW/QhUtYGicXfChAarUIqjUK/hs4oDgW5BpPuESvRBWBjt6BXRK/MFJFy+wKBgHrQxaXsJ4oyxcj820Y6xY7qTgVGbwWxQAvUllOGnPsKKbXmuSVn+QNN8BhOP0d/avzLfy19C+UFRp5P5eeoXcWrRxFfPhmsMcAxa6vdk2NxQa+kqqatrGBHFFUjhPGolFjJigfyI3AOOX+xuWZgiwUafoUADH286ajlRNNmHonDAoGBALauIaG6hpspXxPgJT02fzU8rCr+KY9eZnkZS/Bi9pfLAU437s8drzrPuSWn0whdpzuOkBydM2Tf/ylgmrR2bdhpyj6BvjwTCvRg9jg0PYhVuKNowZTG8uheVL5B7njfIIrYPDgz5NFr0RecM8HcvfZ6OHRw0Au21MiBPsFOiLADAoGAQko/r8nCyxeFE2dKey1F8qD+Pa5f0AZw6mUGVHiqRvqSUuBeNUnTKlayfaxlmCgfkMZSinoxZQKA1TTMdVH4uPY/9buSe/ThdkOa8FQroMVJxk8gLA1axtrVXo8v059bupa8tpvFNNzjjNezJlACL9ajpcL0ps6zZE273O4YoCw=";
            //沙箱环境RSA2支付宝公钥
            String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8s0ODo/JScCoXpRHQ4zG7L+Ec6Ew4AcyXA44XLR6YcvUqxtlWzz1qrLuFAXFrnLd+4IcyNlYms/5FGlcFaUkDIUmO8sjcSoCtlBPf9YkVpvrC3hz62Q2sT6CvCjCHcDp1R5I+veYkK/v9K7dX7d8qgSHrVZMx/cW8N0iLPqo3JVlwgjIRDLuhzhCgUJO2xBC7dnykcILztO/eeqcrTbwrxeuKQrQq75y3sL2Khr5x7MRun1y/WL/ra2y+yEVnDHy84vpLfYpvx4GLuHE9AntS6RJAobyU6QfKxfPxYSZFcndWUnlgY6pbL/Jcy955XpndZmtK6czG0wc9vJTRp9dvwIDAQAB";
            String encodeUrl = "";
            String url = "http://192.168.43.82:2333/Login_return_url";
//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",
//                appid,privateKey,"json","utf-8",alipayPublicKey,"RSA2");
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL,
                    AlipayConfig.APPID,AlipayConfig.RSA_PRIVATE_KEY,AlipayConfig.FORMAT,
                    AlipayConfig.CHARSET,alipayPublicKey,AlipayConfig.SIGNTYPE);
            try{
                encodeUrl = URLEncoder.encode(AlipayConfig.Login_return_url,AlipayConfig.CHARSET);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
            String scope = "auth_user,auth_base";
            String visitUrl = "https://openauth.alipaydev.com/oauth2/appToAppAuth.htm?app_id="
                    +AlipayConfig.APPID+"&redirect_uri="+AlipayConfig.Login_return_url+
                    "&state="+desk_id;
            System.out.println(encodeUrl);
            //https://openauth.alipaydev.com/oauth2/appToAppAuth.htm?app_id=2016102100729554&redirect_uri=http://192.168.43.82:2333/Login_return_url
            return new ModelAndView("redirect:"+visitUrl);
        }
    }
    @RequestMapping("/Login_return_url")
    @ResponseBody
    public ModelAndView Login_return_url(Model model, HttpServletRequest request)
            throws UnsupportedEncodingException, AlipayApiException {

            // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        String userId = "",desk_id="";
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);

            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            // 支付宝用户号
            String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("app_id:" + app_id + "\n");

            // 获取第三方登录授权
            String alipay_app_auth = new String(request.getParameter("source").getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("alipay_app_auth:" + alipay_app_auth + "\n");

            // 第三方授权code
            String app_auth_code = new String(request.getParameter("app_auth_code").getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("app_auth_code:" + app_auth_code + "\n");

            // 获取state值（桌号）
            String state = new String(request.getParameter("state").getBytes("ISO-8859-1"), "UTF-8");
            desk_id = state;
            System.out.println("state:" + state + "\n");

            String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCeBpSvc226wCxREo5jS7U35daejX/fDrSofmIieu6tG85kqKrFc2tdvoDmd801kEmKUE8jrG2BWuM7u6cOVHMjoIlewU4N2RB+33FQtVs+KAEITv5Jou9su0DmWtHiH92fIqg3yh0NOhRBJbOJ1bx6MS1vOrrxbUHutkepuaoWkR60qXPJc8csFPGbNdqFHAfkuQV2vwLFRAU5Gw//FohN4JbqzvePsetAz63mRC65E4oS1vleb9YLKOt6fBicticF456u3XoImY3rH2zylXQSNKA3jEmn30NehJrlTPv/XJ48Ra/UND+7HdZeoa7leD9Rv50oRuFldq7HNKp8dpetAgMBAAECggEAQienHzxHd6Lz6ozGJzOOjfQeQQojuhHB97fRBXZJbRby9JjXxQlorToPZGxK8F2TK+ArAVgyD7Eo59zLNuiLuyJ937k1H/77NOH94jfKFt9Qb1YChnk7ml1Z8hWbP/rvIKu1mIV4XA0wZYWO/+kGmnD0AFip4mBG9dRBdABSAFffQka/kugmFwnqrKMo3BZi/9XCScOAfVzbCQ4dDKkSMWVCk9HV9YMUoVKiS4hCEVTs0E5/vnEO96yCfV593pi8tl0B+W5I5YgTUYibLTfql3loGkmTCPEjdd0FMfZyuspH6VdGNpR2JAcCSXK5mYRaiHGtVSd+IaoyzhtZWHT6UQKBgQDdTTWah9ADc3tV02tlKm8fcLq3Yt95wyDptppUHhm7B68yltuswW/jGzbE1ECZJnSEn825Le+6L4IBsWFyKRnsXqjxxvhByNsSQl9wI+ttJtb6fcRvWj+9sVrya+p/l4iUPuNtQtAW/FDQ++BFVF39mnytgT7qk9HlX2C1Kl1fdwKBgQC2zYzWhDCJ1yFf2lLJQ4rECs84eNDYnFgaTC/Vt8zaKj4xq5TZDPOFZlwm23+OnaQTxR3HLcSyHU71owZ3JyXiNKF/bGPymwcRm9/HwQMoGv2Bti6XW/QhUtYGicXfChAarUIqjUK/hs4oDgW5BpPuESvRBWBjt6BXRK/MFJFy+wKBgHrQxaXsJ4oyxcj820Y6xY7qTgVGbwWxQAvUllOGnPsKKbXmuSVn+QNN8BhOP0d/avzLfy19C+UFRp5P5eeoXcWrRxFfPhmsMcAxa6vdk2NxQa+kqqatrGBHFFUjhPGolFjJigfyI3AOOX+xuWZgiwUafoUADH286ajlRNNmHonDAoGBALauIaG6hpspXxPgJT02fzU8rCr+KY9eZnkZS/Bi9pfLAU437s8drzrPuSWn0whdpzuOkBydM2Tf/ylgmrR2bdhpyj6BvjwTCvRg9jg0PYhVuKNowZTG8uheVL5B7njfIIrYPDgz5NFr0RecM8HcvfZ6OHRw0Au21MiBPsFOiLADAoGAQko/r8nCyxeFE2dKey1F8qD+Pa5f0AZw6mUGVHiqRvqSUuBeNUnTKlayfaxlmCgfkMZSinoxZQKA1TTMdVH4uPY/9buSe/ThdkOa8FQroMVJxk8gLA1axtrVXo8v059bupa8tpvFNNzjjNezJlACL9ajpcL0ps6zZE273O4YoCw=";

            // 支付宝的公钥，不是自己本地生成的公钥
            String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8s0ODo/JScCoXpRHQ4zG7L+Ec6Ew4AcyXA44XLR6YcvUqxtlWzz1qrLuFAXFrnLd+4IcyNlYms/5FGlcFaUkDIUmO8sjcSoCtlBPf9YkVpvrC3hz62Q2sT6CvCjCHcDp1R5I+veYkK/v9K7dX7d8qgSHrVZMx/cW8N0iLPqo3JVlwgjIRDLuhzhCgUJO2xBC7dnykcILztO/eeqcrTbwrxeuKQrQq75y3sL2Khr5x7MRun1y/WL/ra2y+yEVnDHy84vpLfYpvx4GLuHE9AntS6RJAobyU6QfKxfPxYSZFcndWUnlgY6pbL/Jcy955XpndZmtK6czG0wc9vJTRp9dvwIDAQAB";

            // 使用auth_code换取接口access_token及用户userId
            // AlipayClient alipayClient = new
            // DefaultAlipayClient("https://openapi.alipay.com/gateway.do","应用APPID",privateKey,"json","UTF-8",publicKey,"RSA2");//正常环境下的网关
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL,
                    app_id,AlipayConfig.RSA_PRIVATE_KEY,AlipayConfig.FORMAT,
                    AlipayConfig.CHARSET,AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
            AlipayOpenAuthTokenAppRequest requestLogin1 = new AlipayOpenAuthTokenAppRequest();
            requestLogin1.setBizContent(
                    "{" + "\"grant_type\":\"authorization_code\"," + "\"code\":\"" + app_auth_code + "\"" + "}");

            // 第三方授权
            AlipayOpenAuthTokenAppResponse responseToken = alipayClient.execute(requestLogin1);
//            AlipayOpenAuthTokenAppResponse responseToken = alipayClient.certificateExecute(requestLogin1);

            if (responseToken.isSuccess()) {
                System.out.println("<br/>调用成功" + "\n");

                System.out.println(responseToken.getAuthAppId() + "\n");
                System.out.println(responseToken.getAppAuthToken() + "\n");
                userId = responseToken.getUserId();
                System.out.println("userId : "+userId + "\n");
                model.addAttribute("userId",responseToken.getUserId());
                break;
            } else {
                System.out.println("11调用失败" + "\n");
                break;
            }
        }
        System.out.println("123321123");
        ModelAndView modelAndView = new ModelAndView("redirect:/XiaDan?userId="
                +userId+"&desk_id="+desk_id);
        return modelAndView;
    }


    /**
     * 支付宝手机网站支付
     *
//     * @param alipayBean
     * @return
     */
    @RequestMapping("/alipay")
    @ResponseBody
    public String webAlipay(HttpServletRequest request) {
//        , @RequestBody AlipayBean alipayBean
//        ModelMap model, Order order,
        AlipayBean alipayBean = new AlipayBean();
        System.out.println("here is alipay");
        String ID, totalprice;
        ID = request.getParameter("ID");
        Order order = orderMapper.selectByPrimaryKey(ID);
        totalprice = String.valueOf(order.getoTotalprice()) ;
        alipayBean.setOutTradeNo(ID);
        alipayBean.setTotalAmount(totalprice);
        alipayBean.setSubject("餐饮.XX饭店");

        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel AlipayModel = getAlipayTradeWapPayModel(alipayBean);

        // 设置请求支付信息
        alipayRequest.setBizModel(AlipayModel);
//        alipayRequest.setBizContent("{" +
//                "    \"out_trade_no\":\"2016102100729554\"," +
//                "    \"total_amount\":20.88," +
//                "    \"subject\":\"Iphone6 16G\"," +
//                "    \"product_code\":\"QUICK_WAP_WAY\"" +
//                "  }");//填充业务参数

        // 设置异步通知地址
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        // 设置同步地址
        alipayRequest.setReturnUrl(AlipayConfig.return_url);

        // form表单生产
        String form = "";
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            System.out.printf("调用SDK生成表单异常");
        }
        System.out.println("form is \n"+form);
        return form;
    }

    @RequestMapping("/return_url")
    @ResponseBody
    public ModelAndView return_url(Model model, HttpServletRequest request)
            throws UnsupportedEncodingException, AlipayApiException {
        System.out.println("here is return_url");
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //使用商户订单号获取用户号
        Order order = orderMapper.selectByPrimaryKey(out_trade_no);
        String userId = order.getoUser();
        String desk_id = order.getoDesk();

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

        if(verify_result){//验证成功
            System.out.println("验证成功");
            order.setoId(out_trade_no);
            order.setoFlag("1");
            orderMapper.updateByPrimaryKeySelective(order);
            ModelAndView modelAndView = new ModelAndView("redirect:/Order?userId="+userId+"&desk_id="+desk_id);
            return modelAndView;
//            return "redirect:/Order?userId="+userId;
        }else{
            System.out.println("验证失败");
            ModelAndView modelAndView = new ModelAndView("redirect:/XiaDan?userId="+userId+"&desk_id="+desk_id);
            return modelAndView;
//            return "redirect:/XiaDan?userId="+userId;
        }
    }

    @RequestMapping("/notify_url")
    @ResponseBody
    public String notify_url(Model model, HttpServletRequest request)
            throws UnsupportedEncodingException, AlipayApiException {
        System.out.println("here is notify_url");
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        //使用商户订单号获取用户号
        Order order = orderMapper.selectByPrimaryKey(out_trade_no);
        String userId = order.getoUser();
        String desk_id = order.getoDesk();

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

        if(verify_result){//验证成功
            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }

            order.setoFlag("1");
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
            System.out.println("success");	//请不要修改或删除
            return "验证成功";
        }else{
            System.out.println("验证失败");
            return "验证失败";
        }
    }


    /**
     * 封装请求支付信息
     *
     * @param alipayBean
     * @return
     */
    private AlipayTradeWapPayModel getAlipayTradeWapPayModel(AlipayBean alipayBean) {

        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        model.setOutTradeNo(alipayBean.getOutTradeNo());
        // 订单名称，必填
        model.setSubject(alipayBean.getSubject());
        // 付款金额，必填
        model.setTotalAmount(alipayBean.getTotalAmount());
//        // 商品描述，可空
//        model.setBody(alipayBean.getBody());
//        // 超时时间 可空
//        model.setTimeoutExpress("2m");
        // 销售产品码 必填
        model.setProductCode("QUICK_WAP_WAY");
        return model;
    }



}

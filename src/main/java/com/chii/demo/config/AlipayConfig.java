package com.chii.demo.config;

public class AlipayConfig {
    // 商户appid
    public static String APPID = "2016102100729554";

    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCeBpSvc226wCxREo5jS7U35daejX/fDrSofmIieu6tG85kqKrFc2tdvoDmd801kEmKUE8jrG2BWuM7u6cOVHMjoIlewU4N2RB+33FQtVs+KAEITv5Jou9su0DmWtHiH92fIqg3yh0NOhRBJbOJ1bx6MS1vOrrxbUHutkepuaoWkR60qXPJc8csFPGbNdqFHAfkuQV2vwLFRAU5Gw//FohN4JbqzvePsetAz63mRC65E4oS1vleb9YLKOt6fBicticF456u3XoImY3rH2zylXQSNKA3jEmn30NehJrlTPv/XJ48Ra/UND+7HdZeoa7leD9Rv50oRuFldq7HNKp8dpetAgMBAAECggEAQienHzxHd6Lz6ozGJzOOjfQeQQojuhHB97fRBXZJbRby9JjXxQlorToPZGxK8F2TK+ArAVgyD7Eo59zLNuiLuyJ937k1H/77NOH94jfKFt9Qb1YChnk7ml1Z8hWbP/rvIKu1mIV4XA0wZYWO/+kGmnD0AFip4mBG9dRBdABSAFffQka/kugmFwnqrKMo3BZi/9XCScOAfVzbCQ4dDKkSMWVCk9HV9YMUoVKiS4hCEVTs0E5/vnEO96yCfV593pi8tl0B+W5I5YgTUYibLTfql3loGkmTCPEjdd0FMfZyuspH6VdGNpR2JAcCSXK5mYRaiHGtVSd+IaoyzhtZWHT6UQKBgQDdTTWah9ADc3tV02tlKm8fcLq3Yt95wyDptppUHhm7B68yltuswW/jGzbE1ECZJnSEn825Le+6L4IBsWFyKRnsXqjxxvhByNsSQl9wI+ttJtb6fcRvWj+9sVrya+p/l4iUPuNtQtAW/FDQ++BFVF39mnytgT7qk9HlX2C1Kl1fdwKBgQC2zYzWhDCJ1yFf2lLJQ4rECs84eNDYnFgaTC/Vt8zaKj4xq5TZDPOFZlwm23+OnaQTxR3HLcSyHU71owZ3JyXiNKF/bGPymwcRm9/HwQMoGv2Bti6XW/QhUtYGicXfChAarUIqjUK/hs4oDgW5BpPuESvRBWBjt6BXRK/MFJFy+wKBgHrQxaXsJ4oyxcj820Y6xY7qTgVGbwWxQAvUllOGnPsKKbXmuSVn+QNN8BhOP0d/avzLfy19C+UFRp5P5eeoXcWrRxFfPhmsMcAxa6vdk2NxQa+kqqatrGBHFFUjhPGolFjJigfyI3AOOX+xuWZgiwUafoUADH286ajlRNNmHonDAoGBALauIaG6hpspXxPgJT02fzU8rCr+KY9eZnkZS/Bi9pfLAU437s8drzrPuSWn0whdpzuOkBydM2Tf/ylgmrR2bdhpyj6BvjwTCvRg9jg0PYhVuKNowZTG8uheVL5B7njfIIrYPDgz5NFr0RecM8HcvfZ6OHRw0Au21MiBPsFOiLADAoGAQko/r8nCyxeFE2dKey1F8qD+Pa5f0AZw6mUGVHiqRvqSUuBeNUnTKlayfaxlmCgfkMZSinoxZQKA1TTMdVH4uPY/9buSe/ThdkOa8FQroMVJxk8gLA1axtrVXo8v059bupa8tpvFNNzjjNezJlACL9ajpcL0ps6zZE273O4YoCw=";

    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://192.168.43.82:2333/notify_url";
	//public static String notify_url = "http://139.199.11.57:8080/dcxt_Sam/notify_url";

    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://192.168.43.82:2333/return_url";
	//public static String return_url = "http://139.199.11.57:8080/dcxt_Sam/return_url";

    // 用户授权回调路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String Login_return_url = "http://192.168.43.82:2333/Login_return_url";
	//public static String Login_return_url = "http://139.199.11.57:8080/dcxt_Sam/Login_return_url";

    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";

    // 编码
    public static String CHARSET = "UTF-8";

    // 返回格式
    public static String FORMAT = "json";

    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8s0ODo/JScCoXpRHQ4zG7L+Ec6Ew4AcyXA44XLR6YcvUqxtlWzz1qrLuFAXFrnLd+4IcyNlYms/5FGlcFaUkDIUmO8sjcSoCtlBPf9YkVpvrC3hz62Q2sT6CvCjCHcDp1R5I+veYkK/v9K7dX7d8qgSHrVZMx/cW8N0iLPqo3JVlwgjIRDLuhzhCgUJO2xBC7dnykcILztO/eeqcrTbwrxeuKQrQq75y3sL2Khr5x7MRun1y/WL/ra2y+yEVnDHy84vpLfYpvx4GLuHE9AntS6RJAobyU6QfKxfPxYSZFcndWUnlgY6pbL/Jcy955XpndZmtK6czG0wc9vJTRp9dvwIDAQAB";

    // 日志记录目录
    public static String log_path = "/log";

    // RSA2
    public static String SIGNTYPE = "RSA2";
}

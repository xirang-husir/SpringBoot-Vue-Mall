package com.swpu.cl.config;

/**
 * FileName: AlipayConfig
 * Author: hu_sir
 * Date:   2024/6/12 23:37
 * Description: 支付宝支付的配置类
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
public class AlipayConfig {
    // 应用ID，就是支付宝账号创建沙箱账号的 APPID
    public static String app_id = "9021000137691498";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQCzqW2EC4HdEkAJo1HdCGTLwumI7LmaJl1l2Sf8Jw9/zUGD8FTuMlVlYURUEwdSRUe2eg4ewQ+lZ4IZc+WwoRsJFrn3dRgzQ4MPJ3fZtHdiYaEIK4pkntSvK0zksfZniIteUF6LJ8iygaX5O5RxpA9lLrlxxBQq+VRcoE/ZzqpyJRbfR/uwjIm/QsSb2SvG/f14q0I4rkcJnSNLcVi2QOIGqzm4YSzf8qiS5bgisThq6gLJqjow+5GxNsSTXWeC1Wg9tP4TxPzWvPur5pETC1s+K4fVm/ORkLGZ69JsWmSEFFLrS3r2ndY/ZmhQstzPKXliU9Z3/qQkIQllUkuvYmnJAgMBAAECggEBAJ3JHgKK7KmQPqd4RQw/M+70Jo/3f2XPbdVl60T7tRQD2xcZ+i0cDX20+AJyl8p8R+gNt9rwqWIVzt+1trLyOsE3sWmP8AoNr8ueWRbc9MB1h6yaOLf3+khBWg278fCpreTN5sqFzTBgxrhlyBAqCs9NQtaUYzX+tUWIEbjzVSRaztAHnLAYSdzfaKUiQvrHbVG3ihM7nmGPpCjGS6UUW7IJZmPniQNbwHltkNAwEDSmlnNgt4sanOrM+t53eDCWR0SUUxGoUS6p+2KMFv1YKmqwl+GPs9IPbAWd9K8weWW0Up5Z0kTR4E0uEgiZk3+fIHYdHwL3ZWzlNQeYQ6aeBKUCgYEA5rVP7poQf9o70yF0+Iy3UHm6cqHtDGe2ABq3f9xZR14Pqzevus4GTqRuhd5+TSYCAnXRq9wa7cFc3qaXMHeCbj2FNUGKKZokgBtYa6eE8qAcxOg70kvx+OBsYNwzpPd3O2xqak7+T2vKdCcJFmLZCmmb4TYXJ+kgfrw4pVMrsUcCgYEAx1uHTA5fNVuDrbSAen52g/bvcSDWRw8GNNLpZE4DerH6iWnBoH3nFfyVJ8nfYR0s390KemEydhwZFaW98ZZyuI1wykRN6UVhFeZWUeQrgsDMkltiSRJU7ahDSUdvJFNwWavBUZAG8OxfaOsEpZ2SBjifGVLv9yXJtJqV+QExFG8CgYEAjVJJaV5Edxzmgpk+/rWr4Qb/+BJKk8u/x+aVuyPuKHJs5LPS2Ho1kNzWuvgko/uBRaKIUl8cu8Ezs0uW12CNrBvRSmMgUAWVEEgv7JoMLVd46AFeC57r1Ukb+mVI2WehSOxLb11+GIZt2KLExUq9qswwaU9p9c+vURLZHCSLYicCgYEAv4lq5mHwi51PWHdR8V7Lk2KPDswT09XViq8RWjOyGBIjuh6H0CDnAzLYQ5ArNG0JjlQbGxWDnALG/wttAU4hDrumIE+b44GrE5VoXemw/EEsy3lNZxNb0bKWYzNkzyvzh9QDOs/8O0F/MZiLsGtBvwRrp7MNpxEBSKo1aTYB57UCgYEArdo2RzFApPWRruGdmLFPUlMCyPD5kTz+oyT0NFhOoqbL1OdF3fq1fACdKgtQPQcFQE5nZMAAF2bLryLs261D1jUnb1SBYY8Beny+Pud9jNVvZnlCQXmOBjFPyAJLl0k9B/ZXnaG5IdFYkMrZCIpwVfPbsmuw7G5S9PjJM/jRFMI=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl5h/VOcEQe3ANg1HII8oCYA62vrFMDWsSiRis/ZFEiI+2R0d3WHvccaauQyu/whHq0IpMt7rhQoriMUrpxYXLDIs8BmEPMBOu9N/xtdK2MrAZ6sFt4OfV6VbaccVffTrKMuJfPLXgTHyd2YMxfdFojIJm1A0OWyZhb4cuJSfntv+SaA5w1e+2JAFY2jYhXkN1b2gSTrWwfV6sG3fWauAg6lNi7ADRPedDao0c5yU1wghkhnfd06EqRXS3AmAP1U0Yezf6RDe0S8yIBl0mf8AvG0BEJssqfz+b5dAwm3skwyUQ6tZRs1GhOu54xSKp8+cY+IqQ71YBk+vUmYJykN85wIDAQAB";
    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://nvcn3a.natappfree.cc/pay/form";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:9001/pay/backPayInfo";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    // 数据格式
    public static String format = "JSON";
}
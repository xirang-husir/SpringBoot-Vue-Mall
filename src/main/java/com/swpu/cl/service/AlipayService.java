package com.swpu.cl.service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AlipayService {

    @Value("${alipay.gatewayUrl}")
    private String gatewayUrl;

    @Value("${alipay.appId}")
    private String appId;

    @Value("${alipay.merchantPrivateKey}")
    private String merchantPrivateKey;

    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    @Value("${alipay.signType}")
    private String signType;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Value("${alipay.returnUrl}")
    private String returnUrl;

    public String createTrade(String orderId, double totalAmount) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, merchantPrivateKey, "json", "utf-8", alipayPublicKey, signType);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);

        request.setBizContent("{" +
                "\"out_trade_no\":\"" + orderId + "\"," +
                "\"total_amount\":\"" + totalAmount + "\"," +
                "\"subject\":\"订单支付\"," +
                "\"store_id\":\"test_store_id\"," +
                "\"timeout_express\":\"90m\"}");

        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            return response.getQrCode(); // 返回二维码地址
        } else {
            throw new RuntimeException("调用支付宝失败，错误信息：" + response.getSubMsg());
        }
    }
}

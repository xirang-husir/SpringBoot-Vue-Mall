package com.swpu.cl.contrallor;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.swpu.cl.config.AlipayConfig;
import com.swpu.cl.mapper.OrderMapper;
import com.swpu.cl.mapper.PayMapper;
import com.swpu.cl.pojo.Order;
import com.swpu.cl.pojo.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//支付宝沙箱支付
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private PayMapper payMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //通过 @RequestBody 注解，payForm 方法接受一个 JSON 格式的请求体，
    // 包含支付相关的参数（例如 tradeNo, totalAmount, subject）。
    // 前端通过 axios 请求发送支付信息的场景，后端生成支付页面并返回。
    // 把参数带到pay中
    @PostMapping(value = "/form", consumes = "application/json")
    public void payForm(@RequestBody Map<String, String> params, HttpServletResponse response) throws Exception {
        String tradeNo = params.get("tradeNo");
        String totalAmount = params.get("totalAmount");
        String username = params.get("username");

        AlipayClient alipayClient = new DefaultAlipayClient(
                // 网关
                AlipayConfig.gatewayUrl,
                // 应用ID
                AlipayConfig.app_id,
                // 私钥
                AlipayConfig.merchant_private_key,
                // 数据格式
                AlipayConfig.format,
                // 字符编码
                AlipayConfig.charset,
                // 公钥
                AlipayConfig.alipay_public_key,
                // 签名方式
                AlipayConfig.sign_type
        );

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        // 页面跳转异步通知页面路径
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        // 使用固定的 subject 字符串
        String subject = "商品订单";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + tradeNo + "\","
                + "\"total_amount\":\"" + totalAmount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        // 缓存支付信息
        redisTemplate.opsForValue().set("pay:info:" + tradeNo, params, 1, TimeUnit.HOURS);

        // 在返回的结果中添加跳转逻辑
        String redirectScript = "<script>window.onload = function() {" +
                "setTimeout(function() {" +
                "window.location.href='http://localhost:8088';" +
                "}, 1000);" +
                "};</script>";

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println(result + redirectScript);
    }

    @RequestMapping("/backPayInfo")
    @Transactional
    public void backPayInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);

        if (signVerified) {
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            double total_amount = Double.parseDouble(new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8"));

            // 从Redis缓存中获取支付信息
            Map<String, String> payInfo = (Map<String, String>) redisTemplate.opsForValue().get("pay:info:" + out_trade_no);
            if (payInfo == null) {
                response.getWriter().write("支付信息缓存丢失");
                return;
            }

            String username = payInfo.get("username");
            String subject = "商品订单";
            Pay pay = new Pay();
            pay.setTradeNo(trade_no);
            pay.setOutTradeNo(out_trade_no);
            pay.setTotalAmount(total_amount);
            pay.setUsername(username);
            pay.setPayDate(LocalDate.now());
            pay.setSubject(subject);

            if (payMapper.insertPayInfo(pay) > 0) {  // 确保返回值大于0，表示插入成功
                redisTemplate.delete("pay:info:" + out_trade_no);

                // 支付成功之后，跳转到前端支付完成的订单页面
                PrintWriter writer = response.getWriter();
                writer.println("<!DOCTYPE html>");
                writer.println("<html>");
                writer.println("<head>");
                writer.println("<title>定时任务示例</title>");
                writer.println("</head>");
                writer.println("<body>");
                writer.println("<script>");
                writer.println("window.onload = function() {");
                writer.println("  setInterval(function() {");
                writer.println("    location.href='http://localhost:8088/front/paySuccess';");
                writer.println("  }, 1000);");
                writer.println("};");
                writer.println("</script>");
                writer.println("</body>");
                writer.println("</html>");
            } else {
                response.getWriter().write("操作失败");
            }
        } else {
            response.getWriter().write("验证签名失败");
        }
    }

    //通过 @RequestParam 注解，pay 方法从 URL 参数中接收订单 ID（orderId）
    //链接包含订单 ID，服务器根据订单 ID 生成支付页面。
    // 支付订单的方法，完成跳转到沙盒
    @GetMapping("/pay")
    public void pay(@RequestParam String orderId, HttpServletResponse httpServletResponse) throws Exception {
        Order order = orderMapper.selectOrderById(Integer.parseInt(orderId));
        if (order == null) {
            httpServletResponse.setContentType("text/html;charset=UTF-8");
            PrintWriter out = httpServletResponse.getWriter();
            out.println("订单不存在");
            return;
        }

        // 创建支付宝客户端
        AlipayClient alipayClient = new DefaultAlipayClient(
                AlipayConfig.gatewayUrl,
                AlipayConfig.app_id,
                AlipayConfig.merchant_private_key,
                AlipayConfig.format,
                AlipayConfig.charset,
                AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type
        );

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        // 使用固定的 subject 字符串
        String subject = "商品订单";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + order.getOrderNumber() + "\","
                + "\"total_amount\":\"" + order.getOrderTotalPrice() + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        // 调用支付宝接口
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        // 输出结果
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.println(result);
    }
}

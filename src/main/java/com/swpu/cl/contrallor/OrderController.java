package com.swpu.cl.contrallor;

//import com.swpu.cl.service.AlipayService;
import com.alipay.api.AlipayApiException;
import com.swpu.cl.service.AlipayService;
import com.swpu.cl.pojo.OrderItem;
import com.swpu.cl.pojo.OrderRequest;
import com.swpu.cl.mapper.OrderMapper;
import com.swpu.cl.pojo.Order;
import com.swpu.cl.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * FileName: OrderController
 * Author: hu_sir
 * Date:   2024/6/12 22:43
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@RestController
@RequestMapping("/front/settlementPage")
public class OrderController {

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/createOrder")
    public ResponseMessage createOrder(@RequestBody OrderRequest orderRequest) {
        String orderNumber = UUID.randomUUID().toString();
        double totalAmount = 0.0;

        for (OrderItem item : orderRequest.getItems()) {
            totalAmount += item.getProductPrice() * item.getProductAmount();
        }

        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setOrderAddress(orderRequest.getOrderAddress());
        order.setOrderTotalPrice(totalAmount);
        order.setOrderUsername(orderRequest.getOrderUsername());
        order.setOrderState(1);
        order.setCreateTime(LocalDate.now());
        order.setUpdateTime(LocalDate.now());

        int result = orderMapper.insert(order);

        if (result > 0) {
            // 保存订单商品关联信息
            for (OrderItem item : orderRequest.getItems()) {
                item.setOrderNumber(order.getOrderNumber()); // 设置订单number
                orderMapper.insertOrderItem(item); // 用于插入订单商品信息
            }
            redisTemplate.opsForValue().set("order:" + order.getOrderId(), order, 1, TimeUnit.HOURS);
            Map<String, Object> data = new HashMap<>();
            data.put("orderId", order.getOrderId());
            return new ResponseMessage(200, "Order created successfully", data);
        } else {
            return new ResponseMessage(500, "Order creation failed", null);
        }
    }

    @GetMapping("/orderDetail")
    public ResponseMessage getOrderDetail(@RequestParam("order_id") Integer orderId) {
        Order order = (Order) redisTemplate.opsForValue().get("order:" + orderId);
        if (order == null) {
            order = orderMapper.selectOrderById(orderId);
            if (order != null) {
                redisTemplate.opsForValue().set("order:" + orderId, order, 1, TimeUnit.HOURS);
            }
        }

        if (order != null) {
            return new ResponseMessage(200, "Order fetched successfully", order);
        } else {
            return new ResponseMessage(404, "Order not found", null);
        }
    }

    @PostMapping("/tradeQrCode")
    public ResponseMessage generateTradeQrCode(@RequestBody Map<String, String> orderInfo) {
        String orderIdStr = orderInfo.get("orderId");
        if (orderIdStr == null || orderIdStr.isEmpty()) {
            return new ResponseMessage(400, "Order ID is missing", null);
        }

        Integer orderId = Integer.parseInt(orderIdStr);
        Order order = (Order) redisTemplate.opsForValue().get("order:" + orderId);
        if (order == null) {
            order = orderMapper.selectOrderById(orderId);
            if (order != null) {
                redisTemplate.opsForValue().set("order:" + orderId, order, 1, TimeUnit.HOURS);
            }
        }

        if (order == null) {
            return new ResponseMessage(404, "Order not found", null);
        }

        try {
            String qrCodeUrl = alipayService.createTrade(order.getOrderNumber(), order.getOrderTotalPrice());
            Map<String, Object> data = new HashMap<>();
            data.put("qrCodeUrl", qrCodeUrl);
            return new ResponseMessage(200, "QR Code generated successfully", data);
        } catch (AlipayApiException e) {
            return new ResponseMessage(500, "Failed to generate QR Code", e.getMessage());
        }
    }
}

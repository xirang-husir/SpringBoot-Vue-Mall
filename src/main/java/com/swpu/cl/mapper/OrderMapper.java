package com.swpu.cl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swpu.cl.pojo.Order;
import com.swpu.cl.pojo.OrderItem;
import com.swpu.cl.pojo.Pay;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Insert("INSERT INTO `order` (order_number, order_address, order_total_price, create_time,update_time,order_username, order_state) VALUES (#{orderNumber}, #{orderAddress}, #{orderTotalPrice},#{createTime},#{updateTime},#{orderUsername},#{orderState})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId", keyColumn = "order_id")
    int insert(Order order);

    @Select("SELECT order_id, order_number, order_address, order_total_price, order_username, create_time, update_time, order_state FROM `order` WHERE order_id=#{orderId}")
    Order selectOrderById(@Param("orderId") Integer orderId);

    @Select("SELECT order_total_price FROM `order` ORDER BY create_time DESC LIMIT 1")
    double queryLastPay();

    // 将购买的商品插入订单详情表
    @Insert("INSERT INTO order_item (order_number,product_name, product_price, product_amount) VALUES (#{orderNumber},#{productName}, #{productPrice}, #{productAmount})")
    int insertOrderItem(OrderItem item);
}

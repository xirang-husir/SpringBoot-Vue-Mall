package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * FileName: Order
 * Author: hu_sir
 * Date:   2024/6/12 22:38
 * Description: 订单实体类
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "order")
public class Order {
    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;
    private String orderNumber;
    private String orderAddress;
    private Double orderTotalPrice;
    private String orderUsername;
    private LocalDate createTime;
    private LocalDate updateTime;
    @TableLogic
    private Integer orderState;

    private List<ShoppingCar> shoppingCarList;
}

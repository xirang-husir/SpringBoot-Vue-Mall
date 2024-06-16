package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FileName: ShoppingCar
 * Author: hu_sir
 * Date:   2024/6/12 8:16
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "shopping_car")
public class ShoppingCar {
    @TableId(value = "shopping_car_id",type = IdType.AUTO)
    private Integer shoppingCarId;
    private String productImageName;
    private String productName;
    private double productPrice;
    private Integer productAmount;
    private String shoppingCarUsername;
}

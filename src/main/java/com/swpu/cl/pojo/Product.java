package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

/**
 * FileName: Product
 * Author: hu_sir
 * Date:   2024/6/6 9:54
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "product")
public class Product {
    @TableId(value = "product_id",type = IdType.AUTO)
    private Integer productId;
    private String productName;
    private Double productPrice;
    private String productDescription;
    private Double ratingScore;
    private Integer stockQuantity;
    @TableLogic
    private Integer productState;
    @TableField(exist = false)
    private List<Images> images;  // 图片名字
    // 用于模糊搜索的图片名
    @TableField(exist = false)
    private String imagesName;
    @TableField(exist = false)
    private List<Colors> colors; // 颜色
    @TableField(exist = false)
    private Integer commentCount; // 评论总条数
}

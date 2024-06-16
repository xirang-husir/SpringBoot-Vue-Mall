package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * FileName: GoodsType
 * Author: hu_sir
 * Date:   2024/6/5 9:58
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "goods_type")
public class GoodsType {
    @TableId(value = "goods_type_id",type = IdType.AUTO)
    private Integer goodsTypeId;
    private String commodityType;

    // 引入一对多
    @TableField(exist = false)
    private List<Goods> goods;


}

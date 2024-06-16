package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FileName: Hot
 * Author: hu_sir
 * Date:   2024/6/5 9:00
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hot")
public class Hot {
    @TableId(value = "hot_id",type = IdType.AUTO)
    private Integer hotId;
    private String hotName;
    private String hotDescription;
    private String  hotImageName;
    private Double hotPrice;

}

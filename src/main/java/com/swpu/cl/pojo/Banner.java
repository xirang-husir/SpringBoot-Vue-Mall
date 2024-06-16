package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FileName: Banner
 * Author: hu_sir
 * Date:   2024/6/4 9:50
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */

@Data
@AllArgsConstructor
//@NoArgsConstructor
@TableName(value = "banner")
public class Banner {
    @TableId(value = "banner_id",type = IdType.AUTO)
    private Integer bannerId;
    private String bannerName;
}

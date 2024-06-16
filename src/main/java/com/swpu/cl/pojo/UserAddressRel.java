package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FileName: UserAddressRel
 * Author: hu_sir
 * Date:   2024/6/10 18:01
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_address_rel")
public class UserAddressRel {
    @TableField(value = "address_id")
    private Integer addressId;
    @TableField(value = "user_id")
    private Integer userId;
}

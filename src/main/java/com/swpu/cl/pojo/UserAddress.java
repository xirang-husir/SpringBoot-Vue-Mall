package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * FileName: UserAddress
 * Author: hu_sir
 * Date:   2024/6/8 18:14
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "address")
public class UserAddress {
    @TableId(value = "address_id", type = IdType.AUTO)
    private Integer addressId;
    private String username;
    private String username2;
    private String userPhone;
    private String userAddress;
    private String detailAddress;
    // 逻辑删除，1不可用，0可以用
    private Integer addressState;
    private LocalDate createTime;
    private LocalDate updateTime;
}

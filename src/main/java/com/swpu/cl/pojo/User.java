package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * FileName: User
 * Author: hu_sir
 * Date:   2024/6/3 10:22
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user")
public class User {
    // type = IdType.AUTO 指明主键自增，value 属性就是数据库的某一个字段和修饰的属性映射上
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;
    // 修饰主键之外的其他属性，value 属性就是数据库的某一个字段和修饰的属性映射上
    @TableField(value = "user_image")
    private String userImage;
    private String username;
    private String password;
    @TableField(exist = false)
    private  String confirmPassword;
    private Integer userAge;
    private String userGender;
    private String userEmail;
    private String userPhone;
    @TableLogic // 逻辑删除注解，修饰之后调用 mp 提供的删除方法，会变成修改这个字段的操作，1不可用，0可以用
    private Integer userState;
    private LocalDate createTime;
    private LocalDate updateTime;
}

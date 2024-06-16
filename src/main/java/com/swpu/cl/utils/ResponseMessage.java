package com.swpu.cl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FileName: ResponseMessage
 * Author: hu_sir
 * Date:   2024/6/2 16:17
 * Description:返回消息的类型
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data  // 提供了getter/setter/equals/canEqual/hashCode/toString方法
@AllArgsConstructor // 提供了全参的构造方法
@NoArgsConstructor  //  提供了无参的构造方法
public class ResponseMessage {
    // 状态码
    private Integer status;
    // 状态信息
    private String msg;
    // 数据
    private Object data;

    // 重载一个staus ，msg 的构造函数
    public ResponseMessage(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}

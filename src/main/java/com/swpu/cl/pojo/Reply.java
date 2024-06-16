package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * FileName: Reply
 * Author: hu_sir
 * Date:   2024/6/11 8:59
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "reply")
public class Reply {
    @TableId(value = "reply_id",type = IdType.AUTO)
    private Integer replyId;
    private String replyUsername;
    private String replyMsg;
    private LocalDate createTime;
}

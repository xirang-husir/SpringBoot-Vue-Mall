package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * FileName: Comment
 * Author: hu_sir
 * Date:   2024/6/7 9:59
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "comment")
public class Comment {
    @TableId(value = "comment_id",type = IdType.AUTO)
    private Integer commentId;
    private String commentUsername;
    private String commentMsg;
    private Double ratingScore;
    private LocalDate createTime;
    @TableLogic
    private Integer commentState;
    // 引入回复评论对象
    @TableField(exist = false)
    private List<Reply> replyList;
}

package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FileName: News
 * Author: hu_sir
 * Date:   2024/6/4 10:38
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "news")
public class News {
    @TableId(value = "news_id",type = IdType.AUTO)
    private Integer newsId;
    private String newsName;
    private String newsDescription;
    private String newsUrl;
}

package com.swpu.cl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swpu.cl.pojo.Reply;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * FileName: ReplyMapper
 * Author: hu_sir
 * Date:   2024/6/11 9:01
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
public interface ReplyMapper extends BaseMapper<Reply> {
    // 通过comment_id查询replyList
    @Select("select reply.* FROM reply LEFT JOIN comment_reply_rel ON reply.reply_id = comment_reply_rel.reply_id WHERE comment_reply_rel.comment_id=#{commentId}")
    List<Reply> findReplyList(Integer commentId);
}

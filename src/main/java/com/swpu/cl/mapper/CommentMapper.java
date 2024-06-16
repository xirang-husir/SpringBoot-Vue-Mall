package com.swpu.cl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.cl.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * FileName: CommentMapper
 * Author: hu_sir
 * Date:   2024/6/7 10:11
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select COUNT(*) FROM `comment` LEFT JOIN product_comment_rel ON `comment`.comment_id = product_comment_rel.comment_id WHERE product_comment_rel.product_id =#{productId} and comment_state=0")
    Integer countCommentByProductId(@Param("productId") Integer productId);

    Page<Comment> commentList(@Param("page") Page<Comment> commentPage,
                              @Param("productId") Integer productId);
}

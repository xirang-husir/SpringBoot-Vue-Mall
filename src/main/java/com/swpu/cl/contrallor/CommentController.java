package com.swpu.cl.contrallor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.cl.mapper.CommentMapper;
import com.swpu.cl.pojo.Comment;
import com.swpu.cl.utils.ResponseMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * FileName: CommentController
 * Author: hu_sir
 * Date:   2024/6/11 10:01
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@RestController
@RequestMapping("/front/comment")
public class CommentController {
    @Resource
    private CommentMapper commentMapper;
    @GetMapping("/commentList")
    public ResponseMessage commentList(Integer page, Integer size,Integer param) {
        System.out.println("param数据：" + param);
        Page<Comment> commentPage = new Page<>(page, size);
        Page<Comment> comments = commentMapper.commentList(commentPage, param);
        return new ResponseMessage(200, "评论数据", comments);
    }
}


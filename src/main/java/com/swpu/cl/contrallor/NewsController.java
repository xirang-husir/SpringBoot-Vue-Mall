package com.swpu.cl.contrallor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swpu.cl.mapper.NewsMappper;
import com.swpu.cl.pojo.News;
import com.swpu.cl.utils.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: NewsContrallor
 * Author: hu_sir
 * Date:   2024/6/4 10:38
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@RestController
@RequestMapping("/front/news")
public class NewsController {
    @Resource
    private NewsMappper newsMappper;
    @RequestMapping("/newsList")
    public ResponseMessage bannerList() {
//        SELECT * from news ORDER BY news_id desc
        List<News> news = newsMappper.selectList(new QueryWrapper<News>().last("ORDER BY news_id desc limit 6"));
        return new ResponseMessage(200,"新闻",news);
    }
}

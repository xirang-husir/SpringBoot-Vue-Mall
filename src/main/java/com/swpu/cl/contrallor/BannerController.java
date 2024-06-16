package com.swpu.cl.contrallor;

import com.swpu.cl.mapper.BannerMapper;
import com.swpu.cl.pojo.Banner;
import com.swpu.cl.utils.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: BannerContrallor
 * Author: hu_sir
 * Date:   2024/6/4 9:52
 * Description:  // 轮播图的后端逻辑
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@RestController
@RequestMapping("/front/banner")  // 这里要和前端对应
public class BannerController {
    @Resource
    private BannerMapper bannerMapper;
    // 轮播图 http://localhost :9001/front/banner/bannerList
    @GetMapping("/bannerList")
    public ResponseMessage bannerList() {
       List<Banner> banners = bannerMapper.selectList(null);
       return new ResponseMessage(200,"轮播图",banners);
    }
}

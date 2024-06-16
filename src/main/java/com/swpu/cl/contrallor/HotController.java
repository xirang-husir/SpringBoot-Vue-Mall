package com.swpu.cl.contrallor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.cl.mapper.HotMapper;
import com.swpu.cl.pojo.Hot;
import com.swpu.cl.utils.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * FileName: HotContrallor
 * Author: hu_sir
 * Date:   2024/6/5 9:00
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@RestController
@RequestMapping("/front/hot")
public class HotController {
    @Resource
    private HotMapper hotMapper;
    // 热卖图片 http://localhost :9001/front/hot/hotList
    @GetMapping("/hotList")
    public ResponseMessage hotList(Integer page,Integer size) {
        /*
        单表分页可以直接使用mp提供的selectPage方法,参数： 分页对象：Page<T> 条件构造器
        多表分页： 自己写SQL，把分页对象作为一个参数传递给Mapper接口，SQL语句不需要自己加limit,会自动加上
         */
        // 1. 创建分页对象
        Page<Hot> hotPage = new Page<>(page,size); // 参数：page,size
        // 2. 执行查询  相当于是在SQL语句后面拼接
        Page<Hot> hotData = hotMapper.selectPage(hotPage, null);
        return new ResponseMessage(200,"热卖商品",hotData);
    }
}

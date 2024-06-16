package com.swpu.cl.contrallor;

import com.swpu.cl.mapper.GoodsTypeMapper;
import com.swpu.cl.pojo.GoodsType;
import com.swpu.cl.utils.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: GoodsTypeContrallor
 * Author: hu_sir
 * Date:   2024/6/5 10:21
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@RestController
@RequestMapping("/front/goodsType")
public class GoodsTypeController {
    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @GetMapping("/goodsTypeList")
    public ResponseMessage goodsTypeList() {
        List<GoodsType> goodsTypes = goodsTypeMapper.goodsTypeList();
        return new ResponseMessage(200,"全部商品类型",goodsTypes);
    }
}

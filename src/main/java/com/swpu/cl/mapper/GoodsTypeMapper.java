package com.swpu.cl.mapper;

import com.swpu.cl.pojo.GoodsType;

import java.util.List;

/**
 * FileName: GoodsTypeMapper
 * Author: hu_sir
 * Date:   2024/6/5 10:08
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
public interface GoodsTypeMapper {
    // 定义查询方法 （全部商品分类）
    List<GoodsType> goodsTypeList();
}

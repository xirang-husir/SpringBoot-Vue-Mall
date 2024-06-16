package com.swpu.cl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.cl.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * FileName: ProductMapper
 * Author: hu_sir
 * Date:   2024/6/6 10:00
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
public interface ProductMapper extends BaseMapper<Product> {
    // 模糊分页查询
    Page<Product> productList(@Param("page") Page<Product> page, @Param("param") String param);

    // 查询单个商品的信息
    Product findProductByProductName(String productName);
}

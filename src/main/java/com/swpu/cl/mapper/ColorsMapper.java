package com.swpu.cl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swpu.cl.pojo.Colors;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * FileName: ColorsMapper
 * Author: hu_sir
 * Date:   2024/6/7 10:11
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
public interface ColorsMapper extends BaseMapper<Colors> {

    @Select("select colors.* from `colors` \n" +
            "LEFT JOIN product_colors_rel on colors.colors_id = product_colors_rel.colors_id \n" +
            "WHERE product_colors_rel.product_id = #{productId} ")
    List<Colors> findColors(Integer productId);

}

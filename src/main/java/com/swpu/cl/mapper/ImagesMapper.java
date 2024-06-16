package com.swpu.cl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swpu.cl.pojo.Images;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * FileName: ImagesMapper
 * Author: hu_sir
 * Date:   2024/6/7 10:12
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
public interface ImagesMapper extends BaseMapper<Images> {
    @Select("SELECT images.* FROM images LEFT JOIN product_images_rel ON images.images_id = product_images_rel.images_id WHERE product_images_rel.product_id = #{productId} and images_state=0")

    List<Images> findImages(Integer productId);
}

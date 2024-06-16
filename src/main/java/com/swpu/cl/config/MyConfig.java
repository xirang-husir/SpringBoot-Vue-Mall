package com.swpu.cl.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileName: MyConfig
 * Author: hu_sir
 * Date:   2024/6/2 22:28
 * Description: 这是分页插件的配置类
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@Configuration  // 标准当前是一个配置类 ，可以理解为xml配置文件的beans标签
public class MyConfig {
    // mybatis-plus 分页插件
    @Bean // 类似于xml的中的bean标签，方法的返回值就是bean的id，方法的返回值类型就是bean的class
    // 方法的返回值类似于bean的class
    // 方法的名字类似于beand的id
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

}

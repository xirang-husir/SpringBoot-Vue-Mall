package com.swpu.cl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * FileName: AppStart
 * Author: hu_sir
 * Date:   2024/6/2 15:21
 * Description:  启动类： 用来运行springboot项目
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */

@SpringBootApplication // 启动类
@MapperScan(basePackages = {"com.swpu.cl.mapper"})  // 指明这个路径下的所有mapper接口，自动生成代理对象
public class AppStart {
    //  main方法，启动项目
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class, args);
    }
}

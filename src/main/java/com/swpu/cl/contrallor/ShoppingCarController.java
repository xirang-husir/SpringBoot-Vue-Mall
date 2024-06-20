package com.swpu.cl.contrallor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swpu.cl.mapper.OrderMapper;
import com.swpu.cl.mapper.ShoppingCarMapper;
import com.swpu.cl.mapper.UserMapper;
import com.swpu.cl.pojo.Order;
import com.swpu.cl.pojo.ShoppingCar;
import com.swpu.cl.pojo.User;
import com.swpu.cl.utils.ResponseMessage;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * FileName: ShoppingCarController
 * Author: hu_sir
 * Date:   2024/6/12 8:20
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@RestController
@RequestMapping("/front/shoppingCar")
public class ShoppingCarController {
    @Resource
    private ShoppingCarMapper shoppingCarMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;

    // 查询个人购物车数据 http://localhost:9001/front/shoppingCar/findShopping?shoppingCarUsername=xiaohong
    @GetMapping("/findShopping")
    public ResponseMessage findShopping(String shoppingCarUsername) {
        List<ShoppingCar> cars = shoppingCarMapper.selectList(new QueryWrapper<ShoppingCar>().eq("shopping_car_username", shoppingCarUsername));
        return new ResponseMessage(200, "msg", cars);
    }
    // 添加商品到购物车
    @PostMapping("/addToCart")
    public ResponseMessage addToCart(@RequestBody ShoppingCar shoppingCar) {
        // 查询购物车中是否已经存在该商品
        ShoppingCar existingCar = shoppingCarMapper.selectOne(new QueryWrapper<ShoppingCar>()
                .eq("shopping_car_username", shoppingCar.getShoppingCarUsername())
                .eq("product_name", shoppingCar.getProductName()));

        if (existingCar != null) {
            // 如果商品已经在购物车中
            return new ResponseMessage(500, "该商品已经在购物车中");
        }
        int res = shoppingCarMapper.insert(shoppingCar);
        if(res>0){
            //添加成功就保存商品数据到购物车
            return new ResponseMessage(200,"添加成功");
        }
        return new ResponseMessage(500,"添加失败");
    }

    // 更新购物车http://localhost:9001/front/shoppingCar/updateShopping
    @PutMapping("/updateShopping")
    public ResponseMessage updateShopping(@RequestBody ShoppingCar shoppingCar) {
        // 只更新商品的数量（这里与前端只更新商品的数量对应，如果这里只使用了update方法，会出现错误）
        ShoppingCar existingCar = shoppingCarMapper.selectById(shoppingCar.getShoppingCarId());
        if (existingCar != null) {
            // 专门更新商品数量
            existingCar.setProductAmount(shoppingCar.getProductAmount());
            int res = shoppingCarMapper.updateById(existingCar);
            if (res > 0) {
                return new ResponseMessage(200, "更新成功");
            }
        }
        return new ResponseMessage(500, "更新失败");
    }

    // 删除用户购物车中的商品数据
    @DeleteMapping("/deleteShopping")
    public ResponseMessage delete(@RequestBody ShoppingCar shoppingCar){
        int res = shoppingCarMapper.deleteById(shoppingCar.getShoppingCarId());
        return res>0 ? new ResponseMessage(200,"操作成功") : new ResponseMessage(500,"操作失败");
    }

    // 删除已经选择的商品
    @DeleteMapping("/deleteSelected")
    public ResponseMessage deleteSelected(@RequestBody List<ShoppingCar> shoppingCars) {
        for (ShoppingCar shoppingCar : shoppingCars) {
            int res = shoppingCarMapper.deleteById(shoppingCar.getShoppingCarId());
            if (res==0) {
                return new ResponseMessage(500, "删除失败");
            }
        }
        return new ResponseMessage(200, "删除成功");
    }

    // 清空用户购物车
    @DeleteMapping("/clearShopping")
    public ResponseMessage clearShopping(String shoppingCarUsername) {
        int res = shoppingCarMapper.delete(new QueryWrapper<ShoppingCar>().eq("shopping_car_username", shoppingCarUsername));
        return res > 0 ? new ResponseMessage(200, "购物车已清空") : new ResponseMessage(500, "清空购物车失败");
    }


}

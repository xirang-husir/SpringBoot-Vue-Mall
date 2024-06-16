package com.swpu.cl.contrallor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.cl.mapper.UserAddressMapper;
import com.swpu.cl.mapper.UserAddressRelMapper;
import com.swpu.cl.mapper.UserMapper;
import com.swpu.cl.pojo.*;
import com.swpu.cl.utils.ResponseMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * FileName: UserAddressContrallor
 * Author: hu_sir
 * Date:   2024/6/3 10:38
 * Description: Address控制器
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@RestController // @Contrallor + @ResponseBody 相应给前端的数据是json
@RequestMapping("/front/userAddress") // 配置访问路径 修饰在类上就是类请求路径，修饰在方法上就是方法的请求路径
public class UserAddressController {
    // 注入 AddressMapper 接口对象
    @Resource
    private UserAddressMapper userAddressMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserAddressRelMapper userAddressRelMapper;

    //获取当前登录用户的登录地址信息
    @GetMapping("/addressList")
    public ResponseMessage addressList(@RequestParam String username) {
        //先获取当前登录者的信息、
        User findUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (Objects.isNull(findUser)) {
            return new ResponseMessage(500, "用户名不存在，获取信息失败");
        }
        //获取用户相关的关联的地址id
        List<UserAddressRel> list = userAddressRelMapper.selectList(new QueryWrapper<UserAddressRel>().eq("user_id", findUser.getUserId()));
        //根据地址ids查询地址信息
        List<UserAddress> userAddressList = new ArrayList<>();
        if (list.size() > 0) {
            for (UserAddressRel userAddressRel : list) {
                UserAddress userAddress = userAddressMapper.selectOne(new QueryWrapper<UserAddress>().eq("address_id", userAddressRel.getAddressId()));
                userAddressList.add(userAddress);
            }
        }
        return new ResponseMessage(200, "操作成功", userAddressList);
    }

    //保存地址信息
    @PostMapping("/save")
    public ResponseMessage save(@RequestBody UserAddress userAddress) {
        //如果地址id存在执行更新不存在执行保存
        if (userAddress.getAddressId() != null) {
            //更新地址信息
            int result = userAddressMapper.updateById(userAddress);
            if (result > 0) {
                return new ResponseMessage(200, "操作成功");
            }
        } else {
            //先获取当前登录者的信息、
            User findUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", userAddress.getUsername2()));
            if (Objects.isNull(findUser)) {
                return new ResponseMessage(500, "用户名不存在，获取信息失败");
            }
            //保存地址信息
            int result = userAddressMapper.insert(userAddress);
            if (result > 0) {
                //保存地址与用户信息
                UserAddressRel userAddressRel = new UserAddressRel();
                userAddressRel.setAddressId(userAddress.getAddressId());
                userAddressRel.setUserId(findUser.getUserId());
                int result2 = userAddressRelMapper.insert(userAddressRel);
                return new ResponseMessage(200, "操作成功");
            }
        }
        return new ResponseMessage(500, "操作失败");
    }

    //删除地址信息
    @PostMapping("/delete")
    public ResponseMessage delete(@RequestBody UserAddress userAddress) {
        //保存地址信息
        int result = userAddressMapper.deleteById(userAddress.getAddressId());
        if (result > 0) {
            //保存地址与用户信息
            int result2 = userAddressRelMapper.delete(new QueryWrapper<UserAddressRel>().eq("address_id", userAddress.getAddressId()));
            return new ResponseMessage(200, "操作成功");
        }
        return new ResponseMessage(500, "操作失败");
    }
}
